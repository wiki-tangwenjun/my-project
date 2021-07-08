package com.study.mapper;

import java.io.Serializable;
import java.util.List;

/**
 * @projectName: my-project
 * @package: com.study.util
 * @className: BaseMapper
 * @author: wen jun tang
 * @description: 可共用Mapper接口
 * @date: 2021年07月07日 14:28
 * @version: 1.0
 */
public interface BaseMapper<T> {
    /**
     * 插入一条记录
     *
     * @param entity 实体对象
     * @return int
     * @author wen jun tang
     * @date 2021/7/7 14:38
     */
    int insert(T entity);

    /**
     * 根据 ID 删除
     *
     * @param id
     * @return int
     * @author wen jun tang
     * @date 2021/7/7 14:38
     */
    int deleteByPrimaryKey(Serializable id);

    /**
     * 根据条件删除内容
     *
     * @param entity 删除条件
     * @return int
     * @author wen jun tang
     * @date 2021/7/7 14:37
     */
    int delete(T entity);

    /**
     * 根据 ID 修改
     *
     * @param entity 修改对象
     * @return int
     * @author wen jun tang
     * @date 2021/7/7 14:37
     */
    int updateByPrimaryKeySelective(T entity);

    /**
     * 根据 ID 查询
     *
     * @param id
     * @return T
     * @author wen jun tang
     * @date 2021/7/7 14:36
     */
    T selectByPrimaryKey(Serializable id);

    /**
     * 根据 Wrapper 条件，查询总记录数
     *
     * @param queryWrapper 实体对象封装操作类（可以为 null）
     * @return java.lang.Integer
     * @author wen jun tang
     * @date 2021/7/7 14:36
     */
    Integer selectCount(T queryWrapper);

    /**
     * 根据 entity 条件，查询全部记录
     *
     * @param queryWrapper 实体对象封装操作类（可以为 null）
     * @return java.util.List<T>
     * @author wen jun tang
     * @date 2021/7/7 14:35
     */
    List<T> selectList(T queryWrapper);

}
