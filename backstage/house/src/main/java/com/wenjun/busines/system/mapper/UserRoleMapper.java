package com.wenjun.busines.system.mapper;

import com.wenjun.busines.system.pojo.UserRole;
import org.apache.ibatis.annotations.Param;

public interface UserRoleMapper {
    int deleteByPrimaryKey(String id);

    int insert(UserRole record);

    int insertSelective(UserRole record);

    UserRole selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(UserRole record);

    int updateByPrimaryKey(UserRole record);

    int deleteByAttributes(@Param("userId")String userId, @Param("roleId")String roleId);
}
