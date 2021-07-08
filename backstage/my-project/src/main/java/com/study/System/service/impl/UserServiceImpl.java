package com.study.system.service.impl;


import com.study.error.CommonEnum;
import com.study.redis.UserLoginService;
import com.study.system.dto.UserQueryParam;
import com.study.system.mapping.MenuMapper;
import com.study.system.mapping.RoleMapper;
import com.study.system.mapping.UserMapper;
import com.study.system.pojo.Role;
import com.study.system.pojo.User;
import com.study.system.pojo.UserResources;
import com.study.system.service.UserService;
import com.study.util.Base64Util;
import com.study.util.CheckUtil;
import com.study.util.EncryptUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
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
    @Resource
    private UserLoginService userLoginService;
    @Resource
    private MenuMapper menuMapper;
    @Resource
    private RoleMapper roleMapper;

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
    public UserResources findByUserName(HttpServletRequest request, String userName, String password) throws Exception {
        UserResources userResources = new UserResources();

        // 从数据库中验证用户名密码
        userName = new String(Base64Util.decode(userName));
        User user = userMapper.selectByPersonName(userName);
        if (CheckUtil.isNull(user)) {
             throw new NullPointerException(CommonEnum.ERROR_USER_NOT_FOUND.getDescription());
        }

        String inPassword = new String(Base64Util.decode(password), StandardCharsets.UTF_8);
        String encPwd = EncryptUtil.md5(inPassword, null, 2);
        if (!encPwd.trim().equals(user.getPassword().trim())) {
            throw new Exception(CommonEnum.ERROR_USER_PASSWORD.getDescription());
        }

        // 登录成功查找用户角色资源
        userResources.setUser(user);
        List<Role> roles = roleMapper.selectByUserId(user.getId());
        for(Role role: roles) {
            role.setRoleMenu(menuMapper.selectByRoleId(role.getId()));
        }
        userResources.setUserRole(roles);

        // 登陆成功保存回话信息
        userLoginService.update(request.getSession().getId(), userName);
        userLoginService.expired(request.getSession().getId(), 200);

        return userResources;
    }

    @Override
    public List<User> findByAttributes(UserQueryParam userQueryParam) {
        return userMapper.selectByAttributes(userQueryParam);
    }
}
