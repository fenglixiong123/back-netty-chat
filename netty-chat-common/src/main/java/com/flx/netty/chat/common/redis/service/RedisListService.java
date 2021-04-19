package com.flx.netty.chat.common.redis.service;

import java.util.List;

/**
 * @Author: Fenglixiong
 * @Date: 2020/9/2 19:58
 * @Description:
 */
public interface RedisListService {

    /**
     * 通过下标获取list的值
     * @param key
     * @param index
     * @return
     */
    Object lGetByIndex(String key,long index);

    /**
     * 根据索引修改list中的数据
     * @param key
     * @param index
     * @param value
     * @return
     */
    boolean lSetByIndex(String key,long index,Object value);

    /**
     * 获取指定区间内的list的值 0 到 -1代表所有值
     * @param key
     * @param start
     * @param end
     * @return
     */
    List<Object> lGet(String key, long start, long end);

    /**
     * 将list值放入缓存
     * @param key
     * @param value
     * @return
     */
    boolean lSet(String key,Object value);

    /**
     * 带失效时间的设置值
     * @param key
     * @param value
     * @param expire
     * @return
     */
    boolean lSetWithExpire(String key,Object value,long expire);

    /**
     * 设置多个值
     * @param key
     * @param values
     * @return
     */
    boolean lSetMulti(String key,List<Object> values);

    /**
     * 设置带失效时间的
     * @param key
     * @param values
     * @param expire
     * @return
     */
    boolean lSetMultiWithExpire(String key,List<Object> values,long expire);

    /**
     * list的长度
     * @param key
     * @return
     */
    long lSize(String key);

    /**
     * 删除N个值为value的项
     * @param key
     * @param count
     * @param value
     * @return
     */
    boolean lDel(String key,long count,Object value);
    
    
}
