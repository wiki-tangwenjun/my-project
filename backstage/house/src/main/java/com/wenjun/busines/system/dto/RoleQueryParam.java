package com.wenjun.busines.system.dto;

import com.wenjun.dto.PageDomain;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @projectName: house
 * @package: com.wenjun.busines.system.dto
 * @className: RoleQueryParam
 * @author: wen jun tang
 * @description: 角色查询参数封装对象
 * @date: 2021年07月16日 18:13
 * @version: 1.0
 */
@Data
public class RoleQueryParam extends PageDomain implements Serializable {
    private static final long serialVersionUID = -8949586766986953634L;
    @ApiModelProperty("角色中文别名")
    private String name;
    @ApiModelProperty("角色名称")
    private String roleKey;
    @ApiModelProperty("排序")
    private Integer level;
    @ApiModelProperty("角色状态 0正常 1停用")
    private Integer status;
}
