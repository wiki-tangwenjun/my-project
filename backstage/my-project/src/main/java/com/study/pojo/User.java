package com.study.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class User {
    private String id;

    private String userName;

    private String password;

    private String name;

    private String idCard;

    private Byte enabled;

    private Date createTime;

    private String caKey;

    private String reserver1;

    private String reserver2;


}