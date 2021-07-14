package com.wenjun.busines.system.service.impl;


import com.wenjun.busines.system.dto.UserQueryParam;
import com.wenjun.busines.system.mapper.MenuMapper;
import com.wenjun.busines.system.mapper.RoleMapper;
import com.wenjun.busines.system.mapper.UserMapper;
import com.wenjun.busines.system.pojo.Role;
import com.wenjun.busines.system.pojo.User;
import com.wenjun.busines.system.pojo.UserResources;
import com.wenjun.busines.system.service.IMenuService;
import com.wenjun.busines.system.service.IRoleService;
import com.wenjun.busines.system.service.UserService;
import com.wenjun.handlerException.error.CommonEnum;
import com.wenjun.redis.UserLoginService;
import com.wenjun.util.Base64Util;
import com.wenjun.util.CheckUtil;
import com.wenjun.util.EncryptUtil;
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
    private IMenuService iMenuService;
    @Resource
    private IRoleService iRoleService;

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
        Role role = iRoleService.findByUserId(user.getId());
            role.setRoleMenu(iMenuService.selectByRoleId(role.getId()));

        userResources.setUserRole(role);

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
