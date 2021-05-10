package com.study.util;

import java.util.Collection;

/**
 * @文件名称: CheckUtil.java
 * @功能描述: TODO(格式检查工具)
 * @编写作者： tangwenjun
 * @开发日期： 2020年3月26日
 * @历史版本： V1.0
 */
public class CheckUtil {
	/**
	 * 检查字符串是否为空或null
	 * @param value
	 * @return
	 */
	public static boolean isNull(String value){
		if (null == value || value.trim().equals("")){
			return true;
		}
		return false;
	}
	
	/**
	 * 检查容器是否为空或null
	 * @param value
	 * @return
	 */
	public static boolean isNull(Collection<?> value){
		if (null == value || value.isEmpty()){
			return true;
		}
		return false;
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
