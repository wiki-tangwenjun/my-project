package com.wenjun.Filter;

import com.wenjun.handlerException.error.CommonEnum;
import com.wenjun.shiro.JWTToken;
import com.wenjun.util.CheckUtil;
import com.wenjun.util.ServletHttpRequest;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * @projectName: house
 * @package: com.wenjun.Filter
 * @className: JWTFilter
 * @author: wen jun tang
 * @description: 自定义过滤器，对token进行处理
 * @date: 2021年07月14日 15:50
 * @version: 1.0
 */
@Slf4j
public class JWTFilter extends BasicHttpAuthenticationFilter {
    /**
     * 如果带有 token，则对 token 进行检查，否则直接通过
     */
    @SneakyThrows
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        // 判断请求的请求头是否带上 "token"
        if (isLoginAttempt(request, response)) {
            // 如果存在，则进入 executeLogin 方法执行登入，检查 token 是否正确
            try {
                executeLogin(request, response);
                return true;
            } catch (Exception e) {
                // token 错误
                responseError(response, "token 错误");
                throw new Exception(CommonEnum.ERROR_TOKEN.getError());
            }
        }
        // 如果请求头不存在 token，则可能是执行登陆操作或者是游客状态访问，无需检查 token，直接返回 true
        return true;
    }

    /**
     * 判断用户是否想要登入。
     * 检测 header 里面是否包含 token 字段
     */
    @SneakyThrows
    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        String token = ServletHttpRequest.getToken((HttpServletRequest) request);
        return token != null;
    }

    /**
     * 执行登陆操作
     */
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        boolean flag = false;
        String header = httpServletRequest.getHeader("Authorization");
        if (!CheckUtil.isNull(header) && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            JWTToken jwtToken = new JWTToken(token);
            // 提交给realm进行登入，如果错误它会抛出异常并被捕获
            getSubject(request, response).login(jwtToken);
            flag = true;
        }

        return flag;
    }

    /**
     * 对跨域提供支持
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }

    /**
     * 将非法请求跳转到 /unauthorized/**
     */
    private void responseError(ServletResponse response, String message) throws Exception {
        try {
            HttpServletResponse httpServletResponse = (HttpServletResponse) response;
            // 设置编码，否则中文字符在重定向时会变为空字符串
            message = URLEncoder.encode(message, StandardCharsets.UTF_8);
            httpServletResponse.sendRedirect("/404/" + message);
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new Exception("权限认证异常");
        }
    }
}
