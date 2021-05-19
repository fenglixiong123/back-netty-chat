package com.flx.netty.chat.admin.common.redis.impl;


import com.flx.netty.chat.admin.common.except.element.RedisException;
import com.flx.netty.chat.admin.common.redis.RedisHashService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Fenglixiong
 * @Date: 2020/5/27 16:48
 * @Description:
 */
@Slf4j
@Component
public class RedisHashServiceImpl implements RedisHashService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private RedisBaseServiceImpl redisBaseService;

    /**
     * Hash Get
     * @param key 键
     * @param item 项
     * @return
     */
    public Object hGet(String key,String item){
        if(StringUtils.isBlank(key)){
            throw new RedisException("[hGet] key is null !");
        }
        if(StringUtils.isBlank(item)){
            throw new RedisException("[hGet] item is null !");
        }
        try {
            return redisTemplate.opsForHash().get(key,item);
        }catch (Exception e){
            throw new RedisException("[hGet] method occur error : "+e.getMessage()+" !");
        }
    }

    /**
     * Hash Set 设置数据，不存在则创建
     * @param key
     * @param item
     * @return
     */
    public boolean hSet(String key,String item,Object value){
        if(StringUtils.isBlank(key)){
            throw new RedisException("[hSet] key is null !");
        }
        if(StringUtils.isBlank(key)){
            throw new RedisException("[hSet] item is null !");
        }
        try {
            redisTemplate.opsForHash().put(key,item,value);
            return true;
        }catch (Exception e){
            throw new RedisException("[hSet] method occur error : "+e.getMessage()+" !");
        }
    }

    /**
     * Hash Set 设置数据，不存在则创建
     * @param key
     * @param item
     * @return
     */
    public boolean hSetWithExpire(String key, String item, Object value, long expire, TimeUnit unit){
        if(StringUtils.isBlank(key)){
            throw new RedisException("[hSetWithExpire] key is null !");
        }
        if(StringUtils.isBlank(item)){
            throw new RedisException("[hSetWithExpire] item is null !");
        }
        if(expire<=0){
            throw new RedisException("[hSetWithExpire] expire time is illegal !");
        }
        try {
            redisTemplate.opsForHash().put(key,item,value);
            redisBaseService.expire(key,expire,unit);
            return true;
        }catch (Exception e){
            throw new RedisException("[hSetWithExpire] method occur error : "+e.getMessage()+" !");
        }
    }

    /**
     * 获取hashKey的所有键值对
     * @param key
     * @return
     */
    public Map<Object,Object> hmGet(String key){
        if(StringUtils.isBlank(key)){
            throw new RedisException("[hmGet] key is null !");
        }
        try {
            return redisTemplate.opsForHash().entries(key);
        }catch (Exception e){
            throw new RedisException("[hmGet] method occur error : "+e.getMessage()+" !");
        }
    }

    /**
     * HashSet 设置多个键值对
     * @param key
     * @param map
     * @return
     */
    public boolean hmSet(String key,Map<String,Object> map){
        if(StringUtils.isBlank(key)){
            throw new RedisException("[hmSet] key is null !");
        }
        try {
            redisTemplate.opsForHash().putAll(key,map);
            return true;
        }catch (Exception e){
            throw new RedisException("[hmSet] method occur error : "+e.getMessage()+" !");
        }
    }

    /**
     * HashSet 设置多个键值对带过期时间
     * @param key
     * @param map
     * @return
     */
    public boolean hmSetWithExpire(String key,Map<String,Object> map,long expire,TimeUnit unit){
        if(StringUtils.isBlank(key)){
            throw new RedisException("[hmSetWithExpire] key is null !");
        }
        if(expire<=0){
            throw new RedisException("[hmSetWithExpire] expire time is illegal !");
        }
        try {
            redisTemplate.opsForHash().putAll(key,map);
            redisBaseService.expire(key,expire,unit);
            return true;
        }catch (Exception e){
            throw new RedisException("[hmSetWithExpire] method occur error : "+e.getMessage()+" !");
        }
    }

    /**
     * 删除Hash表中的值
     * @param key
     * @param item
     * @return
     */
    public boolean hDel(String key,Object ... item){
        if(StringUtils.isBlank(key)){
            throw new RedisException("[hDel] key is null !");
        }
        try {
            Long result = redisTemplate.opsForHash().delete(key,item);
            return result != null ;
        }catch (Exception e){
            throw new RedisException("[hDel] method occur error : "+e.getMessage()+" !");
        }
    }

    /**
     * 判断hash表中是否存在该项值
     * @param key
     * @param item
     * @return
     */
    public boolean hHasKey(String key,String item){
        if(StringUtils.isBlank(key)){
            throw new RedisException("[hHasKey] key is null !");
        }
        if(StringUtils.isBlank(item)){
            throw new RedisException("[hHasKey] item is null !");
        }
        try {
            return redisTemplate.opsForHash().hasKey(key,item);
        }catch (Exception e){
            throw new RedisException("[hHasKey] method occur error : "+e.getMessage()+" !");
        }
    }

    /**
     * Hash 递增，如果不存在则创建 并返回新增后的值
     * @param key
     * @param item
     * @param by
     * @return
     */
    public double hIncr(String key,String item,double by){
        if(StringUtils.isBlank(key)){
            throw new RedisException("[hIncr] key is null !");
        }
        if(StringUtils.isBlank(item)){
            throw new RedisException("[hIncr] item is null !");
        }
        if(by<=0){
            throw new RedisException("[hIncr] by <= 0 !");
        }
        try {
            return redisTemplate.opsForHash().increment(key,item,by);
        }catch (Exception e){
            throw new RedisException("[hIncr] method occur error : "+e.getMessage()+" !");
        }
    }

    /**
     * 减少多少
     * @param key
     * @param item
     * @param by
     * @return
     */
    public double hDecr(String key,String item,double by){
        if(StringUtils.isBlank(key)){
            throw new RedisException("[hDecr] key is null !");
        }
        if(StringUtils.isBlank(item)){
            throw new RedisException("[hDecr] item is null !");
        }
        if(by<=0){
            throw new RedisException("[hDecr] by < 0 !");
        }
        try {
            return redisTemplate.opsForHash().increment(key,item,-by);
        }catch (Exception e){
            throw new RedisException("[hDecr] method occur error : "+e.getMessage()+" !");
        }
    }

}
