package com.flx.netty.chat.login.console.security.client.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * @Author: Fenglixiong
 * @Date: 2021/5/3 12:28
 * @Description:
 */
@Data
public class OAuthClient {

    /**
     * 记录唯一性标识
     */
    private String id;

    /**
     * 客户端ID，唯一性标识，不可重复
     */
    private String clientId;

    /**
     * 此客户端可以访问的资源。如果为空，则可被调用方忽略
     */
    private Set<String> resourceIds;

    /**
     * 验证此客户端是否需要认证（1：需要认证；其他：不需要认证）。也就是说，如果不需要认证，则会忽略client_secret的校验
     */
    private boolean secretRequired;

    /**
     * 客户端密码（是否有效，与is_secretre_quired有关）
     */
    private String clientSecret;

    /**
     * 此客户端是否限于特定范围。如果为false，则将忽略身份验证请求的作用域。 （1：true；其他：false）
     */
    private boolean scoped;

    /**
     * 此客户端的范围。如果客户端没有作用域，则为空。
     */
    private Set<String> scope;

    /**
     * 为此客户端授权的授予类型。
     */
    private Set<String> authorizedGrantTypes;

    /**
     * 此客户端在“授权代码”访问授予期间使用的预定义重定向URI。
     */
    private Set<String> registeredRedirectUri;

    /**
     * 返回授予OAuth客户端的权限。请注意，这些权限不是使用授权访问令牌授予用户的权限。相反，这些权限是客户本身固有的。
     */
    private Collection<GrantedAuthority> authorities;

    /**
     * 客户端是否需要用户批准特定范围。（1：true；其他：false）
     */
    private Short isAutoApprove;

    /**
     * 此客户端的访问令牌有效期，单位：秒；
     */
    private Integer accessTokenValiditySeconds;

    /**
     * 此客户端的刷新令牌有效期，单位：秒；
     */
    private Integer refreshTokenValiditySeconds;

    private boolean autoApprove;

    private Map<String, Object> additionalInformation;

}
