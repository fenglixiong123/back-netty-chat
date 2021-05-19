package com.flx.netty.chat.admin.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 客户端信息表 VO实体类
 *
 * @author Fenglixiong
 * @since 2021-05-19
 */

@Data
public class OauthClientDetailVO {

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "用于唯一标识每一个客户端(client)也称为(appKey)")
    private String clientId;

    @ApiModelProperty(value = "客户端所能访问的资源id集合,多个资源时用逗号(,)分隔")
    private String resourceIds;

    @ApiModelProperty(value = "用于指定客户端(client)的访问密匙也称为(appSecret)")
    private String clientSecret;

    @ApiModelProperty(value = "指定客户端申请的权限范围,可选值包括read,write,trust")
    private String scope;

    @ApiModelProperty(value = "客户端支持的grant_type")
    private String authorizedGrantTypes;

    @ApiModelProperty(value = "客户端重定向URI")
    private String webServerRedirectUri;

    @ApiModelProperty(value = "客户端所拥有的SpringSecurity的权限值(ROLE_USER)，多个用逗号(,)分隔")
    private String authorities;

    @ApiModelProperty(value = "访问令牌有效时间值(单位:秒)")
    private Integer accessTokenValidity;

    @ApiModelProperty(value = "更新令牌有效时间值(单位:秒)")
    private Integer refreshTokenValidity;

    @ApiModelProperty(value = "预留字段,JSON格式")
    private String additionalInformation;

    @ApiModelProperty(value = "用户是否自动Approval操作")
    private String autoApprove;

    @ApiModelProperty(value = "状态")
    private String state;

    @ApiModelProperty(value = "创建者")
    private String createUser;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "最后更新者")
    private String updateUser;

    @ApiModelProperty(value = "最后更新时间")
    private Date updateTime;

}
