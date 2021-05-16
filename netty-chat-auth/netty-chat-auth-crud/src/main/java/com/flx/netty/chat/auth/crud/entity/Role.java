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
@TableName("web_role")
@ApiModel(value="Role对象", description="")
public class Role extends BaseDO {


    @ApiModelProperty(value = "角色编号")
    @TableField("code")
    private String code;

    @ApiModelProperty(value = "角色名称")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "排序")
    @TableField("order")
    private Integer order;


    public static final String CODE = "code";

    public static final String NAME = "name";

    public static final String ORDER = "order";

    @Override
    protected Serializable pkVal() {
        return null;
    }

}
