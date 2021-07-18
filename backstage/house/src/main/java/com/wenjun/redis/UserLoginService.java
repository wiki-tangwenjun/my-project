package com.wenjun.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 登录缓存服务
 *
 * @author tang wen jun
 * @date 2021/6/11 17:46
 */
@Slf4j
@Component
public class UserLoginService {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void setKey(String key, String randomCode, int minutes) {
        redisTemplate.opsForValue().set(key, randomCode, minutes, TimeUnit.MINUTES);
    }

    public String getKey(String key) {
        return (String) redisTemplate.opsForValue().get(key);
    }

    /**
    * @description: 删除key
    * @author wen jun tang
    * @param key key
    * @date 2021/7/15 10:46
    */
    public void delete(String key) {
        log.info("删除验证码信息：" + key);
        redisTemplate.delete(key);
    }


}
