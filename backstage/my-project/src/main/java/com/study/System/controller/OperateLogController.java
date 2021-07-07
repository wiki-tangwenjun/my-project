package com.study.system.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.study.anno.LoginRequired;
import com.study.anno.Syslog;
import com.study.error.CommonEnum;
import com.study.error.ReturnValue;
import com.study.system.dto.OperateLogQueryParam;
import com.study.system.pojo.OperateLog;
import com.study.system.service.OperateLogService;
import com.study.util.CheckUtil;
import com.study.util.TextUtil;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/operateLog")
@Api(value = "操作日志管理接口列表", tags = "提供操作日志管理相关接口")
public class OperateLogController {
    @Resource
    private OperateLogService operateLogService;

    @LoginRequired
    @ApiOperation(value="添加操作日志信息", notes="添加操作日志信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "operateLog", value = "操作日志信息", required = true, dataType = "OperateLog", paramType="body")})
    @PostMapping(value = "/add")
    @Syslog(module="操作日志",style="添加",description="添加操作日志信息")
    public ReturnValue<String> add(HttpServletRequest request, @RequestBody OperateLog operateLog){
        if(CheckUtil.isNull(operateLog.getUserName())) {
            return new ReturnValue<String>(CommonEnum.ERROR_INVALID_PARAM, "用户名称不能为空!");
        }

        if(CheckUtil.isNull(operateLog.getId())) {
            operateLog.setId(TextUtil.getUUID());
        }

        operateLogService.add(operateLog);

        return new ReturnValue<>(operateLog.getId());
    }

    @LoginRequired
    @ApiOperation(value="删除操作日志信息", notes="通过编号删除操作日志")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "操作日志编号", required = true, dataType = "Long", paramType="path")})
    @DeleteMapping(value = "/delete/{id}")
    @Syslog(module="操作日志",style="删除",description="删除操作日志信息")
    public ReturnValue<String> delete(@PathVariable(name="id", required=true) String id){
        OperateLog operateLog = operateLogService.findById(id);
        if(CheckUtil.isNull(operateLog)) {
            return new ReturnValue<String>(CommonEnum.ERROR_NOT_FOUND, "操作日志不存在!");
        }
        operateLogService.delete(operateLog);

        return new ReturnValue<String>();
    }

    @LoginRequired
    @PutMapping("/update")
    @ApiOperation(value="更新操作日志信息", notes="更新操作日志信息")
    @ApiImplicitParams({ @ApiImplicitParam(paramType = "body", dataType = "OperateLog", name = "operateLog", value = "操作日志信息", required = true) })
    @Syslog(module="操作日志",style="更新",description="更新操作日志信息")
    public ReturnValue<String> update( HttpServletRequest request,@RequestBody OperateLog operateLog) {

        OperateLog temp = operateLogService.findById(operateLog.getId());
        if(CheckUtil.isNull(temp)){
            return new ReturnValue<String>(CommonEnum.ERROR_NOT_FOUND, "该操作日志编号不存在!");
        }
        operateLogService.update(operateLog);

		return new ReturnValue<String>();
    }

    @LoginRequired
    @GetMapping("/findByAttributes")
    @ApiOperation(value="获取操作日志信息列表", notes="分页查询操作日志信息列表")
    @Syslog(module="操作日志",style="查询数据列表",description="查询操作日志信息列表")
    public ReturnValue<List<OperateLog>> findByAttributes(OperateLogQueryParam operateLogQueryParam){
        List<OperateLog> operateLogList = operateLogService.findByAttributes(operateLogQueryParam);
        return new ReturnValue<>(operateLogList);
    }

}