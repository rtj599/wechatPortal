package com.rtj.framework.utils;

import java.util.concurrent.TimeUnit;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

public class RedisExecutor {
	//redis 
    public static ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");	
	@SuppressWarnings("unchecked")
	final RedisTemplate<String, Object> redisTemplate  = ac.getBean("redisTemplate",RedisTemplate.class);
	//kv
	ValueOperations<String, Object> jv = redisTemplate.opsForValue();
    //k hash
//	HashOperations<String, Object, Object>  jh = redisTemplate.opsForHash();
//    //k list
//	ListOperations<String, Object> jl = redisTemplate.opsForList();
//    //k set
//	SetOperations<String, Object> js = redisTemplate.opsForSet();
//    //k zset(有序set)
//	ZSetOperations<String, Object> jzs = redisTemplate.opsForZSet();
    /**
     * 存值
     * @param key
     * @param value
     */
    public void set(String key,Object value){
    	jv.set(key, value);
    }
    
    /**
     * 取值
     * @param key
     * @return
     */
    public Object get(String key){
    	return jv.get(key);
    }
    
    /**
     * 删除
     * @param key
     */
    public void delete(String key){
    	 redisTemplate.delete(key);
    }
    
    /**
     * 存值，并设置一个有效时间，单位为秒
     * @param key
     * @param value
     * @param expireSecond
     */
    public void setValueAndExpireSeconds(String key,Object value,Long expireSecond){
    	jv.set(key, value, expireSecond, TimeUnit.SECONDS);
    }
	
}
