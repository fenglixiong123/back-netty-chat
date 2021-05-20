package com.flx.netty.chat.admin.common.redis.impl;


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
            throw new RuntimeException("[hGet] key is null !");
        }
        if(StringUtils.isBlank(item)){
            throw new RuntimeException("[hGet] item is null !");
        }
        try {
            return redisTemplate.opsForHash().get(key,item);
        }catch (Exception e){
            throw new RuntimeException("[hGet] method occur error : "+e.getMessage()+" !");
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
            throw new RuntimeException("[hSet] key is null !");
        }
        if(StringUtils.isBlank(key)){
            throw new RuntimeException("[hSet] item is null !");
        }
        try {
            redisTemplate.opsForHash().put(key,item,value);
            return true;
        }catch (Exception e){
            throw new RuntimeException("[hSet] method occur error : "+e.getMessage()+" !");
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
            throw new RuntimeException("[hSetWithExpire] key is null !");
        }
        if(StringUtils.isBlank(item)){
            throw new RuntimeException("[hSetWithExpire] item is null !");
        }
        if(expire<=0){
            throw new RuntimeException("[hSetWithExpire] expire time is illegal !");
        }
        try {
            redisTemplate.opsForHash().put(key,item,value);
            redisBaseService.expire(key,expire,unit);
            return true;
        }catch (Exception e){
            throw new RuntimeException("[hSetWithExpire] method occur error : "+e.getMessage()+" !");
        }
    }

    /**
     * 获取hashKey的所有键值对
     * @param key
     * @return
     */
    public Map<Object,Object> hmGet(String key){
        if(StringUtils.isBlank(key)){
            throw new RuntimeException("[hmGet] key is null !");
        }
        try {
            return redisTemplate.opsForHash().entries(key);
        }catch (Exception e){
            throw new RuntimeException("[hmGet] method occur error : "+e.getMessage()+" !");
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
            throw new RuntimeException("[hmSet] key is null !");
        }
        try {
            redisTemplate.opsForHash().putAll(key,map);
            return true;
        }catch (Exception e){
            throw new RuntimeException("[hmSet] method occur error : "+e.getMessage()+" !");
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
            throw new RuntimeException("[hmSetWithExpire] key is null !");
        }
        if(expire<=0){
            throw new RuntimeException("[hmSetWithExpire] expire time is illegal !");
        }
        try {
            redisTemplate.opsForHash().putAll(key,map);
            redisBaseService.expire(key,expire,unit);
            return true;
        }catch (Exception e){
            throw new RuntimeException("[hmSetWithExpire] method occur error : "+e.getMessage()+" !");
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
            throw new RuntimeException("[hDel] key is null !");
        }
        try {
            Long result = redisTemplate.opsForHash().delete(key,item);
            return result != null ;
        }catch (Exception e){
            throw new RuntimeException("[hDel] method occur error : "+e.getMessage()+" !");
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
            throw new RuntimeException("[hHasKey] key is null !");
        }
        if(StringUtils.isBlank(item)){
            throw new RuntimeException("[hHasKey] item is null !");
        }
        try {
            return redisTemplate.opsForHash().hasKey(key,item);
        }catch (Exception e){
            throw new RuntimeException("[hHasKey] method occur error : "+e.getMessage()+" !");
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
            throw new RuntimeException("[hIncr] key is null !");
        }
        if(StringUtils.isBlank(item)){
            throw new RuntimeException("[hIncr] item is null !");
        }
        if(by<=0){
            throw new RuntimeException("[hIncr] by <= 0 !");
        }
        try {
            return redisTemplate.opsForHash().increment(key,item,by);
        }catch (Exception e){
            throw new RuntimeException("[hIncr] method occur error : "+e.getMessage()+" !");
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
            throw new RuntimeException("[hDecr] key is null !");
        }
        if(StringUtils.isBlank(item)){
            throw new RuntimeException("[hDecr] item is null !");
        }
        if(by<=0){
            throw new RuntimeException("[hDecr] by < 0 !");
        }
        try {
            return redisTemplate.opsForHash().increment(key,item,-by);
        }catch (Exception e){
            throw new RuntimeException("[hDecr] method occur error : "+e.getMessage()+" !");
        }
    }

}
