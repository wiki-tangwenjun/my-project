package com.wenjun.busines.house.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
@Data
public class AppointmentHouse {
    private String id;

    private String userId;

    private String hourseId;
    @DateTimeFormat(pattern = "yyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyy-MM-dd HH:mm:ss")
    private Date seeTime;

    private String telphone;

    private Long onTime;
}
