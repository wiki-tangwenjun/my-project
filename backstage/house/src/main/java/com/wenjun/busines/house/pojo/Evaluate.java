package com.wenjun.busines.house.pojo;

import lombok.Data;

@Data
public class Evaluate {
    private String id;

    private String userId;

    private String hourseId;

    private Long onTime;

    private Long userOnTime;

    private Long attitude;

    private Long userAttitude;

    private Long authenticity;

    private String remark;


}
