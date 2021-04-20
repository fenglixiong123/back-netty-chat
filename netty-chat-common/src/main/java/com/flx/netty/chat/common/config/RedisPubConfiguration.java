package com.flx.netty.chat.common.config;

import com.flx.netty.chat.common.redis.message.handler.RedisMessageHandler;
import com.flx.netty.chat.common.redis.message.property.RedisMessageProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * @Author Fenglixiong
 * @Create 2021/4/20 2:38
 * @Description
 **/
@Slf4j
@Configuration
public class RedisPubConfiguration {

    @Autowired
    private RedisMessageProperties messageProperties;

    /**
     * Redis消息监听器容器
     * @param connectionFactory
     * @return
     */
    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        //订阅消息的通道,多通道
        Map<String, ? extends RedisMessageHandler> topicMap = messageProperties.getTopicMap();
        if(!topicMap.isEmpty()){
            //这个container 可以添加多个 messageListener
            topicMap.forEach((k,v)->container.addMessageListener(listenerAdapter(v),new PatternTopic(k)));
        }
        return container;
    }

    /**
     * 配置消息接收处理类
     * @param messageHandler  自定义消息接收类
     * @return
     */
    @Bean()
    @Scope("prototype")
    MessageListenerAdapter listenerAdapter(RedisMessageHandler messageHandler) {
        //这个地方 是给messageListenerAdapter 传入一个消息接受的处理器，利用反射的方法调用“receiveMessage”
        //也有好几个重载方法，这边默认调用处理器的方法 叫handleMessage 可以自己到源码里面看
        return new MessageListenerAdapter(messageHandler, "onMessage");//注意2个通道调用的方法都要为receiveMessage
    }

    @PostConstruct
    public void init() {
        log.info("*************************************************");
        log.info("*                                               *");
        log.info("*               RedisPub Success                *");
        log.info("*                                               *");
        log.info("*************************************************");
    }

}
