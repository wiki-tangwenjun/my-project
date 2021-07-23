package com.wenjun.busines.house.controller;

import com.wenjun.anno.Syslog;
import com.wenjun.busines.house.dto.EvaluateParam;
import com.wenjun.busines.house.pojo.Evaluate;
import com.wenjun.busines.house.service.IEvaluateService;
import com.wenjun.handlerException.error.ReturnValue;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @projectName: house
 * @package: com.wenjun.busines.house.controller
 * @className: EvaluateController
 * @author: wen jun tang
 * @description: 评价前端控制器
 * @date: 2021年07月20日 11:24
 * @version: 1.0
 */
@RestController
@RequestMapping("/evaluate")
@Api(value = "评价相关接口", tags = "租房看房评价")
public class EvaluateController {

    @Resource
    private IEvaluateService iEvaluateService;

    @RequestMapping("/findByAttributes")
    @ApiOperation(value="删除出租屋", notes="删除出租屋")
    @Syslog(module="出租屋信息",style="修改",description="删除出租屋")
    @RequiresRoles(logical = Logical.OR, value = {"landlord", "admin", "superAdmin"})
    public ReturnValue<List<Evaluate>> findByAttributes(EvaluateParam evaluateParam) {

        return new ReturnValue<>(iEvaluateService.findByAttributes(evaluateParam));
    }
}
