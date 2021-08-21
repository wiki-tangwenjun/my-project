package com.wenjun.busines.system.service;

import com.wenjun.busines.system.dto.LoginParam;
import com.wenjun.busines.system.dto.UserQueryParam;
import com.wenjun.busines.system.pojo.AddUserParam;
import com.wenjun.busines.system.pojo.User;
import com.wenjun.busines.system.pojo.UserResources;

import java.util.List;

/**
 * 用户相关接口对象
 *
 * @author tang wen jun
 * @date 2021/6/11 17:39
 */
public interface UserService extends IService<User> {

    /**
     * 增加一个对象
     *
     * @param addUserParam 用户保存对象
     * @return void
     * @author wen jun tang
     * @date 2021/7/16 15:47
     */
    void insert(AddUserParam addUserParam);

    /**
     * 登录接口
     *
     * @param loginParam 登录参数
     * @return java.lang.String
     * @author wen jun tang
     * @date 2021/7/15 12:05
     */
    String findByUserName(LoginParam loginParam) throws Exception;

    /**
     * 根据用户名称查询用户是否存在
     *
     * @param personName 用户名称
     * @return com.wenjun.busines.system.pojo.User
     * @author tang wen jun
     * @date 2021/8/21 22:24
     */
    User findByPersonName(String personName);

    /**
     * 按条件查询用户信息
     *
     * @param userQueryParam 查询条件参数
     * @return java.util.List<com.wenjun.busines.system.pojo.User>
     * @author wen jun tang
     * @date 2021/7/15 12:05
     */
    List<User> findByAttributes(UserQueryParam userQueryParam);

    /**
     * 根据token获取用户名称--》 根据用户名称查询用户角色和资源
     *
     * @param token 用户登录获取的token
     * @return com.wenjun.busines.system.pojo.UserResources
     * @author wen jun tang
     * @date 2021/7/15 12:08
     */
    UserResources findByUserResource(String token);
}
