package com.flx.netty.chat.group.crud.entity;


import com.flx.netty.chat.plugin.annotion.mybatis.TableName;
import com.flx.netty.chat.plugin.plugins.mybatis.base.BaseDO;
import lombok.Data;

/**
 * @Author: Fenglixiong
 * @Date: 2021/4/12 0:44
 * @Description: 群用户
 */
@Data
@TableName(value = "web_group_user")
public class WebGroupUser extends BaseDO {

    /**
     * 群Id
     */
    private Long groupId;

    /**
     * 群成员
     */
    private Long userId;

    /**
     * 群昵称
     */
    private String remarkName;

}
