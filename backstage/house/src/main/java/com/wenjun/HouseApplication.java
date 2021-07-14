package com.wenjun;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
* @author wen jun tang
* @date 2021/7/14 11:35
*/
@SpringBootApplication
@MapperScan("com.wenjun.busines.*.mapper")
public class HouseApplication {

    public static void main(String[] args) {

        SpringApplication.run(HouseApplication.class, args);
    }

}
