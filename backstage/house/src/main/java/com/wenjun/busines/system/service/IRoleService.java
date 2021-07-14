package com.wenjun.busines.system.service;


import com.wenjun.busines.system.pojo.Role;

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
     * @param userId
     * @return com.study.system.pojo.Role
     * @author wen jun tang
     * @date 2021/7/6 17:28
     */
    Role findByUserId(String userId);
}
