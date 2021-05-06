package com.study.service;

public interface BaseService<T> {
	/**
	 * 往数据库插入一条记录，如果已存在则抛异常
	 * @param o
	 */
	 void add(T o);
	/**
	 * 根据主键删除一条记录
	 */
	 void delete(T o);
	/**
	 * 根据对象id更新对象信息
	 */
	 void update(T o);
	/**
	 * 通过主键查询对象
	 * @param id
	 * @return
	 */
	 T findById(String id);
}
