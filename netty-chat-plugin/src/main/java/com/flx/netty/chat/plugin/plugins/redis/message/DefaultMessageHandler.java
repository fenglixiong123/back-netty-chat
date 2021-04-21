package com.flx.netty.chat.plugin.plugins.redis.message;

import com.flx.netty.chat.plugin.plugins.redis.message.handler.RedisMessageHandler;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;

/**
 * @Author: Fenglixiong
 * @Date: 2021/4/21 17:13
 * @Description:
 */
@Slf4j
public class DefaultMessageHandler extends RedisMessageHandler {


    @Override
    public void handleMessage(Message message) {
        log.info(new String(message.getBody(), CharsetUtil.UTF_8));
    }
}
