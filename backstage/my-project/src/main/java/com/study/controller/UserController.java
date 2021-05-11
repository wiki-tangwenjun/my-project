package com.study.controller;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.study.anno.LoginRequired;
import com.study.anno.Syslog;
import com.study.error.CommonEnum;
import com.study.error.ReturnValue;
import com.study.pojo.User;
import com.study.redis.UserLoginService;
import com.study.service.UserService;
import com.study.util.Base64Util;
import com.study.util.CheckUtil;
import com.study.util.EncryptUtil;
import com.study.util.TextUtil;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/user")
@Api(value = "用户管理接口列表", tags = "提供用户信息管理相关接口")
public class UserController {
    @Resource
    private UserLoginService userLoginService;
    @Resource
    private UserService userService;

    @ApiOperation(value = "添加一个新的用户", notes = "用户名、密码必填")
    @ApiImplicitParams({@ApiImplicitParam(name = "user", value = "用户信息", required = true, dataType = "User", paramType = "body")})
    @PostMapping(value = "/add")
    @LoginRequired
    @Syslog(module = "用户", style = "添加", description = "添加用户信息")
    public ReturnValue<String> add(HttpServletRequest request, @RequestBody User user) {
            // 同名检测
            User temp = userService.findByUserName(user.getUserName());
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
    public ReturnValue<String> login(HttpServletRequest request, @RequestParam(name = "userName") String userName, @RequestParam(name = "password") String password) throws IOException {
            // 从数据库中验证用户名密码
            userName = new String(Base64Util.decode(userName));
            User user = userService.findByUserName(userName.trim());
            if (CheckUtil.isNull(user)) {
                return new ReturnValue<String>(CommonEnum.ERROR_NOT_FOUND, "该用户名不存在!");
            }

            String inPassword = new String(Base64Util.decode(password), StandardCharsets.UTF_8);
            String encPwd = EncryptUtil.md5(inPassword, null, 2);
            if (!encPwd.trim().equals(user.getPassword().trim())) {
                return new ReturnValue<String>(CommonEnum.ERROR_USER_PASSWORD);
            }

            // 登陆成功保存回话信息
            userLoginService.update(request.getSession(false).getId(), userName);
            userLoginService.expired(request.getSession(false).getId(), 200);

            return new ReturnValue<String>();
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
    /**
     * @description: 获取用户登录信息
     * @author tang wen jun
     * @param request
     * @return com.study.error.ReturnValue<com.study.pojo.User>
     * @date 2021/5/11 16:28
     */
    @LoginRequired
    @ApiOperation(value = "获取当前登录用户信息", notes = "获取当前登录用户信息")
    @ApiImplicitParams({})
    @GetMapping("/principal/info")
    @Syslog(module = "用户", style = "查询当前用户信息", description = "获取当前登录用户信息")
    public ReturnValue<User> getPrinInfo(HttpServletRequest request) {
        if (CheckUtil.isNull(request.getSession(false))) {
            return new ReturnValue<User>(CommonEnum.ERROR_NOT_LOGIN, "用户会话已过期");
        }
        String userName = userLoginService.find(request.getSession(false).getId());
        if (CheckUtil.isNull(userName)) {
            return new ReturnValue<User>(CommonEnum.ERROR_NOT_LOGIN, "用户会话已过期");
        }
        // 通过用户名获取用户信息
        User user = userService.findByUserName(userName);
        if (CheckUtil.isNull(user)) {
            return new ReturnValue<User>(CommonEnum.ERROR_OBJECT_EXIST, "用户不存在");
        }
        return new ReturnValue<User>(user);
    }

    /**
     * @description: 修改当前用户密码
     * @author tang wen jun
     * @param userName
     * @param oldPassword
     * @param newPassword
     * @return com.study.error.ReturnValue<java.lang.String>
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
            User user = userService.findByUserName(userName);
            if (CheckUtil.isNull(user)) {
                return new ReturnValue<String>(CommonEnum.ERROR_INVALID_PARAM, "用户不存在");
            }

            if (!Base64Util.encodeBytes(oldPassword.getBytes(StandardCharsets.UTF_8)).equals(user.getPassword())) {
                return new ReturnValue<String>(CommonEnum.ERROR_INVALID_PARAM, "原密码不正确");
            }

            user.setPassword(Base64Util.encodeBytes(newPassword.getBytes(StandardCharsets.UTF_8)));
            userService.update(user);

        return new ReturnValue<String>();
    }

    /**
     * @description: 获取用户列表
     * @author tang wen jun
     * @param name
     * @param idCard
     * @param enabled
     * @param pageIndex
     * @param pageSize
     * @param order
     * @param orderProp
     * @return com.study.error.ReturnValue<java.util.List < com.study.pojo.User>>
     * @date 2021/5/11 16:27
     */
    @LoginRequired
    @GetMapping("/findByAttributes")
    @ApiOperation(value = "获取用户列表", notes = "分页查询用户列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "name", value = "用户姓名", required = false),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "idCard", value = "用户身份证", required = false),
            @ApiImplicitParam(paramType = "query", dataType = "Long", name = "enabled", value = "可用性", required = false),
            @ApiImplicitParam(paramType = "query", dataType = "Long", name = "pageIndex", value = "页索引", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "Long", name = "pageSize", value = "页大小", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "orderProp", value = "排序字段", required = false),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "order", value = "排序方式（asc,desc）", required = false)})
    @Syslog(module = "用户", style = "查询数据列表", description = "获取用户列表")
    public ReturnValue<List<User>> findByAttributes(@RequestParam(name = "name", required = false) String name,
                                                    @RequestParam(name = "idCard", required = false) String idCard,
                                                    @RequestParam(name = "enabled", required = false) Long enabled,
                                                    @RequestParam(name = "pageIndex", required = true) Long pageIndex,
                                                    @RequestParam(name = "pageSize", required = true) Long pageSize,
                                                    @RequestParam(name = "order", required = false) String order,
                                                    @RequestParam(name = "orderProp", required = false) String orderProp
    ) {

        List<User> userList = userService.findByAttributes(name, idCard, enabled, pageIndex, pageSize, orderProp, order);
        return new ReturnValue<List<User>>(userList);

    }

    /**
     * @description: 获取用户总数
     * @author tang wen jun
     * @param name
     * @param idCard
     * @param enabled 
     * @return com.study.error.ReturnValue<java.lang.Long>
     * @date 2021/5/11 16:27
     */
    @LoginRequired
    @GetMapping("/findMaxByAttributes")
    @ApiOperation(value = "获取用户总数", notes = "获取用户总数")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "name", value = "用户姓名", required = false),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "idCard", value = "用户身份证", required = false),
            @ApiImplicitParam(paramType = "query", dataType = "Long", name = "enabled", value = "可用性", required = false)})
    @Syslog(module = "用户", style = "查询数据数", description = "获取用户总数")
    public ReturnValue<Long> findMax(@RequestParam(name = "name", required = false) String name,
                                     @RequestParam(name = "idCard", required = false) String idCard,
                                     @RequestParam(name = "enabled", required = false) Long enabled) {
        Long count = userService.findMaxByAttributes(name, idCard, enabled);
        return new ReturnValue<Long>(count);
    }

   /**
    * @description: 删除一个用户
    * @author tang wen jun
    * @param userName
    * @return com.study.error.ReturnValue<java.lang.String>
    * @date 2021/5/11 16:27
    */
    @LoginRequired
    @ApiOperation(value="删除一个用户", notes="")
    @ApiImplicitParams({@ApiImplicitParam(name = "userName", value = "用户名", required = true, dataType = "String", paramType="path")})
    @DeleteMapping(value = "/delete/{userName}")
    @Syslog(module="用户",style="删除",description="删除用户信息")
    public ReturnValue<String> delete(@PathVariable(name="userName", required=true) String userName){
        userService.deleteByUserName(userName);
        return new ReturnValue<String>();
    }

    /**
     * @description: 用户注销
     * @author tang wen jun
     * @param request 
     * @return com.study.error.ReturnValue<java.lang.String>
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