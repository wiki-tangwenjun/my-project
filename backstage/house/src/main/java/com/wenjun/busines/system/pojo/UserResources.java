package com.wenjun.busines.system.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @projectName: my-project
 * @package: com.study.system.pojo
 * @className: UserResources
 * @author: wen jun tang
 * @description: 用户角色资源
 * @date: 2021年07月06日 16:46
 * @version: 1.0
 */
@Data
@ApiModel("用户角色资源封装对象")
public class UserResources implements Serializable {
    private static final long serialVersionUID = 4496274308613062789L;

    @ApiModelProperty("用户信息")
    private User user;
    @ApiModelProperty("用户拥有的角色信息")
    private List<Role> userRole;

}
