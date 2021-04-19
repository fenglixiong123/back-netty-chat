package com.flx.netty.chat.common.redis.service.impl;

import com.flx.netty.chat.common.exception.element.RedisException;
import com.flx.netty.chat.common.redis.service.RedisBaseService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Fenglixiong
 * @Date: 2020/5/26 18:18
 * @Description:
 *
 * string操作
 * hash操作
 * set操作
 * list操作
 */
@Slf4j
@Component
public class RedisBaseServiceImpl implements RedisBaseService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 指定缓存失效时间
     * @param key 键
     * @param expire 时间
     * @return
     */
    public boolean expire(String key,long expire){
        if(StringUtils.isBlank(key)){
            throw new RedisException("[expire] key is null !");
        }
        if(expire<=0){
            throw new RedisException("[expire] expire time is illegal !");
        }
        try {
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
            return true;
        }catch (Exception e){
            throw new RedisException("[expire] method occur error : "+e.getMessage()+" !");
        }
    }

    /**
     * 获取key失效时间
     * @param key
     * @return 返回0代表永久有效
     */
    public long getExpire(String key){
        if(StringUtils.isBlank(key)){
            throw new RedisException("[getExpire] key is null !");
        }
        try {
            Long time = redisTemplate.getExpire(key,TimeUnit.SECONDS);
            return time == null ? -1 : time;
        }catch (Exception e){
            throw new RedisException("[getExpire] method occur error : "+e.getMessage()+" !");
        }
    }

    /**
     * 判断key是否存在
     * @param key
     * @return
     */
    public boolean hasKey(String key){
        if(StringUtils.isBlank(key)){
            throw new RedisException("[hasKey] key is null !");
        }
        try {
            return redisTemplate.hasKey(key) != null;
        }catch (Exception e){
            throw new RedisException("[hasKey] method occur error : "+e.getMessage()+" !");
        }
    }

    /**
     * 删除缓存
     * @param key
     */
    public boolean delete(String key){
        if(StringUtils.isBlank(key)){
            throw new RedisException("[delete] key is null !");
        }
        try {
            return redisTemplate.delete(key)!=null;
        }catch (Exception e){
            throw new RedisException("[delete] method occur error : "+e.getMessage()+" !");
        }
    }

    /**
     * 删除多个缓存key
     * @param keys
     * @return
     */
    public int deleteKeys(List<String> keys){
        if(CollectionUtils.isEmpty(keys)){
            throw new RedisException("[deleteKeys] list is null !");
        }
        try {
            Long result = redisTemplate.delete(keys);
            return result == null ? 0 : result.intValue();
        }catch (Exception e){
            throw new RedisException("[deleteKeys] method occur error : "+e.getMessage()+" !");
        }
    }

}
