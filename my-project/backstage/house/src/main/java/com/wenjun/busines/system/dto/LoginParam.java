package com.wenjun.busines.system.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @projectName: house
 * @package: com.wenjun.busines.system.dto
 * @className: LoginParam
 * @author: wen jun tang
 * @description: 登录参数封装对象
 * @date: 2021年07月14日 17:37
 * @version: 1.0
 */
@Data
@ApiModel("登录参数封装对象")
public class LoginParam implements Serializable {
    private static final long serialVersionUID = 7904116336544010299L;

    @ApiModelProperty(value = "账号")
    @NotBlank(message = "账号不能为空")
    private String userName;

    @ApiModelProperty(value = "密码")
    @NotBlank(message = "密码不能为空")
    private String password;

    @ApiModelProperty(value = "校验码")
    @NotBlank(message = "校验码不能为空")
    private String code;
}
