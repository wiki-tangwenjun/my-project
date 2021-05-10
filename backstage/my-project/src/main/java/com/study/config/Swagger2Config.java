package com.study.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @文件名：Swagger2Config.java
 * @功  能：swagger文档配置
 * @作  者：唐文军
 * @交流群：961179337(QQ群)
 * @日  期：2021/5/10
 * @版  本：1.0.0.1
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {
    @Bean
    /**
     *     创建API基本信息
     */
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                // 扫描该包下的所有需要在Swagger中展示的API，@ApiIgnore注解标注的除外
                .apis(RequestHandlerSelectors.basePackage("com.dondown.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     *     创建API的基本信息，这些信息会在Swagger UI中进行显示
      */
    @SuppressWarnings("deprecation")
	private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("廉政档案接口文档")
                .description("提供廉政档案数据相关接口")
                .contact("www.dondown.com")
                .version("1.0.0.1")
                .build();
    }
}