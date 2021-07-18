package com.wenjun.busines.system.dto;

import com.wenjun.dto.PageDomain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @projectName: my-project
 * @package: com.study.system.dto
 * @className: UserQueryParam
 * @author: wen jun tang
 * @description: 用户查询参数封装对象
 * @date: 2021年07月06日 14:43
 * @version: 1.0
 */

@Data
@ApiModel("用户查询参数封装对象")
public class UserQueryParam extends PageDomain implements Serializable {
    private static final long serialVersionUID = 7904116336544010299L;
    @ApiModelProperty(value = "用户姓名")
    private String name;
    @ApiModelProperty(value = "用户身份证")
    private String idCard;
    @ApiModelProperty(value = "可用性")
    private Long enabled;
    @ApiModelProperty(value = "排序字段")
    private String order;
    @ApiModelProperty(value = "排序方式（asc,desc）")
    private String orderProp;

}
