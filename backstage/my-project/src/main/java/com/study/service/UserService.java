package com.study.service;

import com.study.pojo.User;

import java.util.List;

/**
 *@文件名称： UserService.java
 *@功能描述： 描述
 *@编写作者： tang wen jun
 *@开发日期： 2020年8月28日
 *@历史版本： 1.0.0.1
 */
public interface UserService extends BaseService<User> {
	
	/**
	 * 
	 * @param userName
	 * @return
	 */
	 User findByUserName(String userName);
	
	
	/**
	 * 
	 * @param idCard
	 * @return
	 */
	 User findByIdCard(String idCard);
	
	/**
	 * 
	 * @param userName
	 * @return
	 */
	 void deleteByUserName(String userName);

	/**
	 * 
	 * @param userName
	 * @param idCard
	 * @param enabled
	 * @return
	 */
	 Long findMaxByAttributes(String userName, String idCard, Long enabled);
	
	/**
	 * 
	 * @param userName
	 * @param idCard
	 * @param enabled
	 * @param pageIndex
	 * @param pageSize
	 * @param orderProp
	 * @param order
	 * @return
	 */
	 List<User> findByAttributes(String userName, String idCard ,Long enabled, Long pageIndex, Long pageSize, String orderProp, String order);
	
}
