package com.flx.netty.chat.admin.common.config;

import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import com.flx.netty.chat.admin.common.redis.*;
import com.flx.netty.chat.admin.common.redis.impl.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @Author Fenglixiong
 * @Create 2021/5/20 3:02
 * @Description
 **/
@Slf4j
@Configuration
public class RedisConfiguration implements InitializingBean {

    @Autowired
    private RedisConnectionFactory factory;

    /**
     * 可以探索redis多库配置
     * 在注册一个bean配置如下:
     * LettuceConnectionFactory factory = new LettuceConnectionFactory();
     * factory.setDatabase(1);
     * factory.afterPropertiesSet();//必须初始化实例
     */
    @Bean
    public RedisTemplate<String,Object> redisTemplate(){

        RedisTemplate<String,Object> redisTemplate = new RedisTemplate<>();
        //序列化String类型的key,value
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericFastJsonRedisSerializer());
        //序列化Hash类型的key,value
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericFastJsonRedisSerializer());
        //设置factory
        redisTemplate.setConnectionFactory(factory);
        redisTemplate.afterPropertiesSet();

        return redisTemplate;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info(">>>>>>>>>>>>>Redis Successful<<<<<<<<<<<<");
    }

}
