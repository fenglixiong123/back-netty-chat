package com.flx.netty.chat.plugin.config;

import com.flx.netty.chat.plugin.plugins.cache.CustomCacheManager;
import com.flx.netty.chat.plugin.plugins.redis.service.*;
import com.flx.netty.chat.plugin.plugins.redis.service.impl.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @Author: Fenglixiong
 * @Date: 2020/5/26 16:26
 * @Description:
 */
@Slf4j
@Configuration
public class RedisConfiguration {

    @Autowired
    private RedisConnectionFactory factory;

    @Bean
    public RedisTemplate<String,Object> redisTemplate(){

        RedisTemplate<String,Object> redisTemplate = new RedisTemplate<>();
        //序列化String类型的key,value
        redisTemplate.setKeySerializer(CustomCacheManager.STRING_SERIALIZER);
        redisTemplate.setValueSerializer(CustomCacheManager.FAST_JSON_SERIALIZER);
        //序列化Hash类型的key,value
        redisTemplate.setHashKeySerializer(CustomCacheManager.STRING_SERIALIZER);
        redisTemplate.setHashValueSerializer(CustomCacheManager.FAST_JSON_SERIALIZER);
        //设置factory
        redisTemplate.setConnectionFactory(factory);
        redisTemplate.afterPropertiesSet();

        return redisTemplate;
    }

    @Bean
    public RedisBaseService redisBaseService(){
        return new RedisBaseServiceImpl();
    }

    @Bean
    public RedisStringService redisStringService(){
        return new RedisStringServiceImpl();
    }

    @Bean
    public RedisHashService redisHashService(){
        return new RedisHashServiceImpl();
    }

    @Bean
    public RedisListService redisListService(){
        return new RedisListServiceImpl();
    }

    @Bean
    public RedisSetService redisSetService(){
        return new RedisSetServiceImpl();
    }

}
