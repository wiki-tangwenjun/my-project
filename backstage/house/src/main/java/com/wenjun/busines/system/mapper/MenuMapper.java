package com.wenjun.busines.system.mapper;

import com.wenjun.busines.system.dto.MenuQueryParam;
import com.wenjun.busines.system.pojo.Menu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MenuMapper {
    int deleteByPrimaryKey(String id);

    int insert(Menu record);

    int insertSelective(Menu record);

    Menu selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Menu record);

    int updateByPrimaryKey(Menu record);

    /**
     * 根据角色编号查找角色菜单信息
     *
     * @param roleId 角色id
     * @return java.util.List<com.study.system.pojo.Menu>
     * @author wen jun tang
     * @date 2021/7/8 11:06
     */
    List<Menu> selectByRoleId(@Param("roleId") String roleId);

    /**
     * 根据条件查询总数
     * @author tang wen jun
     * @param menuQueryParam  查询条件
     * @return java.util.List<com.wenjun.busines.system.pojo.Menu>
     * @date 2021/7/18 21:38
     */
    Integer selectCountByAttributes(MenuQueryParam menuQueryParam);

    /**
     * 根据条件查询菜单信息
     * @author tang wen jun
     * @param menuQueryParam  查询条件
     * @return java.util.List<com.wenjun.busines.system.pojo.Menu>
     * @date 2021/7/18 21:38
     */
    List<Menu> selectByAttributes(MenuQueryParam menuQueryParam);
}
