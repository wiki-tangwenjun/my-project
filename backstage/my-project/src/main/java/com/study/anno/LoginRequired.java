package com.study.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @文件名：LoginRequired.java
 * @功  能：
 * @作  者：lixx2048@163.com
 * @交流群：961179337(QQ群)
 * @日  期：2020/8/27
 * @版  本：1.0.0.1
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginRequired {
    boolean loginSuccess() default true;
}
