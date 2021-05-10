package com.flx.netty.chat.auth.console.security.token.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;

/**
 * @Author: Fenglixiong
 * @Date: 2021/5/10 18:54
 * @Description:
 */
@Slf4j
public class CustomTokenService extends DefaultTokenServices {

    @Override
    public OAuth2AccessToken createAccessToken(OAuth2Authentication authentication) throws AuthenticationException {
        return super.createAccessToken(authentication);
    }

    /**
     * 读取token信息
     * @param authentication
     * @return
     */
    @Override
    public OAuth2AccessToken getAccessToken(OAuth2Authentication authentication) {
        return super.getAccessToken(authentication);
    }

    /**
     * 加载用户信息
     * @param accessTokenValue 远程客户端传来的token
     * @return
     * @throws AuthenticationException
     * @throws InvalidTokenException
     */
    @Override
    public OAuth2Authentication loadAuthentication(String accessTokenValue) throws AuthenticationException, InvalidTokenException {
        return super.loadAuthentication(accessTokenValue);
    }
}
