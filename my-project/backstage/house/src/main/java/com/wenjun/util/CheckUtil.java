package com.wenjun.util;

import java.util.Collection;

/**
 * @author wen jun tang
 * @date 2021/7/14 11:45
 */
public class CheckUtil {
	/**
	 * 检查字符串是否为空或null
	 * @param value
	 * @return
	 */
	public static boolean isNull(String value){
        return null == value || "".equals(value.trim());
    }

	/**
	 * 检查容器是否为空或null
	 * @param value
	 * @return
	 */
	public static boolean isNull(Collection<?> value){
        return null == value || value.isEmpty();
    }

	/**
	 * 检查对象是否为null
	 * @param value
	 * @return
	 */
	public static boolean isNull(Object value){
		return null == value;
	}
}
