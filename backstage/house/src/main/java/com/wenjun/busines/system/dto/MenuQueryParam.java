package com.wenjun.busines.system.dto;

import com.wenjun.dto.PageDomain;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @projectName: house
 * @package: com.wenjun.busines.system.dto
 * @className: MenuQueryParam
 * @author: tang wen jun
 * @description: 菜单管理查询条件对象
 * @date: 2021年07月18日 21:37
 * @version: 1.0
 */
@Data
@Api("查询条件封装对象")
public class MenuQueryParam extends PageDomain implements Serializable {
    private static final long serialVersionUID = 5311608608052348498L;

    @ApiModelProperty("菜单名称")
    private String name;
    @ApiModelProperty("0目录 1菜单")
    private Integer folderOrMenu;
    @ApiModelProperty("是否停用 0正常 1停用")
    private Integer status;

}
