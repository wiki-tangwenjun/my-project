package com.study.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

/**
 * @文件名称: SpringUtil.java
 * @功能描述: TODO(获取spring容器上下文工具-单例)
 * @版权信息： www.dondown.com
 * @编写作者： lixx2048@163.com
 * @开发日期： 2020年3月26日
 * @历史版本： V1.0
 */
public class SpringUtil {
	private static ApplicationContext applicationContext = null;

    public static void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if(SpringUtil.applicationContext == null){
            SpringUtil.applicationContext  = applicationContext;
        }
    }

    // 获取applicationContext
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    // 通过name获取 Bean.
    public static Object getBean(String name){
        return getApplicationContext().getBean(name);

    }

    // 通过class获取Bean.
    public static <T> T getBean(Class<T> clazz){
        return getApplicationContext().getBean(clazz);
    }

    // 通过name,以及Clazz返回指定的Bean
    public static <T> T getBean(String name,Class<T> clazz){
        return getApplicationContext().getBean(name, clazz);
    }
}
