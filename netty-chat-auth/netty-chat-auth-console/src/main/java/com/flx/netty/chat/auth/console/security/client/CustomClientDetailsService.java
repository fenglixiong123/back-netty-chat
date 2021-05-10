package com.flx.netty.chat.auth.console.security.client;

import com.flx.netty.chat.auth.console.security.user.password.CustomPasswordEncoder;
import com.flx.netty.chat.auth.crud.entity.ClientDetail;
import com.flx.netty.chat.auth.crud.manager.ClientDetailManager;
import com.flx.netty.chat.common.utils.StringUtils;
import com.flx.netty.chat.common.utils.json.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * @Author: Fenglixiong
 * @Date: 2021/5/3 12:20
 * @Description: 根据ClientId获取客户端的信息
 * 客户端在获取token的时候，会给你个clientId，然后根据这个clientId返回ClientDetails
 */
@Slf4j
@Component
public class CustomClientDetailsService implements ClientDetailsService {

    @Autowired
    private ClientDetailManager clientManager;
    @Autowired
    private CustomPasswordEncoder passwordEncoder;

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        try {
            log.info("loadClientByClientId clientId = {}",clientId);
            //从数据库获取client信息
            ClientDetail oAuthClient = clientManager.get("clientId",clientId);
            log.info("loadClientByClientId clientDetails = {}", JsonUtils.toJsonMsg(oAuthClient));
            return new ClientDetails() {

                /**
                 * 用于唯一标识每一个客户端(client)；注册时必须填写(也可以服务端自动生成)，这个字段是必须的，实际应用也有叫app_key
                 */
                @Override
                public String getClientId() {
                    return oAuthClient.getClientId();
                }

                @Override
                public boolean isSecretRequired() {
                    return true;
                }

                /**
                 * 注册填写或者服务端自动生成，实际应用也有叫app_secret, 必须要有前缀代表加密方式
                 * 必须加密处理(采用BCryptPasswordEncoder进行加密处理,否则报错Bad client credentials)
                 */
                @Override
                public String getClientSecret() {
                    return passwordEncoder.encode(oAuthClient.getClientSecret());
                }

                /**
                 * 客户端能访问的资源id集合，注册客户端时，根据实际需要可选择资源id，也可以根据不同的额注册流程，赋予对应的额资源id
                 */
                @Override
                public Set<String> getResourceIds() {
                    return StringUtils.toSet(oAuthClient.getResourceIds());
                }

                @Override
                public boolean isScoped() {
                    return true;
                }

                /**
                 * 指定client的权限范围，比如读写权限，比如移动端还是web端权限
                 * read,write / web,mobile
                 */
                @Override
                public Set<String> getScope() {
                    return StringUtils.toSet(oAuthClient.getScope());
                }

                /**
                 * 可选值
                 * 授权码模式:authorization_code
                 * 密码模式:password
                 * 刷新token: refresh_token
                 * 隐式模式: implicit
                 * 客户端模式: client_credentials
                 * 支持多个用逗号分隔
                 */
                @Override
                public Set<String> getAuthorizedGrantTypes() {
                    return StringUtils.toSet(oAuthClient.getAuthorizedGrantTypes());
                }



                /**
                 * 指定用户的权限范围，如果授权的过程需要用户登陆，该字段不生效，implicit和client_credentials需要
                 * ROLE_ADMIN,ROLE_USER
                 */
                @Override
                public Collection<GrantedAuthority> getAuthorities() {
                    return StringUtils.isNotBlank(oAuthClient.getAuthorities()) ?
                            AuthorityUtils.commaSeparatedStringToAuthorityList(oAuthClient.getAuthorities()) : Collections.emptyList();
                }

                /**
                 * 设置access_token的有效时间(秒),默认(12小时)
                 */
                @Override
                public Integer getAccessTokenValiditySeconds() {
                    return oAuthClient.getAccessTokenValidity();
                }

                /**
                 * 设置refresh_token有效期(秒)，默认(30天)
                 */
                @Override
                public Integer getRefreshTokenValiditySeconds() {
                    return oAuthClient.getRefreshTokenValidity();
                }

                /**
                 * 适用于authorization_code,implicit模式
                 *
                 * 客户端重定向uri，需要该值进行校验，注册时填写
                 */
                @Override
                public Set<String> getRegisteredRedirectUri() {
                    return StringUtils.toSet(oAuthClient.getWebServerRedirectUri());
                }

                /**
                 * 适用于authorization_code模式
                 *
                 * 默认false,设置用户是否自动approval操作,设置true跳过用户确认授权操作页面，直接跳到redirect_uri
                 */
                @Override
                public boolean isAutoApprove(String s) {
                    return oAuthClient.getAutoApprove().equals(Boolean.TRUE.toString());
                }

                /**
                 * 值必须是json格式
                 * {"key", "value"}
                 */
                @Override
                public Map<String, Object> getAdditionalInformation() {
                    return StringUtils.toMap(oAuthClient.getAdditionalInformation());
                }
            };
        } catch (Exception e) {
            log.error("loadClientByClientId error : {}",e.getMessage());
            throw new ClientRegistrationException("Client is not exist !");
        }

    }

}
