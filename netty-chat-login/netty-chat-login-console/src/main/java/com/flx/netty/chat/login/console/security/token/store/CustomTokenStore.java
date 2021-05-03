package com.flx.netty.chat.login.console.security.token.store;

import com.flx.netty.chat.login.console.security.property.CustomSecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Optional;
import java.util.UUID;

/**
 * @Author: Fenglixiong
 * @Date: 2021/5/3 13:04
 * @Description:
 */
@Slf4j
@Component
public class CustomTokenStore {

    /**
     * token默认存储在内存中
     */
    private final static String TOKEN_STORE_MEMORY = "memory";
    private final static String TOKEN_STORE_REDIS = "redis";
    private final static String TOKEN_STORE_JDBC = "jdbc";

    @Autowired
    private CustomSecurityProperties securityProperties;
    @Autowired(required = false)
    private RedisConnectionFactory redisConnectionFactory;
    @Autowired
    private DataSource dataSource;

    public TokenStore getTokenStore(){
        String tokenStore = Optional.ofNullable(securityProperties.getTokenStore()).orElse(TOKEN_STORE_MEMORY);
        switch (tokenStore){
            case TOKEN_STORE_MEMORY:
                log.info("Token store in memory !");
                return new InMemoryTokenStore();
            case TOKEN_STORE_REDIS:
                if(redisConnectionFactory==null){
                    log.info("Token store in memory !");
                    return new InMemoryTokenStore();
                }
                log.info("Token store in redis !");
                return new RedisTokenStore(redisConnectionFactory);
            case TOKEN_STORE_JDBC:
                JdbcTokenStore jdbcTokenStore = new JdbcTokenStore(dataSource);
                jdbcTokenStore.setAuthenticationKeyGenerator(auth->"FYK"+ UUID.randomUUID().toString().replace("-", ""));
                log.info("Token store in database !");
                return jdbcTokenStore;
            default:
                return null;
        }
    }

}
