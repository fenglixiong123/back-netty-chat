package com.flx.netty.chat.auth.api.vo;

import com.flx.netty.chat.common.vo.BaseVO;
import lombok.Data;

/**
 * 客户端信息表 VO实体类
 *
 * @author Fenglixiong
 * @since 2021-05-07
 */

@Data
public class AuthClientVO extends BaseVO {

    /**
     * 用于唯一标识每一个客户端(client)也称为(appKey)
     */
    private String clientId;

    /**
     * 客户端所能访问的资源id集合,多个资源时用逗号(,)分隔
     */
    private String resourceIds;

    /**
     * 用于指定客户端(client)的访问密匙也称为(appSecret)
     */
    private String clientSecret;

    /**
     * 指定客户端申请的权限范围,可选值包括read,write,trust
     */
    private String scope;

    /**
     * 客户端支持的grant_type
     */
    private String authorizedGrantTypes;

    /**
     * 客户端重定向URI
     */
    private String webServerRedirectUri;

    /**
     * 客户端所拥有的SpringSecurity的权限值(ROLE_USER)，多个用逗号(,)分隔
     */
    private String authorities;

    /**
     * 访问令牌有效时间值(单位:秒)
     */
    private Integer accessTokenValidity;

    /**
     * 更新令牌有效时间值(单位:秒)
     */
    private Integer refreshTokenValidity;

    /**
     * 预留字段,JSON格式
     */
    private String additionalInformation;

    /**
     * 用户是否自动Approval操作
     */
    private String autoApprove;

}
