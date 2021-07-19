package com.wenjun.busines.house.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wenjun.dto.PageDomain;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @projectName: house
 * @package: com.wenjun.busines.house.dto
 * @className: HouseQueryParam
 * @author: wen jun tang
 * @description: 出租屋查询条件
 * @date: 2021年07月19日 17:29
 * @version: 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class HouseQueryParam extends PageDomain implements Serializable {
    private static final long serialVersionUID = 4838679124002475733L;

    @ApiModelProperty("房东姓名")
    private String userName;
    @ApiModelProperty("出租屋公寓名称")
    private String name;
    @ApiModelProperty("出租屋楼多高")
    private Integer floorTop;
    @ApiModelProperty("起租时间 0.5年")
    private String starTime;
    @ApiModelProperty("是否有电梯 0 没有 1 有")
    private Integer elevator;
    @ApiModelProperty("宠物 0 没有 1 有")
    private Integer pets;
    @ApiModelProperty("做饭 0 没有 1 有")
    private Integer cook;
    @ApiModelProperty("洗衣机 0 没有 1 有")
    private Integer machine;
    @ApiModelProperty("空调 0 没有 1 有")
    private Integer conditioner;
    @ApiModelProperty("热水器 0 没有 1 有")
    private Integer heater;
    @ApiModelProperty("干净 0 没有 1 有")
    private Integer clean;
    @ApiModelProperty("房屋评分 默认80分，随着浏览量高而自动加分 同理反之，每周更新一次")
    private float houseScore;
    @ApiModelProperty("房屋所在省份")
    private String province;
    @ApiModelProperty("房屋所在城市具体位置")
    private String cityStreet;
    @ApiModelProperty("房子状态 0待出租 1已出租 2正在装修 3合同纠纷 4其他")
    private Integer status;
    @ApiModelProperty("房屋发布开始时间")
    @DateTimeFormat(pattern = "yyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyy-MM-dd HH:mm:ss")
    private Date starDate;
    @ApiModelProperty("房屋发布结束时间")
    @DateTimeFormat(pattern = "yyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyy-MM-dd HH:mm:ss")
    private Date endDate;

}
