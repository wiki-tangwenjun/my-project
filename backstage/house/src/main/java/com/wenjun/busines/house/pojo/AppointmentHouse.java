package com.wenjun.busines.house.pojo;

import lombok.Data;

import java.util.Date;
@Data
public class AppointmentHouse {
    private String id;

    private String userId;

    private String hourseId;

    private Date seeTime;

    private String telphone;

    private Long onTime;
}
