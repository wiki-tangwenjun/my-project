package com.wenjun.busines.system.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author wen jun tang
 */
@Data
public class Role {
    private String id;

    private String name;

    private Byte level;

    private Byte status;

    private String remark;

    @ApiModelProperty("角色拥有的菜单")
    private List<Menu> roleMenu;
}
