package com.flx.netty.chat.admin.common.redis.impl;

import com.flx.netty.chat.admin.common.redis.RedisBaseService;
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
    public boolean expire(String key,long expire,TimeUnit unit){
        if(StringUtils.isBlank(key)){
            throw new RuntimeException("[expire] key is null !");
        }
        if(expire<=0){
            throw new RuntimeException("[expire] expire time is illegal !");
        }
        try {
            redisTemplate.expire(key, expire, unit);
            return true;
        }catch (Exception e){
            throw new RuntimeException("[expire] method occur error : "+e.getMessage()+" !");
        }
    }

    /**
     * 获取key失效时间
     * @param key
     * @return 返回0代表永久有效
     */
    public long getExpire(String key){
        if(StringUtils.isBlank(key)){
            throw new RuntimeException("[getExpire] key is null !");
        }
        try {
            Long time = redisTemplate.getExpire(key,TimeUnit.SECONDS);
            return time == null ? -1 : time;
        }catch (Exception e){
            throw new RuntimeException("[getExpire] method occur error : "+e.getMessage()+" !");
        }
    }

    /**
     * 判断key是否存在
     * @param key
     * @return
     */
    public boolean hasKey(String key){
        if(StringUtils.isBlank(key)){
            throw new RuntimeException("[hasKey] key is null !");
        }
        try {
            return redisTemplate.hasKey(key) != null;
        }catch (Exception e){
            throw new RuntimeException("[hasKey] method occur error : "+e.getMessage()+" !");
        }
    }

    /**
     * 删除缓存
     * @param key
     */
    public boolean delete(String key){
        if(StringUtils.isBlank(key)){
            throw new RuntimeException("[delete] key is null !");
        }
        try {
            return redisTemplate.delete(key)!=null;
        }catch (Exception e){
            throw new RuntimeException("[delete] method occur error : "+e.getMessage()+" !");
        }
    }

    /**
     * 删除多个缓存key
     * @param keys
     * @return
     */
    public int deleteKeys(List<String> keys){
        if(CollectionUtils.isEmpty(keys)){
            throw new RuntimeException("[deleteKeys] list is null !");
        }
        try {
            Long result = redisTemplate.delete(keys);
            return result == null ? 0 : result.intValue();
        }catch (Exception e){
            throw new RuntimeException("[deleteKeys] method occur error : "+e.getMessage()+" !");
        }
    }

}
