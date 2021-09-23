package com.wenjun.util;

import com.auth0.jwt.exceptions.JWTDecodeException;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @projectName: house
 * @package: com.wenjun.util
 * @className: JWTUtil
 * @author: wen jun tang
 * @description: token生成工具类
 * @date: 2021年07月14日 16:04
 * @version: 1.0
 */
@Component
public class JWTUtil {

    /**
     * 密钥
     */
    private static String SECRET;

    @Value("${jwt.secret}")
    private void setSecretKey(String secret) {
        JWTUtil.SECRET = secret;
    }

    /**
     * 过期时间 24 小时
     */
    private static final long EXPIRE_TIME = 86400 * 1000;


    /**
     * 生成 token
     */
    public static String createToken(String username, String userId) {
        try {
            Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
            JwtBuilder jwtBuilder = Jwts.builder();
            // 附带username信息
            return jwtBuilder.setHeaderParam("type", "JWT")
                    // head
                    .setHeaderParam("alg", "HS256")
                    // payload
                    .claim("username", username)
                    .claim("userId", userId)
                    .setSubject("admin-house")
                    //到期时间
                    .setExpiration(date)
                    //创建一个新的JWT，并使用给定的算法进行标记
                    .setId(TextUtil.getUUID().toString())
                    // signature
                    .signWith(SignatureAlgorithm.HS256, SECRET)
                    .compact();
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * 校验 token 是否正确
     */
    public static boolean verify(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token)
                    .getBody();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 获得token中的信息，无需secret解密也能获得
     */
    public static String getUsername(String token) {
        try {
            JwtParser jwtParser = Jwts.parser();
            Jws<Claims> claimsJws = jwtParser.setSigningKey(SECRET).parseClaimsJws(token);
            Claims claims = claimsJws.getBody();

            return (String) claims.get("username");
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 获得token中的信息，无需secret解密也能获得
     */
    public static String getUserId(String token) {
        try {
            JwtParser jwtParser = Jwts.parser();
            Jws<Claims> claimsJws = jwtParser.setSigningKey(SECRET).parseClaimsJws(token);
            Claims claims = claimsJws.getBody();

            return (String) claims.get("userId");
        } catch (JWTDecodeException e) {
            return null;
        }
    }
}
