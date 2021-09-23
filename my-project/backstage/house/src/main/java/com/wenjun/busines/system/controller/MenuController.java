package com.wenjun.busines.system.controller;

import com.wenjun.anno.Syslog;
import com.wenjun.busines.system.dto.MenuQueryParam;
import com.wenjun.busines.system.pojo.Menu;
import com.wenjun.busines.system.service.IMenuService;
import com.wenjun.handlerException.error.CommonEnum;
import com.wenjun.handlerException.error.ReturnValue;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @projectName: house
 * @package: com.wenjun.busines.system.controller
 * @className: MenuController
 * @author: wen jun tang
 * @description: 菜单前端控制器
 * @date: 2021年07月16日 17:38
 * @version: 1.0
 */
@Slf4j
@RequestMapping("/menu")
@RestController
@Api(value = "菜单相关操作接口", tags = "菜单相关操作接口")
public class MenuController {
    private IMenuService iMenuService;

    @GetMapping("/findByAttributes")
    @RequiresRoles(logical = Logical.OR, value = {"admin", "superAdmin"})
    @Syslog(module="菜单信息",style="查询",description="根据条件查询菜单信息")
    @ApiOperation(value="根据条件查询菜单信息", notes="根据条件查询菜单信息")
    public ReturnValue<List<Menu>> findByAttributes(MenuQueryParam menuQueryParam) {

        return new ReturnValue<>(iMenuService.findByAttributes(menuQueryParam));
    }

    @GetMapping("/findCountByAttributes")
    @RequiresRoles(logical = Logical.OR, value = {"admin", "superAdmin"})
    @Syslog(module="菜单信息",style="查询",description="根据条件查询菜单总数")
    @ApiOperation(value="根据条件查询菜单总数", notes="根据条件查询菜单总数")
    public ReturnValue<List<Menu>> findCountByAttributes(MenuQueryParam menuQueryParam) {

        return new ReturnValue<>(iMenuService.findByAttributes(menuQueryParam));
    }

    @PostMapping("/add")
    @RequiresRoles(logical = Logical.OR, value = {"admin", "superAdmin"})
    @Syslog(module="菜单信息",style="添加",description="添加菜单")
    @ApiOperation(value="添加菜单", notes="添加菜单")
    public ReturnValue<String> add(Menu menu) {
        iMenuService.add(menu);
        return new ReturnValue<>(CommonEnum.ERROR_SUCCESS);
    }

    @PutMapping("/update")
    @RequiresRoles(logical = Logical.OR, value = {"admin", "superAdmin"})
    @Syslog(module="菜单信息",style="修改",description="修改菜单")
    @ApiOperation(value="修改菜单", notes="修改菜单")
    public ReturnValue<String> update(Menu menu) {
        iMenuService.update(menu);
        return new ReturnValue<>(CommonEnum.ERROR_SUCCESS);
    }

    @PutMapping("/delete")
    @RequiresRoles(logical = Logical.OR, value = {"admin", "superAdmin"})
    @Syslog(module="菜单信息",style="删除",description="删除菜单")
    @ApiOperation(value="删除菜单", notes="删除菜单")
    public ReturnValue<String> delete(String id) {
        iMenuService.delete(id);
        return new ReturnValue<>(CommonEnum.ERROR_SUCCESS);
    }

}
