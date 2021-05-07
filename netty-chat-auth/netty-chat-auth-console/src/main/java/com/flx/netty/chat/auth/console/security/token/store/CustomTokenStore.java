package com.flx.netty.chat.auth.console.security.token.store;

import com.flx.netty.chat.auth.console.security.property.CustomSecurityProperties;
import com.flx.netty.chat.auth.console.security.token.store.way.impl.JdbcTokenWay;
import com.flx.netty.chat.auth.console.security.token.store.way.impl.JwtTokenWay;
import com.flx.netty.chat.auth.console.security.token.store.way.impl.MemoryTokenWay;
import com.flx.netty.chat.auth.console.security.token.store.way.impl.RedisTokenWay;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
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
    private final static String TOKEN_STORE_JWT = "jwt";//jwt模式

    @Autowired
    private MemoryTokenWay memoryTokenWay;
    @Autowired
    private JdbcTokenWay jdbcTokenWay;
    @Autowired
    private RedisTokenWay redisTokenWay;
    @Autowired
    private JwtTokenWay jwtTokenWay;

    @Autowired
    private CustomSecurityProperties securityProperties;


    public TokenStore getTokenStore(){
        String storeWay = Optional.ofNullable(securityProperties.getTokenStore()).orElse(TOKEN_STORE_MEMORY);
        switch (storeWay){
            /**
             * Token存储在内存中
             */
            case TOKEN_STORE_MEMORY:
                return memoryTokenWay.getTokenStore();
            /**
             * Token存储在Redis中
             */
            case TOKEN_STORE_REDIS:
                return redisTokenWay.getTokenStore();
            /**
             * Token存储在jwt中
             * jwt的缺点：已发布令牌不可控
             */
            case TOKEN_STORE_JWT:
                return jwtTokenWay.getTokenStore();
            /**
             * 采用数据库存储方式
             */
            case TOKEN_STORE_JDBC:
                return jdbcTokenWay.getTokenStore();
            default:
                return null;
        }
    }

}
