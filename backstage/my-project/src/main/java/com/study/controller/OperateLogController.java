package com.study.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.study.anno.LoginRequired;
import com.study.anno.Syslog;
import com.study.error.ErrorCode;
import com.study.error.ReturnValue;
import com.study.pojo.OperateLog;
import com.study.service.OperateLogService;
import com.study.util.CheckUtil;
import com.study.util.TextUtil;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

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
        try {
        	if(CheckUtil.isNull(operateLog.getUserName())) {
        		return new ReturnValue<String>(ErrorCode.ERROR_INVALID_PARAM, "用户名称不能为空!");
        	}
        	
        	if(CheckUtil.isNull(operateLog.getId())) {
        		operateLog.setId(TextUtil.getUUID());
        	}
        	
            operateLogService.add(operateLog);
            return new ReturnValue<String>(operateLog.getId());
        }catch (Exception e) {
            log.error(e.getCause().getMessage());
            return new ReturnValue<String>(ErrorCode.ERROR_SERVER_ERROR, "添加操作日志失败");
        }
    }
    
    @LoginRequired
    @ApiOperation(value="删除操作日志信息", notes="通过编号删除操作日志")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "操作日志编号", required = true, dataType = "Long", paramType="path")})
    @DeleteMapping(value = "/delete/{id}")
    @Syslog(module="操作日志",style="删除",description="删除操作日志信息")
    public ReturnValue<String> delete(@PathVariable(name="id", required=true) String id){
        try{
        	OperateLog operateLog = operateLogService.findById(id);
        	if(CheckUtil.isNull(operateLog)) {
        		return new ReturnValue<String>(ErrorCode.ERROR_NOT_FOUND, "操作日志不存在!");
        	}
            operateLogService.delete(operateLog);
            return new ReturnValue<String>();
        }catch(Exception e){
            log.error(e.getCause().getMessage());
            return new ReturnValue<String>(ErrorCode.ERROR_SERVER_ERROR);
        }
    }

    @LoginRequired
    @PutMapping("/update")
    @ApiOperation(value="更新操作日志信息", notes="更新操作日志信息")
    @ApiImplicitParams({ @ApiImplicitParam(paramType = "body", dataType = "OperateLog", name = "operateLog", value = "操作日志信息", required = true) })
    @Syslog(module="操作日志",style="更新",description="更新操作日志信息")
    public ReturnValue<String> update( HttpServletRequest request,@RequestBody OperateLog operateLog) {
		try{
			OperateLog temp = operateLogService.findById(operateLog.getId());
			if(CheckUtil.isNull(temp)){
				return new ReturnValue<String>(ErrorCode.ERROR_NOT_FOUND, "该操作日志编号不存在!");
			}
			
			operateLogService.update(operateLog);

		}catch(Exception e){
			log.debug("更新操作日志信息异常" + e.getMessage());
			return new ReturnValue<String>(ErrorCode.ERROR_SERVER_ERROR);
		}
		return new ReturnValue<String>();
    }
    
    @LoginRequired
    @GetMapping("/findMaxByAttributes")
    @ApiOperation(value="获取操作日志总数", notes="根据属性获取操作日志总数")
    @ApiImplicitParams({
    	@ApiImplicitParam(paramType = "query", dataType = "String", name = "userName", value = "用户名", required = false),
    	@ApiImplicitParam(paramType = "query", dataType = "String", name = "userId", value = "用户编号", required = false),
    	@ApiImplicitParam(paramType = "query", dataType = "String", name = "module", value = "操作模块", required = false),
    	@ApiImplicitParam(paramType = "query", dataType = "String", name = "style", value = "操作方式", required = false),
    	@ApiImplicitParam(paramType = "query", dataType = "String", name = "beginTime", value = "操作开始时间", required = false),
    	@ApiImplicitParam(paramType = "query", dataType = "String", name = "endTime", value = "操作结束时间", required = false)
    	})
    @Syslog(module="操作日志",style="查询数据数",description="查询操作日志数量")
    public ReturnValue<Long> findMaxByAttributes(
    		@RequestParam(name="userName", required=false) String userName,
    		@RequestParam(name="userId", required=false) String userId,
    		@RequestParam(name="module", required=false) String module,
    		@RequestParam(name="style", required=false) String style,
    		@RequestParam(name="beginTime", required=false) Date beginTime,
    		@RequestParam(name="endTime", required=false) Date endTime
    		){
        try {
            Long count = operateLogService.findMaxByAttributes(userName, userId, module, style, beginTime, endTime);
            return new ReturnValue<Long>(count);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ReturnValue<Long>(ErrorCode.ERROR_SERVER_ERROR);
        }
    }

    @LoginRequired
    @GetMapping("/findByAttributes")
    @ApiOperation(value="获取操作日志信息列表", notes="分页查询操作日志信息列表")
    @ApiImplicitParams({ 
    	@ApiImplicitParam(paramType = "query", dataType = "String", name = "userName", value = "用户名", required = false),
    	@ApiImplicitParam(paramType = "query", dataType = "String", name = "userId", value = "用户编号", required = false),
    	@ApiImplicitParam(paramType = "query", dataType = "String", name = "module", value = "操作模块", required = false),
    	@ApiImplicitParam(paramType = "query", dataType = "String", name = "style", value = "操作方式", required = false),
    	@ApiImplicitParam(paramType = "query", dataType = "String", name = "beginTime", value = "操作开始时间", required = false),
    	@ApiImplicitParam(paramType = "query", dataType = "String", name = "endTime", value = "操作结束时间", required = false),
    		@ApiImplicitParam(paramType = "query", dataType = "Long", name = "pageIndex", value = "页索引", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "Long", name = "pageSize", value = "页大小", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "orderProp", value = "排序字段", required = false),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "order", value = "排序方式（asc,desc）", required = false)})
    @Syslog(module="操作日志",style="查询数据列表",description="查询操作日志信息列表")
    public ReturnValue<List<OperateLog>> findByAttributes(
										    		@RequestParam(name="userName", required=false) String userName,
										    		@RequestParam(name="userId", required=false) String userId,
										    		@RequestParam(name="module", required=false) String module,
										    		@RequestParam(name="style", required=false) String style,
										    		@RequestParam(name="beginTime", required=false) Date beginTime,
										    		@RequestParam(name="endTime", required=false) Date endTime,
            										@RequestParam(name="pageIndex",required=true) Long pageIndex,
                                                    @RequestParam(name="pageSize",required=true) Long pageSize,
                                                    @RequestParam(name="order", required=false) String order,
                                                    @RequestParam(name="orderProp", required=false) String orderProp
                                                    ){

        try {
            List<OperateLog> operateLogList = operateLogService.findByAttributes(userName, userId, module, style, beginTime, endTime, pageIndex, pageSize, orderProp, order);
            return new ReturnValue<List<OperateLog>>(operateLogList);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ReturnValue<List<OperateLog>>(ErrorCode.ERROR_SERVER_ERROR);
        }
    }

}