package com.wenjun.busines.system.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class User {
    private String id;

    private String userName;

    private String password;

    private String personName;

    private String idNumber;

    private String telphone;

    private Byte loginWay;

    private Float score;

    private Byte enabled;

    private Byte expired;

    private Byte locked;
    @DateTimeFormat(pattern = "yyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyy-MM-dd HH:mm:ss")
    private Date createTime;
}
