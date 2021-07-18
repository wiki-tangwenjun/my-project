package com.wenjun.busines.system.pojo;

import lombok.Data;

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

    private Date createTime;
}
