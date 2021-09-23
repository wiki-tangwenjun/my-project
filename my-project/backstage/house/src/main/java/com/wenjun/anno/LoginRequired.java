package com.wenjun.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 86133
 * @文件名：LoginRequired.java
 * @功  能：
 * @作  者：tang wen jun
 * @日  期：2021/5/11
 * @版  本：1.0.0.1
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginRequired {
    boolean loginSuccess() default true;
}
