package com.flx.netty.chat.common.redis.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author: Fenglixiong
 * @Date: 2020/9/2 19:58
 * @Description:
 */
public interface RedisBaseService {

    //基础操作

    /**
     * 指定缓存失效时间
     * @param key 键
     * @param expire 时间
     * @return
     */
    boolean expire(String key,long expire);

    /**
     * 获取key失效时间
     * @param key
     * @return 返回0代表永久有效
     */
    long getExpire(String key);

    /**
     * 判断key是否存在
     * @param key
     * @return
     */
    boolean hasKey(String key);

    /**
     * 删除缓存
     * @param key
     */
    boolean delete(String key);

    /**
     * 删除多个缓存key
     * @param keys
     * @return
     */
    int deleteKeys(List<String> keys);
    
    
}
