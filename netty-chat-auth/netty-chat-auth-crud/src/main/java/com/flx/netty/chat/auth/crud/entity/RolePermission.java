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
 *  Entity实体类
 *
 * @author Fenglixiong
 * @since 2021-05-16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("web_role_permission")
@ApiModel(value="RolePermission对象", description="")
public class RolePermission extends BaseDO {


    @ApiModelProperty(value = "角色id")
    @TableField("role_id")
    private Long roleId;

    @ApiModelProperty(value = "权限id")
    @TableField("permission_id")
    private Long permissionId;


    public static final String ROLE_ID = "role_id";

    public static final String PERMISSION_ID = "permission_id";

    @Override
    protected Serializable pkVal() {
        return null;
    }

}
