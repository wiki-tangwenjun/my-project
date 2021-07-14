package com.wenjun.busines.system.service;

import com.wenjun.busines.system.dto.UserQueryParam;
import com.wenjun.busines.system.pojo.User;
import com.wenjun.busines.system.pojo.UserResources;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 用户相关接口对象
 *
 * @author tang wen jun
 * @date 2021/6/11 17:39
 */
public interface UserService extends IService<User> {

    /**
     * 根据用户名称查找记录
     * @return UserResources
     */
    UserResources findByUserName(HttpServletRequest request, String userName, String password) throws Exception;


    List<User> findByAttributes(UserQueryParam userQueryParam);

}
