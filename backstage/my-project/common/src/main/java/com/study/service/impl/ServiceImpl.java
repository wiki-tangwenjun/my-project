package com.study.service.impl;


import com.study.mapper.BaseMapper;
import com.study.service.BaseService;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * IService 实现类（ 泛型：M 是 mapper 对象，T 是实体 ）
 *
 * @projectName: my-project
 * @package: com.study.service.impl
 * @className: ServiceImpl
 * @author: wen jun tang
 * @description: 共用服务接口实现层
 * @date: 2021年07月07日 15:01
 * @version: 1.0
 */
public class ServiceImpl<M extends BaseMapper<T>, T> implements BaseService<T> {

    @Resource
    protected M baseMapper;

    @Override
    public void add(T entity) {
        baseMapper.insert(entity);
    }

    @Override
    public void delete(T id) {
        baseMapper.deleteByPrimaryKey((Serializable) id);
    }

    @Override
    public void update(T entity) {
        baseMapper.updateByPrimaryKeySelective(entity);
    }

    @Override
    public T findById(String id) {
        return baseMapper.selectByPrimaryKey(id);
    }

    @Override
    public Integer findClout(T queryWrapper) {
        return baseMapper.selectCount(queryWrapper);
    }

    @Override
    public List<T> findList(T queryWrapper) {
        return baseMapper.selectList(queryWrapper);
    }
}
