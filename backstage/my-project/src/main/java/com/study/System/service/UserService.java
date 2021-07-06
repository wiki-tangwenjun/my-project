package com.study.system.service;

import com.study.system.dto.UserQueryParam;
import com.study.system.pojo.User;
import com.study.system.pojo.UserResources;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
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
     * @return UserResources
     */
    UserResources findByUserName(HttpServletRequest request, String userName, String password) throws Exception;


    List<User> findByAttributes(UserQueryParam userQueryParam);

}
