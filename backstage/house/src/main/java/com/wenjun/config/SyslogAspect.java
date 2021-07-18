package com.wenjun.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wenjun.anno.Syslog;
import com.wenjun.busines.system.pojo.OperateLog;
import com.wenjun.busines.system.pojo.UserResources;
import com.wenjun.busines.system.service.OperateLogService;
import com.wenjun.busines.system.service.UserService;
import com.wenjun.handlerException.error.ReturnValue;
import com.wenjun.util.CheckUtil;
import com.wenjun.util.TextUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wen jun tang
 * @date 2021/7/14 11:45
 */
@Component
@Aspect
@Slf4j
public class SyslogAspect {
    @Resource
    private OperateLogService operateLogService;
    @Resource
    private UserService userService;

    /**
     * 切点。注解的方式
     */
    @Pointcut("@annotation(com.wenjun.anno.Syslog)")
    public void operLogPoinCut() {
    }

    /**
     * 设置操作异常切入点记录异常日志 扫描所有controller包下操作
     */
    @Pointcut("execution(* com.wenjun.busines.*.controller..*.*(..))")
    public void operExceptionLogPoinCut() {
    }

    /**
     * 正常返回通知，拦截用户操作日志，连接点正常执行完成后执行， 如果连接点抛出异常，则不会执行
     *
     * @param joinPoint   切入点
     * @param returnValue 返回结果
     */
    @AfterReturning(value = "operLogPoinCut()", returning = "returnValue")
    public void saveOperLog(JoinPoint joinPoint, Object returnValue) throws Exception {
        // 获取RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        // 从获取RequestAttributes中获取HttpServletRequest的信息
        assert requestAttributes != null;
        HttpServletRequest request = (HttpServletRequest) requestAttributes
                .resolveReference(RequestAttributes.REFERENCE_REQUEST);

        OperateLog operateLog = new OperateLog();
        try {
            // 从切面织入点处通过反射机制获取织入点处的方法
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            // 获取切入点所在的方法
            Method method = signature.getMethod();
            // 获取操作
            Syslog syslog = method.getAnnotation(Syslog.class);
            if (syslog != null) {
                operateLog.setDescription(syslog.description());
                operateLog.setModule(syslog.module());
                operateLog.setStyle(syslog.style());
            }
            // 获取请求的类名
            String className = joinPoint.getTarget().getClass().getName();
            // 获取请求的方法名
            String methodName = method.getName();
            methodName = className + "." + methodName;

            // 请求的参数
            assert request != null;
            Map<String, String> rtnMap = converMap(request.getParameterMap());
            assert syslog != null;
            String param = syslog.param();
            String params = "";
            // 将参数所在的数组转换成json
            if (rtnMap.size() > 0) {
                params = JSON.toJSONString(rtnMap);
                if (params.length() > 2048) {
                    params = params.substring(0, 2047);
                }
            } else {
                Map<String, Object> body = getNameAndValue(joinPoint);
                for (Map.Entry<String, Object> entry : body.entrySet()) {
                    String name = entry.getKey();
                    Object val = entry.getValue();
                    if ("request".equals(name)) {
                        continue;
                    }
                    if (!CheckUtil.isNull(param)) {
                        if (name.equals(param)) {
                            params = JSONObject.toJSON(val).toString();
                            break;
                        }
                    } else {
                        params = JSONObject.toJSON(val).toString();
                        break;
                    }
                }
            }
            // 请求参数
            operateLog.setOperand(params);
            ReturnValue<?> relReturnValue = (ReturnValue<?>) returnValue;
            // 返回结果
            operateLog.setResult(relReturnValue.getDescription());

            if (CheckUtil.isNull(request.getSession(false))) {
                return;
            }

            String header = request.getHeader("Authorization");
            if (!CheckUtil.isNull(header) && header.startsWith("Bearer ")) {
                String token = header.substring(7);
                UserResources userResources = userService.findByUserResource(token);

                if (!CheckUtil.isNull(userResources)) {
                    operateLog.setUserName(userResources.getUser().getUserName());
                    operateLog.setUserId(userResources.getUser().getId());
                    operateLog.setName(userResources.getUser().getPersonName());
                }
            }

            // 请求URI request.getRequestURI()
            operateLog.setId(TextUtil.getUUID());
            operateLogService.add(operateLog);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new Exception("记录日志异常");
        }
    }

    /**
     * 转换request 请求参数
     *
     * @param paramMap request获取的参数数组
     */
    public Map<String, String> converMap(Map<String, String[]> paramMap) {
        Map<String, String> rtnMap = new HashMap<String, String>();
        for (String key : paramMap.keySet()) {
            rtnMap.put(key, paramMap.get(key)[0]);
        }
        return rtnMap;
    }

    /**
     * 获取登录用户的IP地址
     *
     * @param request
     * @return
     */
    public String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        ip = getString(request, ip);
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if ("0:0:0:0:0:0:0:1".equals(ip)) {
            ip = "127.0.0.1";
        }
        if (ip.split(",").length > 1) {
            ip = ip.split(",")[0];
        }
        return ip;
    }

    public static String getString(HttpServletRequest request, String ip) {
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        return ip;
    }

    /**
     * 获取参数Map集合
     */
    Map<String, Object> getNameAndValue(JoinPoint joinPoint) {
        Map<String, Object> param = new HashMap<>();

        Object[] paramValues = joinPoint.getArgs();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String[] paramNames = signature.getParameterNames();

        for (int i = 0; i < paramNames.length; i++) {
            param.put(paramNames[i], paramValues[i]);
        }

        return param;
    }

}
