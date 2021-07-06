package com.study.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/***
* @author tang wen jun
* @date 2021/6/11 17:38
*/
@Data
public class User implements Serializable {
    private static final long serialVersionUID = 3445979952646637065L;
    private String id;

    private String password;

    private String personName;

    private String idNumber;

    private String telphone;

    private Byte loginWay;

    private Byte enabled;

    private Byte expired;

    private Byte locked;

    private Date createTime;

}
