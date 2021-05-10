package com.study.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @文件名：UserLoginService.java
 * @功  能：登陆缓存服务
 * @作  者：tangwenjun
 * @日  期：2021/5/10
 */
@Slf4j
@Component
@CacheConfig(cacheNames="login")
public class UserLoginService {
	@Autowired
    private RedisTemplate<String, Object> redisTemplate;

	public String  getCacheName(){
		String key = "";
		CacheConfig config = UserLoginService.class.getAnnotation(CacheConfig.class);
		String[]  names = config.cacheNames();
		if(names.length>0){
			key = names[0];
		}
		return key;
	}
	
	private String getKey(String sessionId){
		return getCacheName() + "::" + sessionId;
	}	
	
	public void update(String sessionId, String userName){
		redisTemplate.opsForValue().set(getKey(sessionId), userName);
	}
	
	public void update(String sessionId, String userName, int minutes){
		redisTemplate.opsForValue().set(getKey(sessionId), userName, minutes, TimeUnit.MINUTES);
	}
	
	public void expired(String sessionId, int minutes){
		redisTemplate.expire(getKey(sessionId), minutes, TimeUnit.MINUTES);
	}
	
	public String find(String sessionId){
		return (String)redisTemplate.opsForValue().get(getKey(sessionId));
	}
	
	public void delete(String sessionId){
		log.info("通过sessionId删除用户信息：" + sessionId);
		redisTemplate.expire(getKey(sessionId), 1, TimeUnit.SECONDS);
	}
}
