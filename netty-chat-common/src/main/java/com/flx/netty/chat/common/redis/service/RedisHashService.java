package com.flx.netty.chat.common.redis.service;

import java.util.Map;

/**
 * @Author: Fenglixiong
 * @Date: 2020/9/2 19:58
 * @Description:
 */
public interface RedisHashService {

    /**
     * Hash Get
     * @param key 键
     * @param item 项
     * @return
     */
    Object hGet(String key,String item);

    /**
     * Hash Set 设置数据，不存在则创建
     * @param key
     * @param item
     * @return
     */
    boolean hSet(String key,String item,Object value);

    /**
     * Hash Set 设置数据，不存在则创建
     * @param key
     * @param item
     * @return
     */
    boolean hSetWithExpire(String key,String item,Object value,long expire);

    /**
     * 获取hashKey的所有键值对
     * @param key
     * @return
     */
    Map<Object,Object> hmGet(String key);

    /**
     * HashSet 设置多个键值对
     * @param key
     * @param map
     * @return
     */
    boolean hmSet(String key,Map<String,Object> map);

    /**
     * HashSet 设置多个键值对带过期时间
     * @param key
     * @param map
     * @return
     */
    boolean hmSetWithExpire(String key,Map<String,Object> map,long expire);

    /**
     * 删除Hash表中的值
     * @param key
     * @param item
     * @return
     */
    boolean hDel(String key,Object ... item);

    /**
     * 判断hash表中是否存在该项值
     * @param key
     * @param item
     * @return
     */
    boolean hHasKey(String key,String item);

    /**
     * Hash 递增，如果不存在则创建 并返回新增后的值
     * @param key
     * @param item
     * @param by
     * @return
     */
    double hIncr(String key,String item,double by);

    /**
     * 减少多少
     * @param key
     * @param item
     * @param by
     * @return
     */
    double hDecr(String key,String item,double by);
    
    
}
