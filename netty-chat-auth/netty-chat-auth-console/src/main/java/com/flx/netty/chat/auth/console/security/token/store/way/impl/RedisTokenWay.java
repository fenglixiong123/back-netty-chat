package com.flx.netty.chat.auth.console.security.token.store.way.impl;

import com.flx.netty.chat.auth.console.security.token.store.way.TokenWay;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @Author: Fenglixiong
 * @Date: 2021/5/7 15:57
 * @Description: Token存储在Redis中,目前中型微服务使用比较多
 */
@Slf4j
@Component
public class RedisTokenWay implements TokenWay {

    @Autowired(required = false)
    private RedisConnectionFactory redisConnectionFactory;

    @Override
    public TokenStore getTokenStore() {
        if(redisConnectionFactory==null){
            log.info("Token store in memory !");
            return new InMemoryTokenStore();
        }
        log.info("Token store in redis !");
        RedisTokenStore redisTokenStore = new RedisTokenStore(redisConnectionFactory);
        redisTokenStore.setAuthenticationKeyGenerator(auth->"FLX"+ UUID.randomUUID().toString().replace("-", ""));
        return redisTokenStore;
    }

}