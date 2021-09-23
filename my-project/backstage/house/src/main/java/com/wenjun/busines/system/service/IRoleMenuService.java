package com.wenjun.busines.system.service;

import com.wenjun.busines.system.pojo.RoleMenu;
import org.apache.ibatis.annotations.Param;

/**
 * @projectName: house
 * @package: com.wenjun.busines.system.service
 * @className: IRoleMenuService
 * @author: wen jun tang
 * @description: 角色菜单中间表接口
 * @date: 2021年07月15日 17:26
 * @version: 1.0
 */
public interface IRoleMenuService extends IService<RoleMenu> {
    int updateRoleMenuSelective(@Param("roleId")String roleId, @Param("menuId")String menuId);

    int deleteRoleMenu(String roleId, String menuId);
}
