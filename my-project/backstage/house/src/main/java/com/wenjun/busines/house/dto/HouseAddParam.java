package com.wenjun.busines.house.dto;

import com.wenjun.busines.house.pojo.House;
import com.wenjun.busines.house.pojo.HouseEnclosure;
import com.wenjun.busines.house.pojo.HouseLog;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

/**
 * @projectName: house
 * @package: com.wenjun.busines.house.dto
 * @className: HouseAddParam
 * @author: wen jun tang
 * @description: 添加出租屋信息参数-封装对象
 * @date: 2021年07月20日 10:21
 * @version: 1.0
 */
@Data
public class HouseAddParam implements Serializable {
    private static final long serialVersionUID = -4423215939198572458L;

    @ApiModelProperty("添加出租屋信息")
    private House house;
    @ApiModelProperty("出租屋操作日志")
    @NotBlank(message = "操作日志不能为空")
    private HouseLog houseLog;
    @ApiModelProperty("出租屋附件列表")
    @NotBlank(message = "附件不能为空")
    private List<HouseEnclosure> houseEnclosure;
}
