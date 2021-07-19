package com.wenjun.busines.house.controller;

import com.wenjun.anno.Syslog;
import com.wenjun.busines.house.pojo.House;
import com.wenjun.busines.house.service.IHouseService;
import com.wenjun.handlerException.error.ReturnValue;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @projectName: house
 * @package: com.wenjun.busines.house.controller
 * @className: HouseController
 * @author: wen jun tang
 * @description: 出租屋接口前端控制器
 * @date: 2021年07月19日 18:26
 * @version: 1.0
 */
@RestController
@RequestMapping("/house")
@Api(value = "出租屋相关接口",tags = "出租屋相关接口")
public class HouseController {

    @Resource
    private IHouseService iHouseService;

    @GetMapping("/findByUserId")
    @ApiOperation(value="查找我的出租屋列表", notes="查找我的出租屋列表")
    @Syslog(module="出租屋信息",style="查询",description="查找我的出租屋列表")
    @RequiresRoles(logical = Logical.OR, value = {"landlord", "admin", "superAdmin"})
    public ReturnValue<List<House>> findByUserId(@NotBlank(message = "不能为空") String userId){

        return new ReturnValue<>(iHouseService.findByUserId(userId));
    }



}
