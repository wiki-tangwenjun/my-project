package com.study.system.service.impl;


import com.study.system.dto.UserQueryParam;
import com.study.system.mapping.UserMapper;
import com.study.system.pojo.User;
import com.study.system.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


/**
* @description: 用户相关操作接口
* @author wen jun tang
* @date 2021/6/15 17:24
*/
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    @Override
    public void add(User o) {
        userMapper.insertSelective(o);
    }

    @Override
    public void update(User o) {
        userMapper.updateByPrimaryKeySelective(o);
    }

    @Override
    public User findById(String id){
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public void delete(User o) {
        userMapper.deleteByPrimaryKey(o.getId());
    }

    @Override
    public User findByUserName(String userName) {
        return null;
    }

    @Override
    public List<User> findByAttributes(UserQueryParam userQueryParam) {
        return userMapper.selectByAttributes(userQueryParam);
    }

}
