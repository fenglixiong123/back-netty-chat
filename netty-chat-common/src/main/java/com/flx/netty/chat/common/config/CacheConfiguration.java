package com.flx.netty.chat.common.config;

import com.flx.netty.chat.common.utils.json.JacksonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

/**
 * @Author Fenglixiong
 * @Create 2021/4/21 0:39
 * @Description 利用redis缓存中心来实现缓存
 **/
@Slf4j
@EnableCaching
@Configuration
public class CacheConfiguration {

    /**
     * 由于自己写了缓存管理中心，缓存的时候就会自动使用redis，
     * 而不会在用spring默认的缓存机制
     * @param connectionFactory
     * @return
     */
    @Bean
    public RedisCacheManager redisCacheManager(RedisConnectionFactory connectionFactory){
        RedisSerializer<String> redisSerializer = new StringRedisSerializer();
        //解决查询缓存转换异常的问题
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = JacksonUtils.getJacksonSerializer();
        //配置序列化（解决乱码的问题）
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(2))//设置缓存的默认过期时间，也是使用Duration设置
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer))
                .disableCachingNullValues();
        return RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(config)
                .build();
    }

}
