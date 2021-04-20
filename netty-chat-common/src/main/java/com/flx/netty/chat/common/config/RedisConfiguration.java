package com.flx.netty.chat.common.config;

import com.flx.netty.chat.common.redis.service.*;
import com.flx.netty.chat.common.redis.service.impl.*;
import com.flx.netty.chat.common.utils.json.JacksonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @Author: Fenglixiong
 * @Date: 2020/5/26 16:26
 * @Description:
 */
@Slf4j
@Configuration
public class RedisConfiguration {

    @Bean
    public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<String,Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        //使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值
        Jackson2JsonRedisSerializer<Object> jacksonSerializer = JacksonUtils.getJacksonSerializer();
        redisTemplate.setValueSerializer(jacksonSerializer);
        redisTemplate.setHashValueSerializer(jacksonSerializer);
        //用StringRedisSerializer来序列化和反序列化redis的key值
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
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
