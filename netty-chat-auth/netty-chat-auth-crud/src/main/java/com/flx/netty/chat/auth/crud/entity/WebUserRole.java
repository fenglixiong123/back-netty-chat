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
@TableName("web_user_role")
public class WebUserRole extends BaseDO {


    /**
     * 用户id
     */
    private Long userId;

    /**
     * 角色id
     */
    private Long roleId;


    public static final String USER_ID = "user_id";

    public static final String ROLE_ID = "role_id";

    @Override
    protected Serializable pkVal() {
        return null;
    }

}
