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
 * 
 *
 * @author Fenglixiong
 * @since 2021-05-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("web_role_permission")
@ApiModel(value="WebRolePermission对象", description="")
public class WebRolePermission extends Model<WebRolePermission> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "角色id")
    @TableField("role_id")
    private Long roleId;

    @ApiModelProperty(value = "权限id")
    @TableField("permission_id")
    private Long permissionId;

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


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}