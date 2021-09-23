package com.wenjun.busines.system.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @projectName: house
 * @package: com.wenjun.busines.system.pojo
 * @className: IService
 * @author: wen jun tang
 * @description: 日志封装对象
 * @date: 2021年07月14日 14:36
 * @version: 1.0
 */
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
