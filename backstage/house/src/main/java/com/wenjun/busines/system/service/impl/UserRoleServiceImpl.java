package com.wenjun.busines.system.service.impl;

import com.wenjun.busines.system.mapper.UserRoleMapper;
import com.wenjun.busines.system.pojo.UserRole;
import com.wenjun.busines.system.service.IUserRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @projectName: house
 * @package: com.wenjun.busines.system.service.impl
 * @className: UserRoleServiceImpl
 * @author: wen jun tang
 * @description: 用户角色接口实现类
 * @date: 2021年07月15日 17:27
 * @version: 1.0
 */
@Service
public class UserRoleServiceImpl implements IUserRoleService {
    @Resource
    private UserRoleMapper userRoleMapper;

    @Override
    public void add(UserRole entity) {
        userRoleMapper.insert(entity);
    }

    @Override
    public void delete(Object id) {
        userRoleMapper.deleteByPrimaryKey((String) id);
    }

    @Override
    public void update(UserRole entity) {
        userRoleMapper.updateByPrimaryKey(entity);
    }

    @Override
    public UserRole findById(String id) {
        return userRoleMapper.selectByPrimaryKey(id);
    }
}
