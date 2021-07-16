package com.wenjun.busines.system.mapper;

import com.wenjun.busines.system.pojo.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMapper {
    int deleteByPrimaryKey(String id);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    /**
     * 根据用户id查找用户所拥有的角色
     * 1 --> n
     *
     * @param userId
     * @return com.study.system.pojo.Role
     * @author wen jun tang
     * @date 2021/7/6 17:28
     */
    List<Role> selectByUserId(@Param("userId") String userId);
}
