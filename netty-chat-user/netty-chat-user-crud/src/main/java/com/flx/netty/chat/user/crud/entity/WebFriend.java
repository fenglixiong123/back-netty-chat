package com.flx.netty.chat.user.crud.entity;

import com.flx.netty.chat.common.annotion.mybatis.TableName;
import com.flx.netty.chat.common.plugins.mybatis.base.BaseDO;
import lombok.Data;

/**
 * @Author: Fenglixiong
 * @Date: 2021/4/11 20:19
 * @Description: 好友
 */
@Data
@TableName(value = "web_friend")
public class WebFriend extends BaseDO {

    /**
     * 用户Id
     */
    private Long userId;

    /**
     * 好友Id
     */
    private Long friendId;

    /**
     * 备注昵称
     */
    private String remarkName;

    /**
     * 标签1#2#3
     */
    private String labels;

}
