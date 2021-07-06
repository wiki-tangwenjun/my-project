package com.study.system.pojo;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class OperateLog {
    private String id;
    private String userName;
    private String userId;
    private String name;
    private String module;
    private String style;
    private String url;
    private String description;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date operateTime;
    private String operand;
    private String result;
}
