package com.flx.netty.chat.admin.common.redis.impl;

import com.flx.netty.chat.admin.common.redis.RedisStringService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @Author: Fenglixiong
 * @Date: 2020/5/27 16:45
 * @Description:
 */
@Slf4j
@Component
public class RedisStringServiceImpl implements RedisStringService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 获取值
     * @param key
     * @return
     */
    public Object get(String key){
        if(StringUtils.isBlank(key)){
            throw new RuntimeException("[get] key is null !");
        }
        try {
            return redisTemplate.opsForValue().get(key);
        }catch (Exception e){
            throw new RuntimeException("[get] method occur error : "+e.getMessage()+" !");
        }
    }

    /**
     * 设置值
     * @param key
     * @param value
     * @return
     */
    public boolean set(String key,Object value){
        if(StringUtils.isBlank(key)){
            throw new RuntimeException("[set] key is null !");
        }
        try {
            redisTemplate.opsForValue().set(key,value);
            return true;
        }catch (Exception e){
            throw new RuntimeException("[set] method occur error : "+e.getMessage()+" !");
        }
    }

    /**
     * 设置带失效时间的值
     * @param key
     * @param value
     * @param expire
     * @return
     */
    public boolean set(String key,Object value,long expire,TimeUnit unit){
        if(StringUtils.isBlank(key)){
            throw new RuntimeException("[setWithExpire] key is null !");
        }
        if(expire<=0){
            throw new RuntimeException("[setWithExpire] time is illegal !");
        }
        try{
            redisTemplate.opsForValue().set(key,value,expire, unit);
            return true;
        }catch (Exception e){
            throw new RuntimeException("[setWithExpire] method occur error : "+e.getMessage()+" !");
        }
    }

    /**
     * 设置带失效时间的值
     * @param key
     * @param value
     * @param expire
     * @return
     */
    public boolean setIfAbsent(String key,Object value,long expire,TimeUnit unit){
        if(StringUtils.isBlank(key)){
            throw new RuntimeException("[setWithExpire] key is null !");
        }
        if(expire<=0){
            throw new RuntimeException("[setWithExpire] time is illegal !");
        }
        try{
            Boolean result = redisTemplate.opsForValue().setIfAbsent(key, value, expire, unit);
            if(result==null){
                return false;
            }
            return result;
        }catch (Exception e){
            throw new RuntimeException("[setWithExpire] method occur error : "+e.getMessage()+" !");
        }
    }

    /**
     * 递增
     * @param key
     * @param count
     * @return
     */
    public long incr(String key,long count){
        if(StringUtils.isBlank(key)){
            throw new RuntimeException("[incr] key is null !");
        }
        if(count<=0){
            throw new RuntimeException("[incr] count <= 0 !");
        }
        try{
            Long result = redisTemplate.opsForValue().increment(key,count);
            return result == null ? 0 : result;
        }catch (Exception e){
            throw new RuntimeException("[incr] method occur error : "+e.getMessage()+" !");
        }
    }

    /**
     * 递减
     * @param key
     * @param count
     * @return
     */
    public long decr(String key,long count){
        if(StringUtils.isBlank(key)){
            throw new RuntimeException("[decr] key is null !");
        }
        if(count<=0){
            throw new RuntimeException("[decr] count <= 0 !");
        }
        try{
            Long result = redisTemplate.opsForValue().decrement(key,count);
            return result == null ? 0 : result;
        }catch (Exception e){
            throw new RuntimeException("[decr] method occur error : "+e.getMessage()+" !");
        }
    }

}
