package com.wenjun.busines.system.controller;

import com.wenjun.anno.Syslog;
import com.wenjun.busines.system.dto.LoginParam;
import com.wenjun.busines.system.pojo.UserResources;
import com.wenjun.busines.system.service.UserService;
import com.wenjun.handlerException.error.CommonEnum;
import com.wenjun.handlerException.error.ReturnValue;
import com.wenjun.redis.UserLoginService;
import com.wenjun.util.CheckUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @projectName: house
 * @package: com.wenjun.busines.system.controller
 * @className: UserController
 * @author: wen jun tang
 * @description: 用户相关操作前端控制器
 * @date: 2021年07月14日 17:26
 * @version: 1.0
 */
@Slf4j
@RequestMapping("/user")
@RestController
@Api(value = "用户相关操作接口", tags = "用户相关操作接口")
public class UserController {
    @Resource
    private UserLoginService userLoginService;
    @Resource
    private UserService userService;

    public static final String RANDOMKEY = "randomCode";

    @GetMapping("/login")
    @ApiOperation(value="登录接口", notes="账号密码 随机数登录")
    @Syslog(module="用户信息",style="查询",description="账号密码 随机数登录")
    public ReturnValue<String> login(@Valid LoginParam loginParam) throws Exception {
        return new ReturnValue<>(userService.findByUserName(loginParam));
    }

    @GetMapping("/getVerificationCode")
    @ApiOperation(value="获取随机码接口", notes="随机数接口")
    @Syslog(module="用户信息",style="查询",description="获取随机码")
    public ReturnValue<String> getVerificationCode() {
        userLoginService.delete(RANDOMKEY);
        userLoginService.setKey(RANDOMKEY, String.valueOf((int) ((Math.random() * 9 + 1) * 10000)), 60);

        String randomCode = userLoginService.getKey(RANDOMKEY);

        return new ReturnValue<>(CommonEnum.ERROR_SUCCESS, randomCode);
    }

//    @RequiresRoles(logical = Logical.OR, value = {"user", "admin"})
    @GetMapping("/getUserResources")
    @RequiresRoles(logical = Logical.OR, value = {"admin", "superAdmin"})
    @Syslog(module="用户信息",style="查询",description="查询用户角色权限信息")
    @ApiOperation(value="根据token获取用户角色权限信息接口", notes="根据token获取用户角色权限信息")
    public ReturnValue<UserResources> getUserResources(HttpServletRequest request) {
        UserResources userResources = null;
        String header = request.getHeader("Authorization");
        if (!CheckUtil.isNull(header) && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            userResources = userService.findByUserResource(token);
        }

        return new ReturnValue<>(userResources);
    }
}
