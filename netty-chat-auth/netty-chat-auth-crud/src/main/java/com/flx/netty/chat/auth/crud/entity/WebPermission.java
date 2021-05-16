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
@TableName("web_permission")
@ApiModel(value="Permission对象", description="")
public class WebPermission extends BaseDO {


    @ApiModelProperty(value = "父权限id")
    @TableField("pid")
    private Long pid;

    @ApiModelProperty(value = "权限编码")
    @TableField("code")
    private String code;

    @ApiModelProperty(value = "权限名称")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "权限路径")
    @TableField("path")
    private String path;

    @ApiModelProperty(value = "权限图标")
    @TableField("icon")
    private String icon;

    @ApiModelProperty(value = "排序")
    @TableField("order")
    private Integer order;


    public static final String PID = "pid";

    public static final String CODE = "code";

    public static final String NAME = "name";

    public static final String PATH = "path";

    public static final String ICON = "icon";

    public static final String ORDER = "order";

    @Override
    protected Serializable pkVal() {
        return null;
    }

}
