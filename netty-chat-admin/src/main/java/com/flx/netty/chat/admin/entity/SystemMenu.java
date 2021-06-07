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
 * 菜单信息
 *
 * @author Fenglixiong
 * @since 2021-05-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("system_menu")
public class SystemMenu extends Model<SystemMenu> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 父菜单id
     */
    @TableField("pid")
    private Long pid;

    /**
     * 菜单编码
     */
    @TableField("`code`")
    private String code;

    /**
     * 菜单名称
     */
    @TableField("`name`")
    private String name;

    /**
     * 权限路径
     */
    @TableField("path")
    private String path;

    /**
     * 菜单图标
     */
    @TableField("icon")
    private String icon;

    /**
     * 菜单排序
     */
    @TableField("`order`")
    private Integer order;

    /**
     * 状态
     */
    @TableField(value = "state")
    private String state;

    /**
     * 创建者
     */
    @TableField(value = "create_user")
    private String createUser;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 最后更新者
     */
    @TableField(value = "update_user")
    private String updateUser;

    /**
     * 最后更新时间
     */
    @TableField(value = "update_time")
    private Date updateTime;


    public static final String ID = "id";

    public static final String PID = "pid";

    public static final String CODE = "code";

    public static final String NAME = "name";

    public static final String PATH = "path";

    public static final String ICON = "icon";

    public static final String ORDER = "order";

    public static final String STATE = "state";

    public static final String CREATE_USER = "create_user";

    public static final String CREATE_TIME = "create_time";

    public static final String UPDATE_USER = "update_user";

    public static final String UPDATE_TIME = "update_time";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
