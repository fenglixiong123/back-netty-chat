package com.flx.netty.chat.auth.console.security.token.store.way.impl;

import com.flx.netty.chat.auth.console.security.token.store.way.TokenWay;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.stereotype.Component;

/**
 * @Author: Fenglixiong
 * @Date: 2021/5/7 14:39
 * @Description: Token存储于内存中
 */
@Slf4j
@Component
public class MemoryTokenWay implements TokenWay {


    @Override
    public TokenStore getTokenStore() {
        log.info("Token store in memory !");
        return new InMemoryTokenStore();
    }

}
