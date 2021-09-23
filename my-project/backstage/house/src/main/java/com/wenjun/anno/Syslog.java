package com.wenjun.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 日志注解
 * @author tangwenjun
 * @date 2021/5/10
 */
@Target(ElementType.METHOD) //注解放置的目标位置,METHOD是可注解在方法级别上
@Retention(RetentionPolicy.RUNTIME) //注解在哪个阶段执行
public @interface Syslog {
	 String module() default "";
	 String style() default "";
	 String description() default "";
	 String param() default "";
}
