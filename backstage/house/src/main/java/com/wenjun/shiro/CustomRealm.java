package com.wenjun.shiro;

import com.wenjun.busines.system.mapper.UserMapper;
import com.wenjun.busines.system.pojo.Menu;
import com.wenjun.busines.system.pojo.Role;
import com.wenjun.busines.system.pojo.User;
import com.wenjun.busines.system.service.IMenuService;
import com.wenjun.busines.system.service.IRoleService;
import com.wenjun.util.CheckUtil;
import com.wenjun.util.JWTUtil;
import lombok.SneakyThrows;
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

    @SneakyThrows
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("doGetAuthenticationInfo=> 认证");
        String token = (String) authenticationToken.getCredentials();
        // 解密获得username，用于和数据库进行对比
        String username = JWTUtil.getUsername(token);
        String userId = JWTUtil.getUserId(token);
        if (CheckUtil.isNull(username) || CheckUtil.isNull(userId) || !JWTUtil.verify(token)) {
            throw new AuthenticationException("token认证失败！");
        }

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
            List<Role> roles = iRoleService.findByUserId(user.getId());
            Set<String> roleSet = new HashSet<>();
            Set<String> permissions = new HashSet<>();
            for (Role role: roles) {
                // 查找该角色权限
                List<Menu> menus = iMenuService.findByRoleId(role.getId());
                role.setRoleMenu(menus);
                // 需要将 [role], [permission] 封装到 Set 作为 info.setRoles(), info.setStringPermissions() 的参数
                roleSet.add(role.getRoleKey());
                for (Menu menu: menus) {
                    permissions.add(menu.getPermissionName());
                }
            }

        // 设置用户[角色]和[权限]
        info.setRoles(roleSet);
        info.setStringPermissions(permissions);
        return info;
    }
}
