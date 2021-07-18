package com.wenjun.busines.system.controller;

import com.wenjun.anno.Syslog;
import com.wenjun.busines.system.dto.RoleQueryParam;
import com.wenjun.busines.system.pojo.Role;
import com.wenjun.busines.system.service.IRoleService;
import com.wenjun.handlerException.error.ReturnValue;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @projectName: house
 * @package: com.wenjun.busines.system.controller
 * @className: RoleController
 * @author: wen jun tang
 * @description: 角色信息前端控制器
 * @date: 2021年07月16日 17:37
 * @version: 1.0
 */
@Slf4j
@RestController
@RequestMapping("/role")
@Api(value = "角色相关操作接口", tags = "角色相关操作接口")
public class RoleController {
    @Resource
    private IRoleService iRoleService;

    @GetMapping("/findById")
    @RequiresRoles(logical = Logical.OR, value = {"superAdmin"})
    @ApiOperation(value="根据角色id查询角色信息", notes="根据角色id查询角色信息")
    @Syslog(module="角色信息",style="查询",description="根据角色id查询角色信息")
    public ReturnValue<Role> findById(String id) {
        Role role = iRoleService.findById(id);
        return new ReturnValue<>(role);
    }

    @GetMapping("/findCountByAttributes")
    @RequiresRoles(logical = Logical.OR, value = {"superAdmin"})
    @ApiOperation(value="根据角色id查询角色信息", notes="根据角色id查询角色信息")
    @Syslog(module="角色信息",style="查询",description="根据角色id查询角色信息")
    public ReturnValue<Integer> findCountByAttributes(RoleQueryParam roleQueryParam) {
        return new ReturnValue<>(iRoleService.findCountByAttributes(roleQueryParam));
    }

    @GetMapping("/findByAttributes")
    @RequiresRoles(logical = Logical.OR, value = {"superAdmin"})
    @ApiOperation(value="根据角色id查询角色信息", notes="根据角色id查询角色信息")
    @Syslog(module="角色信息",style="查询",description="根据角色id查询角色信息")
    public ReturnValue<List<Role>> findByAttributes(RoleQueryParam roleQueryParam) {
        List<Role> roles = iRoleService.findByAttributes(roleQueryParam);
        return new ReturnValue<>(roles);
    }


}
