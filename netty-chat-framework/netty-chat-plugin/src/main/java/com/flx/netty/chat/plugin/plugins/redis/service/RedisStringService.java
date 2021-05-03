package com.flx.netty.chat.plugin.plugins.redis.service;

import java.util.concurrent.TimeUnit;

/**
 * @Author: Fenglixiong
 * @Date: 2020/9/2 19:58
 * @Description:
 */
public interface RedisStringService {

    /**
     * 获取值
     * @param key
     * @return
     */
    Object get(String key);

    /**
     * 设置值
     * @param key
     * @param value
     * @return
     */
    boolean set(String key,Object value);

    /**
     * 设置带失效时间的值
     * @param key
     * @param value
     * @param expire
     * @return
     */
    boolean set(String key, Object value, long expire, TimeUnit unit);

    /**
     * 不存在才设置
     * @param key
     * @param value
     * @param expire
     * @param unit
     * @return
     */
    boolean setIfAbsent(String key,Object value,long expire,TimeUnit unit);

    /**
     * 递增
     * @param key
     * @param count
     * @return
     */
    long incr(String key,long count);

    /**
     * 递减
     * @param key
     * @param count
     * @return
     */
    long decr(String key,long count);
    
    
}
