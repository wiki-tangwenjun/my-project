package com.wenjun.busines.system.service;


import com.wenjun.busines.system.dto.MenuQueryParam;
import com.wenjun.busines.system.pojo.Menu;

import java.util.List;

/**
 * @projectName: my-project
 * @package: com.study.system.service
 * @className: IMenuService
 * @author: wen jun tang
 * @description: 菜单管理接口
 * @date: 2021年07月06日 18:41
 * @version: 1.0
 */
public interface IMenuService extends IService<Menu> {
    /**
     * 根据角色编号查找角色菜单信息
     *
     * @param roleId 角色id
     * @return java.util.List<com.study.system.pojo.Menu>
     * @author wen jun tang
     * @date 2021/7/8 11:06
     */
    List<Menu> findByRoleId(String roleId);

    /**
     * 根据条件查询总数
     * @author tang wen jun
     * @param menuQueryParam  查询条件
     * @return java.util.List<com.wenjun.busines.system.pojo.Menu>
     * @date 2021/7/18 21:38
     */
    Integer findCountByAttributes(MenuQueryParam menuQueryParam);

    /**
     * 根据条件查询菜单信息
     * @author tang wen jun
     * @param menuQueryParam  查询条件
     * @return java.util.List<com.wenjun.busines.system.pojo.Menu>
     * @date 2021/7/18 21:38
     */
    List<Menu> findByAttributes(MenuQueryParam menuQueryParam);
}
