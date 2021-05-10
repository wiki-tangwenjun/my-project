package com.study.util;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * @文件名: ObjectUtil.java
 * @功    能: 对象处理工具
 * @作    者： lixx2048@163.com
 * @日    期： 2020年5月8日
 * @版    本： V1.0
 */
@Slf4j
public class ObjectUtil {

	/**
	 * @功    能: 将新对象中非空字段设置为旧对象字段,处理后,旧对象为最新字段
	 * @作    者： lixx2048@163.com
	 * @日    期： 2020年5月8日
	 * @说    明：
	 * @历    史：lixx2048@163.com 1.0
	 */
	public static void updateObjectData(Object original, Object newObject) {
		try {
			Class<?> oClass = original.getClass();
			Method[] methods = oClass.getMethods();
			for (Method method : methods) {
				method.setAccessible(true);
				String getMethodName = method.getName();
				if (getMethodName.equals("getClass")) {
					continue;
				}
				if (getMethodName.startsWith("get")) {
					Object value = method.invoke(newObject);
					if(value != null){
						//Class<?>[] interfaces = value.getClass().getInterfaces();
						if(!isListOrSetOrMap(value)){
							String setMethodName = "set" + getMethodName.substring(3);
							Method setMethod = oClass.getMethod(setMethodName, value.getClass());
							setMethod.invoke(original, value);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @功    能: 通过解析一行文本设置对象属性
	 * @作    者： lixx2048@163.com
	 * @日    期： 2020年5月14日
	 * @说    明：
	 * @历    史：lixx2048@163.com 1.0
	 */
	public static Object updateObjectByParseLine(Object object, String line, String regex, boolean genStatic) {
		
		int index = 0;
		String[] attr = line.split(regex);
		
		Class<?> temp = object.getClass();
		while (temp != null && index < attr.length) {
			// 获取所有声明的属性
			Field[] fds = temp.getDeclaredFields();
			if (fds == null || fds.length <= 0) {
				temp = temp.getSuperclass();
				continue;
			}
			for (Field field : fds) {
				if(index >= attr.length)
					break;
				// 不生成静态属性
				if (Modifier.isStatic(field.getModifiers()) && !genStatic) {
					continue;
				}
				// 设置访问属性
				field.setAccessible(true);
				// 获取设置方法
				Class<?> type = field.getType();
				String methodName = "set" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
				Method method = null;
				try {
					// 获取方法
					method = temp.getMethod(methodName, type);
					if (null == method) {
						continue;
					}
					// 设置对应属性
					method.invoke(object, textToObject(attr[index], type));
				} catch (Exception e) {
					log.error("设置对象属性异常：{}({}){}", methodName, attr[index], e.getMessage());
				}
				++index;
			}
			temp = temp.getSuperclass();
		}
		return object;
	}
	
	/**
	 * @功能描述: 文本转对象
	 * @编写作者： lixx2048@163.com
	 * @开发日期： 2020年4月30日
	 * @历史版本： V1.0  
	 * @参数说明：
	 * @返  回  值：
	 */
	private static Object textToObject(String text, Class<?> type) {
		if (type == Integer.class || type == int.class) {
			return Integer.valueOf(text);
		}
		if (type == String.class) {
			return text;
		}
		if (type == Float.class || type == float.class) {
			return Float.valueOf(text);
		}
		if (type == Long.class || type == long.class) {
			return Long.valueOf(text);
		}
		if (type == Byte.class || type == byte.class) {
			return Byte.valueOf(text);
		}
		if (type == Boolean.class || type == boolean.class) {
			return Boolean.valueOf(text);
		}
		if (type == Timestamp.class) {
			return Timestamp.valueOf(text);
		}
		if (type == Date.class ) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			try {
				return format.parse(text);
			} catch (ParseException e) {
				return null;
			}
		}
		if (type.getEnumConstants() != null) {
			Object[] t = type.getEnumConstants();
			for (Object object : t) {
				if (object.toString().equalsIgnoreCase(text)) {
					return object;
				}
			}
		}
		return text;
	}
	
	/**
	 * @功能描述: 对象转json字符串
	 * @编写作者： lixx2048@163.com
	 * @开发日期： 2020年4月16日
	 * @历史版本： V1.0  
	 * @参数说明：
	 */
	public static String toJson(Object original) {
		String result = "";
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS"));
			result = mapper.writeValueAsString(original);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return result;
	}
	public static String toJson(Object original, SimpleDateFormat dateFormat) {
		String result = "";
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.setDateFormat(dateFormat);
			result = mapper.writeValueAsString(original);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return result;
	}
	
	/**
	 * @功    能: map转字符串
	 * @作    者： lixx2048@163.com
	 * @日    期： 2020年4月21日
	 * @说    明：
	 * @历    史：lixx2048@163.com 1.0
	 */
	public static String toJson(Map<String, Object> map) {
		String result = "";
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS"));
			result = mapper.writeValueAsString(map);
//			ObjectNode node = mapper.createObjectNode();
//			node.put("status", status);
//			node.put("value", mapper.readTree(jsonSource));
//			result = mapper.writeValueAsString(node);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return result;
	}
	
	public static String toJson(Map<String, Object> map, SimpleDateFormat dateFormat) {
		String result = "";
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.setDateFormat(dateFormat);
			result = mapper.writeValueAsString(map);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return result;
	}
	
	/**
	 * @功    能: 将json转化为对应实体类
	 * @作    者： lixx2048@163.com
	 * @日    期： 2020年4月20日
	 * @说    明：
	 * @历    史：lixx2048@163.com 1.0
	 */
	public static <T> T fromJson(String json , Class<T> c){
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS"));
			return mapper.readValue(json, c);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static <T> T fromJson(String json , Class<T> c, SimpleDateFormat dateFormat){
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.setDateFormat(dateFormat);
			return mapper.readValue(json, c);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 如果对象是集合类，则返回true
	 * @param o
	 */
	public static boolean isListOrSetOrMap(Object value) {
		return value instanceof List || value instanceof Set || value instanceof Map;
	}
	
	/**
	 * @功    能: 获取对象属性以及属性值
	 * @作    者： lixx2048@163.com
	 * @日    期： 2020年5月8日
	 * @说    明：
	 * @历    史：lixx2048@163.com 1.0
	 */
	@SuppressWarnings({ "rawtypes" })	
	public static Map<String, Object> getFieldAndValue(Object object) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		Class temp = object.getClass();
		while (temp != null) {
			Field[] fds = temp.getDeclaredFields();
			if (fds == null || fds.length <= 0) {
				temp = temp.getSuperclass();
				continue;
			}
			for (Field field : fds) {
				// 获取属性的Get方法
				String getMethodName = null;
				if (field.getType() == Boolean.class || field.getType() == boolean.class) {
					getMethodName = "is" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
				} else {
					getMethodName = "get" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
				}
				
				// 通过get方法获取属性值
				Method getMethod = null;
				try {
					// 获取对应方法
					getMethod = object.getClass().getMethod(getMethodName);
					if(null == getMethod) 
						continue;
					// 获取对应值
					Object value = getMethod.invoke(object);
					map.put(field.getName(), value);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			temp = temp.getSuperclass();
		}
		
		return map;
	}
}
