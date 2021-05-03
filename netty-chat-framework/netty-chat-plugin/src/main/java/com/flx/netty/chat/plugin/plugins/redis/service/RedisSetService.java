package com.flx.netty.chat.plugin.plugins.redis.service;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Fenglixiong
 * @Date: 2020/9/2 19:58
 * @Description:
 */
public interface RedisSetService {

    /**
     * 根据key获取set的所有值
     * @param key
     * @return
     */
    Set<Object> sGet(String key);

    /**
     * 将数据放入set缓存
     * @param key
     * @param values
     * @return
     */
    boolean sSet(String key,Object ... values);

    /**
     * 带失效时间的set设置
     * @param key
     * @param values
     * @param expire
     * @return
     */
    boolean sSetWithExpire(String key, long expire, TimeUnit unit, Object ... values);

    /**
     * 是否有某个value
     * @param key
     * @param value
     * @return
     */
    boolean sHasKey(String key,Object value);

    /**
     * 获取set的长度
     * @param key
     * @return
     */
    long sSize(String key);

    /**
     * 移除value值
     * @param key
     * @param values
     * @return
     */
    boolean sDel(String key,Object ... values);

}
