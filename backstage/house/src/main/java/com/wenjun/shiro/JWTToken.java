package com.wenjun.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @projectName: house
 * @package: com.wenjun.shiro
 * @className: JWTToken
 * @author: wen jun tang
 * @description: 对token进行扩展
 * @date: 2021年07月14日 15:59
 * @version: 1.0
 */
public class JWTToken implements AuthenticationToken {

    private String token;

    public JWTToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
