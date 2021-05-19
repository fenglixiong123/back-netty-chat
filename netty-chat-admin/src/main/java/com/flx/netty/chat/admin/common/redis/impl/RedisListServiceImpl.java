package com.flx.netty.chat.admin.common.redis.impl;

import com.flx.netty.chat.admin.common.except.element.RedisException;
import com.flx.netty.chat.admin.common.redis.RedisListService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Fenglixiong
 * @Date: 2020/5/27 16:51
 * @Description:
 */
@Slf4j
@Component
public class RedisListServiceImpl implements RedisListService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private RedisBaseServiceImpl redisBaseService;

    /**
     * 通过下标获取list的值
     * @param key
     * @param index
     * @return
     */
    public Object lGetByIndex(String key,long index){
        if(StringUtils.isBlank(key)){
            throw new RedisException("[lGetByIndex] key is null !");
        }
        if(index<0){
            throw new RedisException("[lGetByIndex] index < 0 !");
        }
        try {
            return redisTemplate.opsForList().index(key,index);
        }catch (Exception e){
            throw new RedisException("[lGetByIndex] method occur error : "+e.getMessage()+" !");
        }
    }

    /**
     * 根据索引修改list中的数据
     * @param key
     * @param index
     * @param value
     * @return
     */
    public boolean lSetByIndex(String key,long index,Object value){
        if(StringUtils.isBlank(key)){
            throw new RedisException("[lSetByIndex] key is null !");
        }
        if(index<0){
            throw new RedisException("[lSetByIndex] index < 0 !");
        }
        try {
            redisTemplate.opsForList().set(key,index,value);
            return true;
        }catch (Exception e){
            throw new RedisException("[lSetByIndex] method occur error : "+e.getMessage()+" !");
        }
    }

    /**
     * 获取指定区间内的list的值 0 到 -1代表所有值
     * @param key
     * @param start
     * @param end
     * @return
     */
    public List<Object> lGet(String key, long start, long end){
        if(StringUtils.isBlank(key)){
            throw new RedisException("[lGet] key is null !");
        }
        try {
            return redisTemplate.opsForList().range(key,start,end);
        }catch (Exception e){
            throw new RedisException("[sDel] method occur error : "+e.getMessage()+" !");
        }
    }

    /**
     * 将list值放入缓存
     * @param key
     * @param value
     * @return
     */
    public boolean lSet(String key,Object value){
        if(StringUtils.isBlank(key)){
            throw new RedisException("[lSet] key is null !");
        }
        try {
            Long result = redisTemplate.opsForList().rightPush(key,value);
            return result!=null;
        }catch (Exception e){
            throw new RedisException("[lSet] method occur error : "+e.getMessage()+" !");
        }
    }

    /**
     * 带失效时间的设置值
     * @param key
     * @param value
     * @param expire
     * @return
     */
    public boolean lSetWithExpire(String key, Object value, long expire, TimeUnit unit){
        if(StringUtils.isBlank(key)){
            throw new RedisException("[lSetWithExpire] key is null !");
        }
        if(expire<=0){
            throw new RedisException("[lSetWithExpire] expire time is illegal !");
        }
        try {
            redisTemplate.opsForList().rightPush(key,value);
            redisBaseService.expire(key,expire,unit);
            return true;
        }catch (Exception e){
            throw new RedisException("[lSetWithExpire] method occur error : "+e.getMessage()+" !");
        }
    }

    /**
     * 设置多个值
     * @param key
     * @param values
     * @return
     */
    public boolean lSetMulti(String key,List<Object> values){
        if(StringUtils.isBlank(key)){
            throw new RedisException("[lSetMulti] key is null !");
        }
        try {
            redisTemplate.opsForList().rightPushAll(key,values);
            return true;
        }catch (Exception e){
            throw new RedisException("[lSetMulti] method occur error : "+e.getMessage()+" !");
        }
    }

    /**
     * 设置带失效时间的
     * @param key
     * @param values
     * @param expire
     * @return
     */
    public boolean lSetMultiWithExpire(String key,List<Object> values,long expire,TimeUnit unit){
        if(StringUtils.isBlank(key)){
            throw new RedisException("[lSetMultiWithExpire] key is null !");
        }
        if(expire<=0){
            throw new RedisException("[lSetMultiWithExpire] expire time is illegal !");
        }
        try {
            redisTemplate.opsForList().rightPushAll(key,values);
            redisBaseService.expire(key,expire,unit);
            return true;
        }catch (Exception e){
            throw new RedisException("[lSetMultiWithExpire] method occur error : "+e.getMessage()+" !");
        }
    }

    /**
     * list的长度
     * @param key
     * @return
     */
    public long lSize(String key){
        if(StringUtils.isBlank(key)){
            throw new RedisException("[lSize] key is null !");
        }
        try {
            Long result = redisTemplate.opsForList().size(key);
            return result ==null ? 0 : result;
        }catch (Exception e){
            throw new RedisException("[lSize] method occur error : "+e.getMessage()+" !");
        }
    }

    /**
     * 删除N个值为value的项
     * @param key
     * @param count
     * @param value
     * @return
     */
    public boolean lDel(String key,long count,Object value){
        if(StringUtils.isBlank(key)){
            throw new RedisException("[lDel] key is null !");
        }
        try {
            Long result = redisTemplate.opsForList().remove(key,count,value);
            return result !=null;
        }catch (Exception e){
            throw new RedisException("[lDel] method occur error : "+e.getMessage()+" !");
        }
    }

}
