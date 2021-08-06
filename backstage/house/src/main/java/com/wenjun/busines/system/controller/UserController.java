package com.wenjun.busines.system.controller;

import com.wenjun.anno.Syslog;
import com.wenjun.busines.system.dto.LoginParam;
import com.wenjun.busines.system.pojo.AddUserParam;
import com.wenjun.busines.system.pojo.User;
import com.wenjun.busines.system.pojo.UserResources;
import com.wenjun.busines.system.pojo.UserRole;
import com.wenjun.busines.system.service.IUserRoleService;
import com.wenjun.busines.system.service.UserService;
import com.wenjun.handlerException.error.CommonEnum;
import com.wenjun.handlerException.error.ReturnValue;
import com.wenjun.redis.UserLoginService;
import com.wenjun.util.CheckUtil;
import com.wenjun.util.ServletHttpRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.*;

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
    @Resource
    private IUserRoleService iUserRoleService;

    public static final String RANDOMKEY = "randomCode";

    @GetMapping("/login")
    @ApiOperation(value="登录接口", notes="账号密码 随机数登录")
    @Syslog(module="用户信息",style="查询",description="账号密码 随机数登录")
    public ReturnValue<String> login(@Valid LoginParam loginParam) throws Exception {
        return new ReturnValue<>(CommonEnum.ERROR_SUCCESS, userService.findByUserName(loginParam));
    }

    @GetMapping("/getVerificationCode")
    @ApiOperation(value="获取随机码接口", notes="随机数接口")
    @Syslog(module="用户信息",style="查询",description="获取随机码")
    public ReturnValue<String> getVerificationCode() {
        userLoginService.delete(RANDOMKEY);
        userLoginService.setKey(RANDOMKEY, String.valueOf((int) ((Math.random() * 9 + 1) * 10000)), 60);

        return new ReturnValue<>(CommonEnum.ERROR_SUCCESS, userLoginService.getKey(RANDOMKEY));
    }

    @GetMapping("/getUserResources")
    @RequiresRoles(logical = Logical.OR, value = {"user", "landlord", "admin", "superAdmin"})
    @Syslog(module="用户信息",style="查询",description="查询用户角色权限信息")
    @ApiOperation(value="根据token获取用户角色权限信息接口", notes="根据token获取用户角色权限信息")
    public ReturnValue<UserResources> getUserResources(HttpServletRequest request) throws Exception {
        return new ReturnValue<>(userService.findByUserResource(ServletHttpRequest.getToken(request)));
    }

    @PostMapping("/add")
    @RequiresRoles(logical = Logical.OR, value = {"user", "landlord", "admin", "superAdmin"})
    @Syslog(module="用户信息",style="新增",description="新增用户")
    @ApiOperation(value="新增用户信息接口", notes="新增用户信息接口")
    public ReturnValue<String> add(AddUserParam addUserParam) {
        userService.insert(addUserParam);

        return new ReturnValue<>(CommonEnum.ERROR_SUCCESS);
    }

    @PostMapping("/addUserRole")
    @RequiresRoles(logical = Logical.OR, value = {"admin", "superAdmin"})
    @Syslog(module="用户信息",style="新增",description="用户新增角色")
    @ApiOperation(value="用户新增角色接口", notes="用户新增角色接口")
    public ReturnValue<String> addUserRole(UserRole userRole) {
        iUserRoleService.add(userRole);

        return new ReturnValue<>(CommonEnum.ERROR_SUCCESS);
    }

    @DeleteMapping("/deleteUserRole")
    @RequiresRoles(logical = Logical.OR, value = {"superAdmin"})
    @Syslog(module="用户信息",style="删除",description="删除用户角色")
    @ApiOperation(value="删除用户角色接口", notes="删除用户角色接口")
    public ReturnValue<String> deleteUserRole(String userId, String roleId) {
        iUserRoleService.deleteByAttributes(userId, roleId);
        return new ReturnValue<>(CommonEnum.ERROR_SUCCESS);
    }

    @PutMapping("/update")
    @RequiresRoles(logical = Logical.OR, value = {"user", "landlord", "admin", "superAdmin"})
    @Syslog(module="用户信息",style="修改",description="修改用户信息")
    @ApiOperation(value="修改用户信息接口", notes="修改用户信息接口")
    public ReturnValue<String> updateUser(User user) {
        userService.update(user);

        return new ReturnValue<>(CommonEnum.ERROR_SUCCESS);
    }

    @DeleteMapping("/deleteUser")
    @RequiresRoles(logical = Logical.OR, value = {"superAdmin"})
    @Syslog(module="用户信息",style="删除",description="删除用户")
    @ApiOperation(value="删除用户", notes="删除用户")
    public ReturnValue<String> deleteUser(String userId) {
        userService.delete(userId);

        return new ReturnValue<>(CommonEnum.ERROR_SUCCESS);
    }
}
