package com.wenjun.busines.system.controller;

import com.wenjun.busines.system.dto.LoginParam;
import com.wenjun.handlerException.error.ReturnValue;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/login")
    public ReturnValue<String> login(@Valid LoginParam loginParam) throws Exception {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(loginParam.getUserName(), loginParam.getPassword());
        try {
            subject.login(token);
            return new ReturnValue<>();
        } catch (UnknownAccountException e) {
            throw new NullPointerException("用户名不存在");
        } catch (IncorrectCredentialsException e) {
            throw new Exception("密码错误");
        }
    }
}
