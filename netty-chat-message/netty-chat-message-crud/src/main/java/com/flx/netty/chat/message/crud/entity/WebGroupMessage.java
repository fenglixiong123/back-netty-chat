package com.flx.netty.chat.message.crud.entity;

import com.flx.netty.chat.plugin.annotion.mybatis.TableName;
import com.flx.netty.chat.plugin.plugins.mybatis.base.BaseDO;
import lombok.Data;

import java.util.Date;

/**
 * @Author: Fenglixiong
 * @Date: 2021/4/12 0:58
 * @Description:
 */
@Data
@TableName(value = "web_group_message")
public class WebGroupMessage extends BaseDO {

    /**
     * 群Id
     */
    private Long groupId;

    /**
     * 发送者Id
     */
    private Long senderId;

    /**
     * 发送者昵称
     */
    private String sendName;

    /**
     * 消息类型
     */
    private String msgType;

    /**
     * 消息类型
     */
    private String message;

    /**
     * 发送时间
     */
    private Date sendTime;

}
