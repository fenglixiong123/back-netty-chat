package com.flx.netty.chat.common.utils.json;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

/**
 * @Author Fenglixiong
 * @Create 2021/4/21 0:42
 * @Description
 **/
public class JacksonUtils {

    /**
     * 获取jackson序列化注册器
     * @return
     */
    public static Jackson2JsonRedisSerializer<Object> getJacksonSerializer(){

        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);

        return jackson2JsonRedisSerializer;

    }

}
