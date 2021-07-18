package com.wenjun.busines.system.service;

import com.wenjun.busines.system.pojo.UserRole;

/**
 * @projectName: house
 * @package: com.wenjun.busines.system.service
 * @className: IUserRoleService
 * @author: wen jun tang
 * @description: 用户角色中间表接口
 * @date: 2021年07月15日 17:25
 * @version: 1.0
 */
public interface IUserRoleService extends IService<UserRole>{

    /**
    * 根据角色id和用户id删除用户角色
    * @author wen jun tang
    * @param userId 用户id
    * @param roleId 角色id
    * @date 2021/7/16 16:29
    */
    void deleteByAttributes(String userId, String roleId);
}
