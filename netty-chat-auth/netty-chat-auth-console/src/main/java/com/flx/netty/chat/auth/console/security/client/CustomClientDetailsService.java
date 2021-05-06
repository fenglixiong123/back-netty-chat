package com.flx.netty.chat.auth.console.security.client;

import com.flx.netty.chat.auth.console.security.model.OAuthClient;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * @Author: Fenglixiong
 * @Date: 2021/5/3 12:20
 * @Description: 根据ClientId获取客户端的信息
 * 客户端在获取token的时候，会给你个clientId，然后根据这个clientId返回ClientDetails
 */
@Component
public class CustomClientDetailsService implements ClientDetailsService {


    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {

        //从数据库获取client信息
        OAuthClient oAuthClient = new OAuthClient();
        //设置客户端信息
        return new ClientDetails() {
            @Override
            public String getClientId() {
                return oAuthClient.getClientId();
            }

            @Override
            public Set<String> getResourceIds() {
                return oAuthClient.getResourceIds();
            }

            @Override
            public boolean isSecretRequired() {
                return oAuthClient.isSecretRequired();
            }

            @Override
            public String getClientSecret() {
                return oAuthClient.getClientSecret();
            }

            @Override
            public boolean isScoped() {
                return oAuthClient.isScoped();
            }

            @Override
            public Set<String> getScope() {
                return oAuthClient.getScope();
            }

            @Override
            public Set<String> getAuthorizedGrantTypes() {
                return oAuthClient.getAuthorizedGrantTypes();
            }

            @Override
            public Set<String> getRegisteredRedirectUri() {
                return oAuthClient.getRegisteredRedirectUri();
            }

            @Override
            public Collection<GrantedAuthority> getAuthorities() {
                return oAuthClient.getAuthorities();
            }

            @Override
            public Integer getAccessTokenValiditySeconds() {
                return oAuthClient.getAccessTokenValiditySeconds();
            }

            @Override
            public Integer getRefreshTokenValiditySeconds() {
                return oAuthClient.getRefreshTokenValiditySeconds();
            }

            @Override
            public boolean isAutoApprove(String s) {
                return oAuthClient.isAutoApprove();
            }

            @Override
            public Map<String, Object> getAdditionalInformation() {
                return oAuthClient.getAdditionalInformation();
            }
        };
    }

}
