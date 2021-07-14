package com.wenjun.shiro;

import com.wenjun.busines.system.mapper.UserMapper;
import com.wenjun.busines.system.pojo.Menu;
import com.wenjun.busines.system.pojo.Role;
import com.wenjun.busines.system.pojo.User;
import com.wenjun.busines.system.service.IMenuService;
import com.wenjun.busines.system.service.IRoleService;
import com.wenjun.util.CheckUtil;
import com.wenjun.util.JWTUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * @projectName: house
 * @package: com.wenjun.shiro
 * @className: CustomRealm
 * @author: wen jun tang
 * @description: 自定义域，实现shiro安全认证
 * @date: 2021年07月14日 15:57
 * @version: 1.0
 */
@Component
public class CustomRealm extends AuthorizingRealm {

    @Resource
    private UserMapper userMapper;
    @Resource
    private IRoleService iRoleService;
    @Resource
    private IMenuService iMenuService;
    /**
     * 必须重写此方法，不然会报错
     */
    @Override
    public boolean supports(AuthenticationToken  token) {
        return token instanceof JWTToken;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("doGetAuthenticationInfo=> 认证");
        String token = (String) authenticationToken.getCredentials();
        // 解密获得username，用于和数据库进行对比
        String username = JWTUtil.getUsername(token);
        if (username == null || !JWTUtil.verify(token, username)) {
            throw new AuthenticationException("token认证失败！");
        }

        /* 以下数据库查询可根据实际情况，可以不必再次查询，这里我两次查询会很耗资源
         * 我这里增加两次查询是因为考虑到数据库管理员可能自行更改数据库中的用户信息
         */
        User user = userMapper.selectByPersonName(username);
        if (CheckUtil.isNull(user.getPassword())) {
            throw new AuthenticationException("该用户不存在！");
        }

        if (user.getLocked() == 1) {
            throw new AuthenticationException("该用户已被封号！");
        }
        return new SimpleAuthenticationInfo(token, token, "MyRealm");
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("doGetAuthorizationInfo=>> 授权");
        String username = JWTUtil.getUsername(principals.toString());
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        // 获得该用户角色
        User user = userMapper.selectByPersonName(username);
        Role role = iRoleService.findByUserId(user.getId());
        // 每个角色拥有默认的权限
        List<Menu> menus = iMenuService.selectByRoleId(role.getId());
        Set<String> roleSet = new HashSet<>();
        Set<String> permissions = new HashSet<>();

        // 需要将 role, permission 封装到 Set 作为 info.setRoles(), info.setStringPermissions() 的参数
        roleSet.add(role.getName());
        permissions.add(Arrays.toString(menus.toArray()));
        //设置该用户拥有的角色和权限
        info.setRoles(roleSet);
        info.setStringPermissions(permissions);
        return info;
    }
}
