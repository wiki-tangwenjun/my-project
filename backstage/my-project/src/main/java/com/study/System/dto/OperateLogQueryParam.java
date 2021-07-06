package com.study.system.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.study.dto.PageDomain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @projectName: my-project
 * @package: com.study.system.dto
 * @className: OperateLogQueryParam
 * @author: wen jun tang
 * @description: 日志的查询条件
 * @date: 2021年07月06日 14:26
 * @version: 1.0
 */
@Data
@ApiModel("日志查询条件封装对象")
public class OperateLogQueryParam extends PageDomain implements Serializable {
    private static final long serialVersionUID = 1348699966832091477L;
    @ApiModelProperty(value = "用户名称")
    private String userName;
    @ApiModelProperty(value = "用户id")
    private String userId;
    @ApiModelProperty(value = "操作模块")
    private String module;
    @ApiModelProperty(value = "操作方式")
    private String style;
    @ApiModelProperty(value = "操作开始时间")
    @DateTimeFormat(pattern = "yyy-MM-dd")
    @JsonFormat(pattern = "yyy-MM-dd")
    private Date beginTime;
    @ApiModelProperty(value = "操作结束时间")
    @DateTimeFormat(pattern = "yyy-MM-dd")
    @JsonFormat(pattern = "yyy-MM-dd")
    private Date endTime;
    @ApiModelProperty(value = "排序字段")
    private String order;
    @ApiModelProperty(value = "排序方式（asc,desc）")
    private String orderProp;
}
