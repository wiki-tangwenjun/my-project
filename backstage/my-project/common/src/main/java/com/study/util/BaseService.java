package com.study.util;

import java.util.List;

/**
 * @projectName: my-project
 * @package: com.study.util
 * @className: BaseService
 * @author: wen jun tang
 * @description: 可公用Service接口
 * @date: 2021年07月07日 14:39
 * @version: 1.0
 */
public interface BaseService<T> {
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
    void delete(T id);

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

    /**
     * 根据 Wrapper 条件，查询总记录数
     *
     * @param queryWrapper 实体对象封装操作类（可以为 null）
     * @return java.lang.Integer
     * @author wen jun tang
     * @date 2021/7/7 14:36
     */
    Integer findClout(T queryWrapper);

    /**
     * 根据 entity 条件，查询全部记录
     *
     * @param queryWrapper 实体对象封装操作类（可以为 null）
     * @return java.util.List<T>
     * @author wen jun tang
     * @date 2021/7/7 14:35
     */
    List<T> findList(T queryWrapper);
}
