package com.flx.netty.chat.plugin.property;

import com.flx.netty.chat.plugin.plugins.redis.message.handler.RedisMessageHandler;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author Fenglixiong
 * @Create 2021/4/20 2:48
 * @Description
 **/
@Data
@ConfigurationProperties(prefix = "flx.redis",ignoreInvalidFields = true)
public class RedisMessageProperties {

    /**
     * 对应主题以及主题处理类
     */
    private Map<String,? extends RedisMessageHandler> topicMap;

}
