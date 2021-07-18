package com.wenjun.busines.system.service;

/**
 * @projectName: house
 * @package: com.wenjun.busines.system.service
 * @className: IService
 * @author: wen jun tang
 * @description: 基础服务接口
 * @date: 2021年07月14日 14:36
 * @version: 1.0
 */
public interface IService<T> {
    /**
     * 往数据库插入一条记录，如果已存在则抛异常
     * @description:
     * @author wen jun tang
     * @param entity 实体对象
     * @date 2021/7/7 14:42
     */
    void add(T entity);

    /**
     * 根据主键删除一条记录
     * @author wen jun tang
     * @param id 主键
     * @date 2021/7/7 14:43
     */
    void delete(Object id);

    /**
     * 根据对象id更新对象信息
     * @author wen jun tang
     * @param entity 实体对象
     * @date 2021/7/7 14:44
     */
    void update(T entity);

    /**
     * 通过主键查询对象
     * @param id 主键
     * @return T
     */
    T findById(String id);
}
