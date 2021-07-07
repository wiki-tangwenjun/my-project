package com.study.system.controller;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.study.anno.LoginRequired;
import com.study.anno.Syslog;
import com.study.error.CommonEnum;
import com.study.error.ReturnValue;
import com.study.system.dto.UserQueryParam;
import com.study.system.mapping.UserMapper;
import com.study.system.pojo.User;
import com.study.redis.UserLoginService;
import com.study.system.pojo.UserResources;
import com.study.system.service.UserService;
import com.study.util.*;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @author wen jun tang
 */
@Slf4j
@RestController
@RequestMapping("/user")
@Api(value = "用户管理接口列表", tags = "提供用户信息管理相关接口")
public class UserController {
    @Resource
    private UserService userService;
    @Resource
    private UserLoginService userLoginService;
    @Resource
    private UserMapper userMapper;

    @ApiOperation(value = "添加一个新的用户", notes = "用户名、密码必填")
    @ApiImplicitParams({@ApiImplicitParam(name = "user", value = "用户信息", required = true, dataType = "User", paramType = "body")})
    @PostMapping(value = "/add")
    @LoginRequired
    @Syslog(module = "用户", style = "添加", description = "添加用户信息")
    public ReturnValue<String> add(HttpServletRequest request, @RequestBody User user) {
        // 同名检测
        User temp = userMapper.selectByPersonName(user.getPersonName());
        if (!CheckUtil.isNull(temp)) {
            return new ReturnValue<String>(CommonEnum.ERROR_OBJECT_EXIST, "该用户名已存在!");
        }

        if (CheckUtil.isNull(user.getId())) {
            user.setId(TextUtil.getUUID());
        }
        // 加密password
        user.setPassword(EncryptUtil.md5(user.getPassword(), null, 2));

        userService.add(user);
        return new ReturnValue<>(user.getId());
    }

    @ApiOperation(value = "用户登陆", notes = "用户登陆")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "用户名称", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "password", value = "用户密码", required = true, dataType = "String", paramType = "query")
    })
    @GetMapping(value = "/login")
    @Syslog(module = "用户", style = "登录", description = "用户登录")
    public ReturnValue<UserResources> login(HttpServletRequest request, @RequestParam(name = "userName") String userName, @RequestParam(name = "password") String password) throws Exception {
        return new ReturnValue<>(userService.findByUserName(request, userName, password));
    }


    @LoginRequired
    @PutMapping("/update")
    @ApiOperation(value = "更新用户信息", notes = "更新用户信息")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "body", dataType = "User", name = "user", value = "用户信息", required = true)})
    @Syslog(module = "用户", style = "更新", description = "更新用户信息")
    public ReturnValue<String> update(HttpServletRequest request, @RequestBody User user) {
        User temp = userService.findById(user.getId());
        if (CheckUtil.isNull(temp)) {
            return new ReturnValue<String>(CommonEnum.ERROR_NOT_FOUND, "该用户编号不存在!");
        }

        if (!CheckUtil.isNull(user.getPassword())) {
            user.setPassword(Base64Util.encodeBytes(user.getPassword().getBytes(StandardCharsets.UTF_8)));
        }

        userService.update(user);

        return new ReturnValue<String>();
    }

    @LoginRequired
    @ApiOperation(value = "获取当前登录用户信息", notes = "获取当前登录用户信息")
    @ApiImplicitParams({})
    @GetMapping("/principal/info")
    @Syslog(module = "用户", style = "查询当前用户信息", description = "获取当前登录用户信息")
    public ReturnValue<User> getPrincipalInfo(HttpServletRequest request) {
        if (CheckUtil.isNull(request.getSession(false))) {
            return new ReturnValue<User>(CommonEnum.ERROR_NOT_LOGIN, "用户会话已过期");
        }
        String userName = userLoginService.find(request.getSession(false).getId());
        if (CheckUtil.isNull(userName)) {
            return new ReturnValue<User>(CommonEnum.ERROR_NOT_LOGIN, "用户会话已过期");
        }

        // 通过用户名获取用户信息
        User user = userMapper.selectByPersonName(userName);
        if (CheckUtil.isNull(user)) {
            return new ReturnValue<User>(CommonEnum.ERROR_OBJECT_EXIST, "用户不存在");
        }
        return new ReturnValue<User>(user);
    }

    /**
     * @return com.study.error.ReturnValue<java.lang.String>
     * @description: 修改当前用户密码
     * @author tang wen jun
     * @date 2021/5/11 16:28
     */
    @LoginRequired
    @ApiOperation(value = "修改当前用户密码", notes = "通过用户名修改用户密码")
    @ApiImplicitParams({@ApiImplicitParam(name = "userName", value = "用户名", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "oldPassword", value = "原密码", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "newPassword", value = "新密码", required = true, dataType = "String", paramType = "query")})
    @PutMapping(value = "/modifyPassword")
    @Syslog(module = "用户", style = "更新", description = "通过用户名修改用户密码")
    public ReturnValue<String> modifyPassword(@RequestParam("userName") String userName,
                                              @RequestParam("oldPassword") String oldPassword,
                                              @RequestParam("newPassword") String newPassword) {
        User user = userMapper.selectByPersonName(userName);
        if (CheckUtil.isNull(user)) {
            return new ReturnValue<String>(CommonEnum.ERROR_INVALID_PARAM, "用户不存在");
        }

        if (!Base64Util.encodeBytes(oldPassword.getBytes(StandardCharsets.UTF_8)).equals(user.getPassword())) {
            return new ReturnValue<String>(CommonEnum.ERROR_INVALID_PARAM, "原密码不正确");
        }

        user.setPassword(Base64Util.encodeBytes(newPassword.getBytes(StandardCharsets.UTF_8)));
        userService.update(user);
        return new ReturnValue<String>( );
    }

    @LoginRequired
    @GetMapping("/findByAttributes")
    @ApiOperation(value = "获取用户列表", notes = "分页查询用户列表")
    @Syslog(module = "用户", style = "查询数据列表", description = "获取用户列表")
    public ReturnValue<List<User>> findByAttributes(UserQueryParam userQueryParam) {
        List<User> userList = userService.findByAttributes(userQueryParam);
        return new ReturnValue<>(userList);

    }

    /**
     * @param request
     * @return com.study.error.ReturnValue<java.lang.String>
     * @description: 用户注销
     * @author tang wen jun
     * @date 2021/5/11 16:27
     */
    @LoginRequired
    @ApiOperation(value = "用户注销", notes = "用户注销")
    @GetMapping(value = "/logout")
    @Syslog(module = "用户", style = "登出", description = "用户登出")
    public ReturnValue<String> logout(HttpServletRequest request) {
        // 登陆成功保存回话信息
        userLoginService.delete(request.getSession(false).getId());
        return new ReturnValue<String>();
    }
}