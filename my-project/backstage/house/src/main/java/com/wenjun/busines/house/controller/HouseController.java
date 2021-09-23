package com.wenjun.busines.house.controller;

import com.wenjun.anno.Syslog;
import com.wenjun.busines.house.dto.HouseAddParam;
import com.wenjun.busines.house.dto.HouseQueryParam;
import com.wenjun.busines.house.pojo.House;
import com.wenjun.busines.house.service.IHouseService;
import com.wenjun.handlerException.error.CommonEnum;
import com.wenjun.handlerException.error.ReturnValue;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
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

    @GetMapping("/findByAttributes")
    @ApiOperation(value="查找出租屋列表", notes="查找出租屋列表")
    @Syslog(module="出租屋信息",style="查询",description="查找出租屋列表")
    @RequiresRoles(logical = Logical.OR, value = {"landlord", "admin", "superAdmin"})
    public ReturnValue<List<House>> findByAttributes(HouseQueryParam houseQueryParam){

        return new ReturnValue<>(iHouseService.findByAttributes(houseQueryParam));
    }

    @GetMapping("/findCountByAttributes")
    @ApiOperation(value="查找出租屋列表总数", notes="查找出租屋列表总数")
    @Syslog(module="出租屋信息",style="查询",description="查找出租屋列表总数")
    @RequiresRoles(logical = Logical.OR, value = {"landlord", "admin", "superAdmin"})
    public ReturnValue<Integer> findCountByAttributes(HouseQueryParam houseQueryParam){

        return new ReturnValue<>(iHouseService.findCountByAttributes(houseQueryParam));
    }

    @GetMapping("/findById")
    @ApiOperation(value="根据id查找出租屋", notes="根据id查找出租屋")
    @Syslog(module="出租屋信息",style="查询",description="根据id查找出租屋")
    @RequiresRoles(logical = Logical.OR, value = {"landlord", "admin", "superAdmin"})
    public ReturnValue<House> findById(String id){

        return new ReturnValue<>(iHouseService.findById(id));
    }

    @PostMapping("/add")
    @ApiOperation(value="添加出租屋", notes="添加出租屋")
    @Syslog(module="出租屋信息",style="添加",description="添加出租屋")
    @RequiresRoles(logical = Logical.OR, value = {"landlord", "superAdmin"})
    public ReturnValue<String> add(HttpServletRequest request, @Valid HouseAddParam houseAddParam) throws Exception {
        iHouseService.insert(request, houseAddParam);

        return new ReturnValue<>(CommonEnum.ERROR_SUCCESS);
    }

    @PutMapping("/update")
    @ApiOperation(value="修改出租屋", notes="修改出租屋")
    @Syslog(module="出租屋信息",style="修改",description="修改出租屋")
    @RequiresRoles(logical = Logical.OR, value = {"landlord", "superAdmin"})
    public ReturnValue<String> update(House house){
        iHouseService.update(house);

        return new ReturnValue<>(CommonEnum.ERROR_SUCCESS);
    }

    @PutMapping("/deleteById")
    @ApiOperation(value="删除出租屋", notes="删除出租屋")
    @Syslog(module="出租屋信息",style="修改",description="删除出租屋")
    @RequiresRoles(logical = Logical.OR, value = {"landlord", "superAdmin"})
    public ReturnValue<String> deleteById(String id){
        iHouseService.delete(id);

        return new ReturnValue<>(CommonEnum.ERROR_SUCCESS);
    }

}
