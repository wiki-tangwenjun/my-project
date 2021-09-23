package com.wenjun.busines.system.service.impl;

import com.wenjun.busines.system.mapper.RoleMenuMapper;
import com.wenjun.busines.system.pojo.RoleMenu;
import com.wenjun.busines.system.service.IRoleMenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @projectName: house
 * @package: com.wenjun.busines.system.service.impl
 * @className: RoleMenuServiceImpl
 * @author: wen jun tang
 * @description: 角色菜单接口实现类
 * @date: 2021年07月15日 17:29
 * @version: 1.0
 */
@Service
public class RoleMenuServiceImpl implements IRoleMenuService {
    @Resource
    private RoleMenuMapper roleMenuMapper;

    @Override
    public void add(RoleMenu entity) {
        roleMenuMapper.insert(entity);
    }

    @Override
    public void delete(Object id) {
        roleMenuMapper.deleteByPrimaryKey((String) id);
    }

    @Override
    public void update(RoleMenu entity) {
        roleMenuMapper.updateByPrimaryKey(entity);
    }

    @Override
    public RoleMenu findById(String id) {
        return roleMenuMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateRoleMenuSelective(String roleId, String menuId) {
        return roleMenuMapper.updateRoleMenuSelective(roleId, menuId);
    }

    @Override
    public int deleteRoleMenu(String roleId, String menuId) {
        return roleMenuMapper.deleteRoleMenu(roleId, menuId);
    }
}
