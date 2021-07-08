package com.study.system.mapping;

import com.study.system.pojo.Menu;
import com.study.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wen jun tang
 */
public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * 根据角色编号查找角色菜单信息
     *
     * @param roleId 角色id
     * @return java.util.List<com.study.system.pojo.Menu>
     * @author wen jun tang
     * @date 2021/7/8 11:06
     */
    List<Menu> selectByRoleId(@Param("roleId") String roleId);
}
