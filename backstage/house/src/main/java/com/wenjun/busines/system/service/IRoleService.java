package com.wenjun.busines.system.service;


import com.wenjun.busines.system.dto.RoleQueryParam;
import com.wenjun.busines.system.pojo.Role;

import java.util.List;

/**
 * @projectName: my-project
 * @package: com.study.service
 * @className: IRoleService
 * @author: wen jun tang
 * @description: 角色相关接口
 * @date: 2021年07月06日 12:27
 * @version: 1.0
 */
public interface IRoleService extends IService<Role> {
    /**
     * 根据用户id查找用户所拥有的角色
     * 1 --> n
     *
     * @param userId 用户id
     * @return com.study.system.pojo.Role
     * @author wen jun tang
     * @date 2021/7/6 17:28
     */
    List<Role> findByUserId(String userId);

    /**
    * 根据条件差总数
    *
    * @author wen jun tang
    * @param roleQueryParam 查询条件
    * @return Integer
    * @date 2021/7/16 18:15
    */
    Integer findCountByAttributes(RoleQueryParam roleQueryParam);

    /**
    * 根据条件查信息
     *
    * @author wen jun tang
    * @param roleQueryParam 查询条件
    * @return java.util.List<com.wenjun.busines.system.pojo.Role>
    * @date 2021/7/16 18:16
    */
    List<Role> findByAttributes(RoleQueryParam roleQueryParam);
}
