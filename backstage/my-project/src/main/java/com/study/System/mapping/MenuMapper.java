package com.study.system.mapping;

import com.study.system.pojo.Menu;
import com.study.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wen jun tang
 */
public interface MenuMapper extends BaseMapper<Menu> {
    int deleteByPrimaryKey(String id);

    int insert(Menu record);

    int insertSelective(Menu record);

    Menu selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Menu record);

    int updateByPrimaryKey(Menu record);

    List<Menu> selectByRoleId(@Param("roleId")String roleId);
}
