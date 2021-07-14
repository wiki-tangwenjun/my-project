package com.wenjun.busines.system.service.impl;

import com.wenjun.busines.system.mapper.MenuMapper;
import com.wenjun.busines.system.pojo.Menu;
import com.wenjun.busines.system.service.IMenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @projectName: my-project
 * @package: com.study.system.service.impl
 * @className: MenuServiceImpl
 * @author: wen jun tang
 * @description: 菜单管理实现接口
 * @date: 2021年07月06日 18:45
 * @version: 1.0
 */
@Service
public class MenuServiceImpl implements IMenuService {
    @Resource
    private MenuMapper menuMapper;

    @Override
    public void add(Menu o) {
        menuMapper.insert(o);
    }

    @Override
    public void delete(Menu o) {
        menuMapper.deleteByPrimaryKey(o.getId());
    }

    @Override
    public void update(Menu o) {
        menuMapper.updateByPrimaryKeySelective(o);
    }

    @Override
    public Menu findById(String id) {
        return menuMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Menu> selectByRoleId(String roleId) {
        return menuMapper.selectByRoleId(roleId);
    }
}
