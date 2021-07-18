package com.wenjun.busines.system.service.impl;


import com.wenjun.busines.system.controller.UserController;
import com.wenjun.busines.system.dto.LoginParam;
import com.wenjun.busines.system.dto.UserQueryParam;
import com.wenjun.busines.system.mapper.MenuMapper;
import com.wenjun.busines.system.mapper.RoleMapper;
import com.wenjun.busines.system.mapper.UserMapper;
import com.wenjun.busines.system.mapper.UserRoleMapper;
import com.wenjun.busines.system.pojo.*;
import com.wenjun.busines.system.service.IMenuService;
import com.wenjun.busines.system.service.IRoleService;
import com.wenjun.busines.system.service.UserService;
import com.wenjun.handlerException.error.CommonEnum;
import com.wenjun.redis.UserLoginService;
import com.wenjun.util.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.List;


/**
 * @author wen jun tang
 * @description: 用户相关操作接口
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
    @Resource
    private UserRoleMapper userRoleMapper;

    @Override
    public void add(User o) {
        userMapper.insertSelective(o);
    }

    @Override
    public void update(User o) {
        userMapper.updateByPrimaryKeySelective(o);
    }

    @Override
    public User findById(String id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public void delete(Object id) {
        userMapper.deleteByPrimaryKey((String) id);
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void insert(AddUserParam addUserParam) {
        addUserParam.getUser().setId(TextUtil.getUUID());

        UserRole userRole = new UserRole();
            userRole.setId(TextUtil.getUUID());
            userRole.setUserId(addUserParam.getUser().getId());
            userRole.setRoleId(addUserParam.getRoleId());

        userRoleMapper.insert(userRole);

        userMapper.insert(addUserParam.getUser());
    }

    @Override
    public String findByUserName(LoginParam loginParam) throws Exception {
        // 先验证验证码是否正确
        String code = userLoginService.getKey(UserController.RANDOMKEY);
        if (CheckUtil.isNull(code)) {
            throw new Exception(CommonEnum.ERROR_CHECK_CODE.getError());
        }

        String tokenName = "";
        if (loginParam.getCode().equals(code)) {
            // 从数据库中验证用户名密码
            String userName = new String(Base64Util.decode(loginParam.getUserName()));
            User user = userMapper.selectByPersonName(userName);
            if (CheckUtil.isNull(user)) {
                throw new NullPointerException(CommonEnum.ERROR_USER_NOT_FOUND.getDescription());
            }

            tokenName = user.getUserName();
            String inPassword = new String(Base64Util.decode(loginParam.getPassword()), StandardCharsets.UTF_8);
            String encPwd = EncryptUtil.md5(inPassword, null, 2);
            if (!encPwd.trim().equals(user.getPassword().trim())) {
                throw new Exception(CommonEnum.ERROR_USER_PASSWORD.getError());
            }
        } else {
            throw new Exception(CommonEnum.ERROR_CHECK_CODE.getError());
        }

        return JWTUtil.createToken(tokenName);
    }

    @Override
    public List<User> findByAttributes(UserQueryParam userQueryParam) {
        return userMapper.selectByAttributes(userQueryParam);
    }

    @Override
    public UserResources findByUserResource(String token) {
        UserResources userResources = new UserResources();
        User user = userMapper.selectByPersonName(JWTUtil.getUsername(token));
        // 登录成功查找用户角色资源
        userResources.setUser(user);
            List<Role> roles = roleMapper.selectByUserId(user.getId());
            userResources.setUserRole(roles);
            for (Role role: roles) {
                role.setRoleMenu(menuMapper.selectByRoleId(role.getId()));
            }

        return userResources;
    }
}
