package com.wenjun.busines.system.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @projectName: house
 * @package: com.wenjun.busines.system.pojo
 * @className: AddUserParam
 * @author: wen jun tang
 * @description: 增加角色参数封装对象
 * @date: 2021年07月16日 15:36
 * @version: 1.0
 */
@Data
public class AddUserParam implements Serializable {
    private static final long serialVersionUID = -4194763186472282190L;

    @ApiModelProperty("用户信息")
    private User user;
    @ApiModelProperty("角色id")
    private String roleId;

}
