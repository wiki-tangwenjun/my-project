package com.study.service;

import com.study.pojo.OperateLog;

import java.util.Date;
import java.util.List;


/**
 *@文件名称： OperateLogService.java
 *@功能描述： 描述
 *@版权信息：www.dondown.com
 *@编写作者： 怕背锅不写
 *@开发日期： 2020年9月8日
 *@历史版本： 1.0.0.1
 */
public interface OperateLogService extends BaseService<OperateLog> {

	/**
	 * 
	 * @param userName
	 * @param userId
	 * @param module
	 * @param style
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	public Long findMaxByAttributes(String userName, String userId, String module, String style, Date beginTime, Date endTime);
	
	/**
	 * 
	 * @param userName
	 * @param userId
	 * @param module
	 * @param style
	 * @param beginTime
	 * @param endTime
	 * @param pageIndex
	 * @param pageSize
	 * @param orderProp
	 * @param order
	 * @return
	 */
	public List<OperateLog> findByAttributes(String userName, String userId, String module, String style, Date beginTime, Date endTime, Long pageIndex, Long pageSize, String orderProp, String order);
	
}
