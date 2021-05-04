package com.flx.netty.chat.login.console.security.token.store;

import com.flx.netty.chat.login.console.security.property.CustomSecurityProperties;
import com.flx.netty.chat.login.console.security.token.jwt.CustomJwtAccessTokenConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Optional;
import java.util.UUID;

/**
 * @Author: Fenglixiong
 * @Date: 2021/5/3 13:04
 * @Description: Token存储方式
 * token+redis方式好处是服务端可以主动让token失效，不好的是要占用token资源
 * jwt的好处是去中心化，可以实现服务端无感知，便于分布式系统使用，但是服务端不能去中断和失效token
 */
@Slf4j
@Component
public class CustomTokenStore {

    /**
     * token默认存储在内存中
     */
    private final static String TOKEN_STORE_MEMORY = "memory";//内存模式
    private final static String TOKEN_STORE_REDIS = "redis";//redis模式
    private final static String TOKEN_STORE_JDBC = "jdbc";//jwt模式
    private final static String TOKEN_STORE_JWT = "jwt";//jdbc模式

    @Autowired(required = false)
    private DataSource dataSource;
    @Autowired(required = false)
    private CustomSecurityProperties securityProperties;
    @Autowired(required = false)
    private RedisConnectionFactory redisConnectionFactory;
    @Autowired(required = false)
    private CustomJwtAccessTokenConverter accessTokenConverter;

    public TokenStore getTokenStore(){
        String storeWay = Optional.ofNullable(securityProperties.getTokenStore()).orElse(TOKEN_STORE_MEMORY);
        switch (storeWay){
            case TOKEN_STORE_MEMORY:
                log.info("Token store in memory !");
                return new InMemoryTokenStore();
            case TOKEN_STORE_REDIS:
                if(redisConnectionFactory==null){
                    log.info("Token store in memory !");
                    return new InMemoryTokenStore();
                }
                log.info("Token store in redis !");
                RedisTokenStore redisTokenStore = new RedisTokenStore(redisConnectionFactory);
                redisTokenStore.setAuthenticationKeyGenerator(auth->"FLX"+ UUID.randomUUID().toString().replace("-", ""));
                return redisTokenStore;
            case TOKEN_STORE_JWT:
                if(accessTokenConverter==null){
                    log.info("Token store in memory !");
                    return new InMemoryTokenStore();
                }
                log.info("Token store in jwt !");
                accessTokenConverter.setSigningKey(securityProperties.getSigningKey());//设置JWT签名密钥
                return new JwtTokenStore(accessTokenConverter);
            case TOKEN_STORE_JDBC:
                if(dataSource==null){
                    log.info("Token store in memory !");
                    return new InMemoryTokenStore();
                }
                JdbcTokenStore jdbcTokenStore = new JdbcTokenStore(dataSource);
                jdbcTokenStore.setAuthenticationKeyGenerator(auth->"FLX"+ UUID.randomUUID().toString().replace("-", ""));
                log.info("Token store in database !");
                return jdbcTokenStore;
            default:
                return null;
        }
    }

}
