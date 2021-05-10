package com.study.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dondown.anno.LoginRequired;
import com.dondown.anno.Syslog;
import com.dondown.error.ErrorCode;
import com.dondown.error.ReturnValue;
import com.dondown.model.User;
import com.dondown.redis.UserLoginService;
import com.dondown.service.UserService;
import com.dondown.util.Base64Util;
import com.dondown.util.CheckUtil;
import com.dondown.util.EncryptUtil;
import com.dondown.util.TextUtil;

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
    @Autowired
    private UserLoginService userLoginService;
    @Autowired
    private UserService userService;

    @ApiOperation(value = "添加一个新的用户", notes = "用户名、密码必填")
    @ApiImplicitParams({@ApiImplicitParam(name = "user", value = "用户信息", required = true, dataType = "User", paramType = "body")})
    @PostMapping(value = "/add")
    @LoginRequired
    @Syslog(module = "用户", style = "添加", description = "添加用户信息")
    public ReturnValue<String> add(HttpServletRequest request, @RequestBody User user) {
        try {
            // 同名检测
            User temp = userService.findByUserName(user.getUserName());
            if (!CheckUtil.isNull(temp)) {
                return new ReturnValue<String>(ErrorCode.ERROR_OBJECT_EXIST, "该用户名已存在!");
            }

            if (CheckUtil.isNull(user.getId())) {
                user.setId(TextUtil.getUUID());
            }
            // 加密password
            user.setPassword(EncryptUtil.md5(user.getPassword(), null, 2));

            String decryptCaKey = new String(Base64Util.decode(user.getCaKey()));
            user.setCaKey(EncryptUtil.md5(decryptCaKey, null, 2));
            userService.add(user);
            return new ReturnValue<String>(user.getId());
        } catch (Exception e) {
            log.error(e.getCause().getMessage());
            return new ReturnValue<String>(ErrorCode.ERROR_SERVER_ERROR, "添加用户失败");
        }
    }

    @ApiOperation(value = "用户登陆", notes = "用户登陆")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "用户名称", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "password", value = "用户密码", required = true, dataType = "String", paramType = "query")
    })
    @GetMapping(value = "/login")
    @Syslog(module = "用户", style = "登录", description = "用户登录")
    public ReturnValue<String> login(HttpServletRequest request, @RequestParam(name = "userName") String userName, @RequestParam(name = "password") String password) {
        try {
            // 从数据库中验证用户名密码
            userName = new String(Base64Util.decode(userName));
            User user = userService.findByUserName(userName.trim());
            if (CheckUtil.isNull(user)) {
                return new ReturnValue<String>(ErrorCode.ERROR_NOT_FOUND, "该用户名不存在!");
            }

            String inPassword = new String(Base64Util.decode(password), "utf-8");
            String encPwd = EncryptUtil.md5(inPassword, null, 2);
            if (!encPwd.trim().equals(user.getPassword().trim())) {
                return new ReturnValue<String>(ErrorCode.ERROR_USER_PASSWORD);
            }

            // 登陆成功保存回话信息
            userLoginService.update(request.getSession(false).getId(), userName);
            userLoginService.expired(request.getSession(false).getId(), 200);
            return new ReturnValue<String>();
        } catch (Exception e) {
            log.error(e.getCause().getMessage());
        }

        return new ReturnValue<String>(ErrorCode.ERROR_SERVER_ERROR, "用户登陆失败");
    }

    @ApiOperation(value = "用户密匙登陆", notes = "用户密匙登陆")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "用户名称", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "caKey", value = "用户密匙", required = true, dataType = "String", paramType = "query")
    })
    @GetMapping(value = "/caLogin")
    @Syslog(module = "用户", style = "登录", description = "用户密匙登录")
    public ReturnValue<String> caLogin(HttpServletRequest request, @RequestParam(name = "userName") String userName, @RequestParam(name = "caKey") String caKey) {
        try {
            // 从数据库中验证用户名密码
            userName = new String(Base64Util.decode(userName));
            User user = userService.findByUserName(userName.trim());
            if (CheckUtil.isNull(user)) {
                return new ReturnValue<String>(ErrorCode.ERROR_NOT_FOUND, "用户名或密码错误!");
            }

            String realCaKey = new String(Base64Util.decode(caKey));
            String encCaKey = new String(EncryptUtil.md5(realCaKey, null, 2));
            if (!encCaKey.trim().equals(user.getCaKey().trim())) {
                return new ReturnValue<String>(ErrorCode.ERROR_USER_PASSWORD, "用户名或密码错误!");
            }

            // 登陆成功保存回话信息
            userLoginService.update(request.getSession(false).getId(), userName);
            userLoginService.expired(request.getSession(false).getId(), 20);
            return new ReturnValue<String>();
        } catch (Exception e) {
            log.error(e.getCause().getMessage());
        }

        return new ReturnValue<String>(ErrorCode.ERROR_SERVER_ERROR, "用户登陆失败");
    }

    @LoginRequired
    @PutMapping("/update")
    @ApiOperation(value = "更新用户信息", notes = "更新用户信息")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "body", dataType = "User", name = "user", value = "用户信息", required = true)})
    @Syslog(module = "用户", style = "更新", description = "更新用户信息")
    public ReturnValue<String> update(HttpServletRequest request, @RequestBody User user) {
        try {
            User temp = userService.findById(user.getId());
            if (CheckUtil.isNull(temp)) {
                return new ReturnValue<String>(ErrorCode.ERROR_NOT_FOUND, "该用户编号不存在!");
            }

            if (!CheckUtil.isNull(user.getPassword())) {
                user.setPassword(Base64Util.encodeBytes(user.getPassword().getBytes("utf-8")));
            }

            userService.update(user);
        } catch (Exception e) {
            log.debug("更新用户信息异常" + e.getMessage());
            return new ReturnValue<String>(ErrorCode.ERROR_SERVER_ERROR);
        }
        return new ReturnValue<String>();
    }

    /**
     * 获取用户登录信息
     *
     * @return
     */
    @LoginRequired
    @ApiOperation(value = "获取当前登录用户信息", notes = "获取当前登录用户信息")
    @ApiImplicitParams({})
    @GetMapping("/principal/info")
    @Syslog(module = "用户", style = "查询当前用户信息", description = "获取当前登录用户信息")
    public ReturnValue<User> getPrinInfo(HttpServletRequest request) {
        if (CheckUtil.isNull(request.getSession(false))) {
            return new ReturnValue<User>(ErrorCode.ERROR_NOT_LOGIN, "用户会话已过期");
        }
        String userName = userLoginService.find(request.getSession(false).getId());
        if (CheckUtil.isNull(userName)) {
            return new ReturnValue<User>(ErrorCode.ERROR_NOT_LOGIN, "用户会话已过期");
        }
        // 通过用户名获取用户信息
        User user = userService.findByUserName(userName);
        if (CheckUtil.isNull(user)) {
            return new ReturnValue<User>(ErrorCode.ERROR_SERVER_ERROR, "用户不存在");
        }
        return new ReturnValue<User>(user);
    }

    /**
     * 修改用户
     *
     * @param userName
     * @param oldPassword
     * @param newPassword
     * @return
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
        try {
            User user = userService.findByUserName(userName);
            if (CheckUtil.isNull(user)) {
                return new ReturnValue<String>(ErrorCode.ERROR_INVALID_PARAM, "用户不存在");
            }

            if (!Base64Util.encodeBytes(oldPassword.getBytes("utf-8")).equals(user.getPassword())) {
                return new ReturnValue<String>(ErrorCode.ERROR_INVALID_PARAM, "原密码不正确");
            }

            user.setPassword(Base64Util.encodeBytes(newPassword.getBytes("utf-8")));
            userService.update(user);
        } catch (Exception e) {
            return new ReturnValue<String>(ErrorCode.ERROR_SERVER_ERROR);
        }
        return new ReturnValue<String>();
    }

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

        try {
            List<User> userList = userService.findByAttributes(name, idCard, enabled, pageIndex, pageSize, orderProp, order);
            return new ReturnValue<List<User>>(userList);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ReturnValue<List<User>>(ErrorCode.ERROR_SERVER_ERROR);
        }
    }

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
        try {
            Long count = userService.findMaxByAttributes(name, idCard, enabled);
            return new ReturnValue<Long>(count);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ReturnValue<Long>(ErrorCode.ERROR_SERVER_ERROR);
        }
    }

    /**
     * 删除一个用户
     *
     * @param
     * @return
     */
//    @LoginRequired
//    @ApiOperation(value="删除一个用户", notes="")
//    @ApiImplicitParams({@ApiImplicitParam(name = "userName", value = "用户名", required = true, dataType = "String", paramType="path")})
//    @DeleteMapping(value = "/delete/{userName}")
//    @Syslog(module="用户",style="删除",description="删除用户信息")
//    public ReturnValue<String> delete(@PathVariable(name="userName", required=true) String userName){
//        try{
//            userService.deleteByUserName(userName);
//            return new ReturnValue<String>();
//        }catch(Exception e){
//            log.error(e.getCause().getMessage());
//            return new ReturnValue<String>(ErrorCode.ERROR_SERVER_ERROR);
//        }
//    }
    @LoginRequired
    @ApiOperation(value = "用户注销", notes = "用户注销")
    @GetMapping(value = "/logout")
    @Syslog(module = "用户", style = "登出", description = "用户登出")
    public ReturnValue<String> logout(HttpServletRequest request) {
        try {
            // 登陆成功保存回话信息
            userLoginService.delete(request.getSession(false).getId());
            return new ReturnValue<String>();
        } catch (Exception e) {
            log.error(e.getCause().getMessage());
        }
        return new ReturnValue<String>(ErrorCode.ERROR_SERVER_ERROR, "用户注销失败");
    }

}