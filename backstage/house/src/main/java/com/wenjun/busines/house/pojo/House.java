package com.wenjun.busines.house.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
@Data
public class House {
    private String id;

    private String userId;

    private String name;

    private Long floor;

    private Long floorTop;

    private String starTime;

    private Long elevator;

    private Long pets;

    private Long cook;

    private Long machine;

    private Long conditioner;

    private Long heater;

    private Long clean;

    private Float houseScore;

    private String province;

    private String provinceCode;

    private String cityStreet;

    private String cityCode;

    private Long status;

    private String longitude;

    private String latitude;

    @DateTimeFormat(pattern = "yyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyy-MM-dd HH:mm:ss")
    private Date createTime;

    @DateTimeFormat(pattern = "yyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyy-MM-dd HH:mm:ss")
    private Date updateTime;

    private String remark;

    private String reserve1;

}
