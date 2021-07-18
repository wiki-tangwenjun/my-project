package com.wenjun.busines.system.mapper;

import com.wenjun.busines.system.pojo.RoleMenu;
import org.apache.ibatis.annotations.Param;

public interface RoleMenuMapper {
    int deleteByPrimaryKey(String id);

    int insert(RoleMenu record);

    int insertSelective(RoleMenu record);

    RoleMenu selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(RoleMenu record);

    int updateByPrimaryKey(RoleMenu record);

    int updateRoleMenuSelective(@Param("roleId")String roleId, @Param("menuId")String menuId);

    int deleteRoleMenu(@Param("roleId")String roleId, @Param("menuId")String menuId);
}