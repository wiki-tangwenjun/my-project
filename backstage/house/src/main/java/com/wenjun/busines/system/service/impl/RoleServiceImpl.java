package com.wenjun.busines.system.service.impl;

import com.wenjun.busines.system.dto.RoleQueryParam;
import com.wenjun.busines.system.mapper.RoleMapper;
import com.wenjun.busines.system.pojo.Role;
import com.wenjun.busines.system.service.IRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @projectName: my-project
 * @package: com.study.system.service.impl
 * @className: RoleServiceImpl
 * @author: wen jun tang
 * @description: 角色管理实现接口
 * @date: 2021年07月06日 18:42
 * @version: 1.0
 */
@Service
public class RoleServiceImpl implements IRoleService {
    @Resource
    private RoleMapper roleMapper;
    @Override
    public void add(Role o) {
        roleMapper.insert(o);
    }

    @Override
    public void delete(Object id) {
        roleMapper.deleteByPrimaryKey((String) id);
    }

    @Override
    public void update(Role o) {
        roleMapper.updateByPrimaryKey(o);
    }

    @Override
    public Role findById(String id) {
        return roleMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Role> findByUserId(String userId) {
        return roleMapper.selectByUserId(userId);
    }

    @Override
    public Integer findCountByAttributes(RoleQueryParam roleQueryParam) {
        return roleMapper.selectCountByAttributes(roleQueryParam);
    }

    @Override
    public List<Role> findByAttributes(RoleQueryParam roleQueryParam) {
        return roleMapper.selectByAttributes(roleQueryParam);
    }
}
