package com.flx.netty.chat.plugin.plugins.redis.message.handler;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

/**
 * @Author Fenglixiong
 * @Create 2021/4/20 2:42
 * @Description
 **/
@Component
public abstract class RedisMessageHandler implements MessageListener {

    @Override
    public void onMessage(Message message, byte[] bytes) {
        this.handleMessage(message);
    }

    /**
     * 需要有实现类来调用
     * @param message
     */
    public abstract void handleMessage(Message message);
}
