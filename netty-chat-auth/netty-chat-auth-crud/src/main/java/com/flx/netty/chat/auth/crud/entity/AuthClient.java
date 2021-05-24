package com.flx.netty.chat.auth.crud.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.flx.netty.chat.plugin.plugins.mybatis.base.BaseDO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 客户端信息表 Entity实体类
 *
 * @author Fenglixiong
 * @since 2021-05-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("oauth_client_detail")
@ApiModel(value="ClientDetail对象", description="客户端信息表")
public class AuthClient extends BaseDO {


    @ApiModelProperty(value = "用于唯一标识每一个客户端(client)也称为(appKey)")
    @TableField("client_id")
    private String clientId;

    @ApiModelProperty(value = "客户端所能访问的资源id集合,多个资源时用逗号(,)分隔")
    @TableField("resource_ids")
    private String resourceIds;

    @ApiModelProperty(value = "用于指定客户端(client)的访问密匙也称为(appSecret)")
    @TableField("client_secret")
    private String clientSecret;

    @ApiModelProperty(value = "指定客户端申请的权限范围,可选值包括read,write,trust")
    @TableField("scope")
    private String scope;

    @ApiModelProperty(value = "客户端支持的grant_type")
    @TableField("authorized_grant_types")
    private String authorizedGrantTypes;

    @ApiModelProperty(value = "客户端重定向URI")
    @TableField("web_server_redirect_uri")
    private String webServerRedirectUri;

    @ApiModelProperty(value = "客户端所拥有的SpringSecurity的权限值(ROLE_USER)，多个用逗号(,)分隔")
    @TableField("authorities")
    private String authorities;

    @ApiModelProperty(value = "访问令牌有效时间值(单位:秒)")
    @TableField("access_token_validity")
    private Integer accessTokenValidity;

    @ApiModelProperty(value = "更新令牌有效时间值(单位:秒)")
    @TableField("refresh_token_validity")
    private Integer refreshTokenValidity;

    @ApiModelProperty(value = "预留字段,JSON格式")
    @TableField("additional_information")
    private String additionalInformation;

    @ApiModelProperty(value = "用户是否自动Approval操作")
    @TableField("auto_approve")
    private String autoApprove;


    public static final String CLIENT_ID = "client_id";

    public static final String RESOURCE_IDS = "resource_ids";

    public static final String CLIENT_SECRET = "client_secret";

    public static final String SCOPE = "scope";

    public static final String AUTHORIZED_GRANT_TYPES = "authorized_grant_types";

    public static final String WEB_SERVER_REDIRECT_URI = "web_server_redirect_uri";

    public static final String AUTHORITIES = "authorities";

    public static final String ACCESS_TOKEN_VALIDITY = "access_token_validity";

    public static final String REFRESH_TOKEN_VALIDITY = "refresh_token_validity";

    public static final String ADDITIONAL_INFORMATION = "additional_information";

    public static final String AUTO_APPROVE = "auto_approve";

    @Override
    protected Serializable pkVal() {
        return null;
    }

}
