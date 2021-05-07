package com.flx.netty.chat.auth.console.security.token.store.way;

import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * @Author: Fenglixiong
 * @Date: 2021/5/7 14:40
 * @Description:
 */
public interface TokenWay {

    /**
     * 获取Token存储方式
     * @return
     */
    TokenStore getTokenStore();

}
