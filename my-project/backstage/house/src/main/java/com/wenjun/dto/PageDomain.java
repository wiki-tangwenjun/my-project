package com.wenjun.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 分页数据
 *
 * @author gobon
 */
@Data
@ApiModel(description = "分页参数")
public class PageDomain
{
    /** 当前记录起始索引 */
    @ApiModelProperty(value = "当前记录起始索引")
    private Integer pageNum;

    /** 每页显示记录数 */
    @ApiModelProperty(value = "每页显示记录数")
    private Integer pageSize;

    /** 排序列 */
    private String orderByColumn;

    /** 排序的方向desc或者asc */
    private String isAsc = "asc";
}
