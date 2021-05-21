package com.flx.netty.chat.admin.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 客户端信息表
 *
 * @author Fenglixiong
 * @since 2021-05-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("oauth_client_detail")
@ApiModel(value="OauthClientDetail对象", description="客户端信息表")
public class AuthClient extends Model<AuthClient> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

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

    @ApiModelProperty(value = "状态")
    @TableField(value = "state", fill = FieldFill.INSERT)
    private String state;

    @ApiModelProperty(value = "创建者")
    @TableField(value = "create_user", fill = FieldFill.INSERT)
    private String createUser;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "最后更新者")
    @TableField(value = "update_user", fill = FieldFill.INSERT_UPDATE)
    private String updateUser;

    @ApiModelProperty(value = "最后更新时间")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    public static final String ID = "id";

    public static final String CLIENT_ID = "client_id";

    public static final String RESOURCE_IDS = "resource_ids";

    public static final String SCOPE = "scope";

    public static final String CLIENT_SECRET = "client_secret";

    public static final String STATE = "state";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
