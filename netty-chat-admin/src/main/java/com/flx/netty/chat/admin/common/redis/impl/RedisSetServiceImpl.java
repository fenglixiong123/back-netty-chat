package com.flx.netty.chat.admin.common.redis.impl;

import com.flx.netty.chat.admin.common.redis.RedisSetService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Fenglixiong
 * @Date: 2020/5/27 16:50
 * @Description:
 */
@Slf4j
@Component
public class RedisSetServiceImpl implements RedisSetService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private RedisBaseServiceImpl redisBaseService;

    /**
     * 根据key获取set的所有值
     * @param key
     * @return
     */
    public Set<Object> sGet(String key){
        if(StringUtils.isBlank(key)){
            throw new RuntimeException("[sGet] key is null !");
        }
        try {
            return redisTemplate.opsForSet().members(key);
        }catch (Exception e){
            throw new RuntimeException("[sGet] method occur error : "+e.getMessage()+" !");
        }
    }

    /**
     * 将数据放入set缓存
     * @param key
     * @param values
     * @return
     */
    public boolean sSet(String key,Object ... values){
        if(StringUtils.isBlank(key)){
            throw new RuntimeException("[sSet] key is null !");
        }
        try {
            Long result = redisTemplate.opsForSet().add(key,values);
            return result != null;
        }catch (Exception e){
            throw new RuntimeException("[sSet] method occur error : "+e.getMessage()+" !");
        }
    }

    /**
     * 带失效时间的set设置
     * @param key
     * @param values
     * @param expire
     * @return
     */
    public boolean sSetWithExpire(String key, long expire, TimeUnit unit, Object ... values){
        if(StringUtils.isBlank(key)){
            throw new RuntimeException("[sSetWithExpire] key is null !");
        }
        if(expire<=0){
            throw new RuntimeException("[sSetWithExpire] expire time is illegal !");
        }
        try {
            Long result = redisTemplate.opsForSet().add(key,values);
            redisBaseService.expire(key,expire,unit);
            return result != null;
        }catch (Exception e){
            throw new RuntimeException("[sSetWithExpire] method occur error : "+e.getMessage()+" !");
        }
    }

    /**
     * 是否有某个value
     * @param key
     * @param value
     * @return
     */
    public boolean sHasKey(String key,Object value){
        if(StringUtils.isBlank(key)){
            throw new RuntimeException("[sHasKey] key is null !");
        }
        try {
            Boolean result = redisTemplate.opsForSet().isMember(key,value);
            return result ==null ? false : result;
        }catch (Exception e){
            throw new RuntimeException("[sHasKey] method occur error : "+e.getMessage()+" !");
        }
    }

    /**
     * 获取set的长度
     * @param key
     * @return
     */
    public long sSize(String key){
        if(StringUtils.isBlank(key)){
            throw new RuntimeException("[sSize] key is null !");
        }
        try {
            Long result = redisTemplate.opsForSet().size(key);
            return result ==null ? 0 : result;
        }catch (Exception e){
            throw new RuntimeException("[sSize] method occur error : "+e.getMessage()+" !");
        }
    }

    /**
     * 移除value值
     * @param key
     * @param values
     * @return
     */
    public boolean sDel(String key,Object ... values){
        if(StringUtils.isBlank(key)){
            throw new RuntimeException("[sDel] key is null !");
        }
        try {
            Long result = redisTemplate.opsForSet().remove(key,values);
            return result != null;
        }catch (Exception e){
            throw new RuntimeException("[sDel] method occur error : "+e.getMessage()+" !");
        }
    }

}
