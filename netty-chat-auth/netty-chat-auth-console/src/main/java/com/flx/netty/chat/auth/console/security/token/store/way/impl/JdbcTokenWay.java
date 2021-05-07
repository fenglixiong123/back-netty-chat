package com.flx.netty.chat.auth.console.security.token.store.way.impl;

import com.flx.netty.chat.auth.console.security.token.store.way.TokenWay;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.UUID;

/**
 * @Author: Fenglixiong
 * @Date: 2021/5/7 15:54
 * @Description: Token存储在数据库中，需要增加几张表
 * oauth_access_token       访问令牌
 * oauth_refresh_token      更新令牌
 */
@Slf4j
@Component
public class JdbcTokenWay implements TokenWay {

    @Autowired(required = false)
    private DataSource dataSource;

    @Override
    public TokenStore getTokenStore() {
        if(dataSource==null){
            log.info("Token store in memory !");
            return new InMemoryTokenStore();
        }
        JdbcTokenStore jdbcTokenStore = new JdbcTokenStore(dataSource);
        jdbcTokenStore.setAuthenticationKeyGenerator(auth->"FLX"+ UUID.randomUUID().toString().replace("-", ""));
        log.info("Token store in database !");
        return jdbcTokenStore;
    }

}
