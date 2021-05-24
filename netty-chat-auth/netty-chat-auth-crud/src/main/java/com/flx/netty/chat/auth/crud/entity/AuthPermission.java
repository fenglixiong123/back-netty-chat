package com.flx.netty.chat.auth.crud.entity;

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
@TableName("web_permission")
public class AuthPermission extends BaseDO {

    /**
     * 父权限id
     */
    private Long pid;

    /**
     * 权限编码
     */
    private String code;

    /**
     * 权限名称
     */
    private String name;

    /**
     * 权限路径
     */
    private String path;

    /**
     * 权限方法
     */
    private String method;

    /**
     * 权限图标
     */
    private String icon;

    /**
     * 排序
     */
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
