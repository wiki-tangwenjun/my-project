package com.wenjun.busines.system.controller;

import com.wenjun.anno.Syslog;
import com.wenjun.busines.system.pojo.Menu;
import com.wenjun.busines.system.pojo.RoleMenu;
import com.wenjun.busines.system.service.IMenuService;
import com.wenjun.busines.system.service.IRoleMenuService;
import com.wenjun.handlerException.error.CommonEnum;
import com.wenjun.handlerException.error.ReturnValue;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @projectName: house
 * @package: com.wenjun.busines.system.controller
 * @className: RoleMenuController
 * @author: tang wen jun
 * @description: 角色菜单前端控制器
 * @date: 2021年07月18日 23:59
 * @version: 1.0
 */
@RestController
@RequestMapping("/roleMenu")
@Api(value = "角色菜单相关接口", tags = "角色菜单相关接口")
public class RoleMenuController {
    @Resource
    private IRoleMenuService iRoleMenuService;

    @Resource
    private IMenuService iMenuService;

    @GetMapping("/findByAttributes")
    @RequiresRoles(logical = Logical.OR, value = {"admin", "superAdmin"})
    @Syslog(module="角色菜单信息",style="查询",description="根据角色id查询角色菜单信息")
    @ApiOperation(value="根据角色id查询角色菜单信息", notes="根据角色id查询角色菜单信息")
    public ReturnValue<List<Menu>> findByAttributes (String roleId) {
        return new ReturnValue<>(iMenuService.findByRoleId(roleId));
    }

    @PostMapping("/add")
    @RequiresRoles(logical = Logical.OR, value = {"admin", "superAdmin"})
    @Syslog(module="添加角色菜单信息",style="查询",description="添加角色菜单信息")
    @ApiOperation(value="添加角色菜单信息", notes="添加角色菜单信息")
    public ReturnValue<String> add (RoleMenu roleMenu) {
        iRoleMenuService.add(roleMenu);
        return new ReturnValue<>(CommonEnum.ERROR_SUCCESS);
    }

    @DeleteMapping("/delete")
    @RequiresRoles(logical = Logical.OR, value = {"admin", "superAdmin"})
    @Syslog(module="删除角色菜单信息",style="查询",description="删除角色菜单信息")
    @ApiOperation(value="删除角色菜单信息", notes="删除角色菜单信息")
    public ReturnValue<String> delete (String roleId, String menuId) {
        iRoleMenuService.deleteRoleMenu(roleId, menuId);
        return new ReturnValue<>(CommonEnum.ERROR_SUCCESS);
    }

    @PutMapping("/update")
    @RequiresRoles(logical = Logical.OR, value = {"admin", "superAdmin"})
    @Syslog(module="修改角色菜单信息",style="查询",description="修改角色菜单信息")
    @ApiOperation(value="修改角色菜单信息", notes="修改角色菜单信息")
    public ReturnValue<String> update (String roleId, String menuId) {
        iRoleMenuService.updateRoleMenuSelective(roleId, menuId);
        return new ReturnValue<>(CommonEnum.ERROR_SUCCESS);
    }

}
