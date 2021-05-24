package com.flx.netty.chat.auth.crud.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.flx.netty.chat.plugin.plugins.mybatis.base.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 *  Entity实体类
 *
 * @author Fenglixiong
 * @since 2021-05-16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("web_role")
public class AuthRole extends BaseDO {


    /**
     * 角色编号
     */
    @TableField("`code`")
    private String code;

    /**
     * 角色名称
     */
    @TableField("`name`")
    private String name;

    /**
     * 排序
     */
    @TableField("`order`")
    private Integer order;


    public static final String CODE = "code";

    public static final String NAME = "name";

    public static final String ORDER = "order";

    @Override
    protected Serializable pkVal() {
        return null;
    }

}
