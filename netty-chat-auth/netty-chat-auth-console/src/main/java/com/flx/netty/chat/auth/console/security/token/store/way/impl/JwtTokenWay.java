package com.flx.netty.chat.auth.console.security.token.store.way.impl;

import com.flx.netty.chat.auth.console.security.property.CustomSecurityProperties;
import com.flx.netty.chat.auth.console.security.token.store.way.TokenWay;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @Author: Fenglixiong
 * @Date: 2021/5/7 15:56
 * @Description:
 */
@Slf4j
@Component
public class JwtTokenWay implements TokenWay {

    private final static String JWT_WAY_SY = "sy";//jwt对称加密模式
    private final static String JWT_WAY_ASY = "asy";//jwt非对称加密模式

    @Autowired
    private CustomSecurityProperties securityProperties;
    @Autowired
    @Qualifier("jwtAccessTokenConverter")
    private JwtAccessTokenConverter accessTokenConverter;

    @Override
    public TokenStore getTokenStore() {
        String jwtWay = Optional.ofNullable(securityProperties.getTokenStore()).orElse(JWT_WAY_SY);
        if(jwtWay.equals(JWT_WAY_SY)) {//对称加密
            accessTokenConverter.setSigningKey(securityProperties.getSigningKey());//设置JWT签名密钥
        }else if(jwtWay.equals(JWT_WAY_ASY)){//非对称加密
//                KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("fyk-jwt.jks"),
//                        "fyk123".toCharArray());
//                converter.setKeyPair(keyStoreKeyFactory.getKeyPair("fyk-jwt"));
        }else {//非对称加密
            log.info("Token store in memory !");
            return new InMemoryTokenStore();
        }
        log.info("Token store in jwt !");
        return new JwtTokenStore(accessTokenConverter);
    }

}
