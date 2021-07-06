package com.study.system.service;

import com.study.system.dto.UserQueryParam;
import com.study.system.pojo.User;

import java.util.List;

/**
 * 用户相关接口对象
 *
 * @author tang wen jun
 * @date 2021/6/11 17:39
 */
public interface UserService extends BaseService<User> {

    /**
     * 根据用户名称查找记录
     *
     * @param userName
     * @return
     */
    User findByUserName(String userName);


    List<User> findByAttributes(UserQueryParam userQueryParam);

}
