package com.flx.netty.chat.message.api.vo;

import com.flx.netty.chat.common.vo.BaseVO;
import lombok.Data;

import java.util.Date;

/**
 * @Author: Fenglixiong
 * @Date: 2021/4/12 0:58
 * @Description:
 */
@Data
public class WebGroupMessageVO extends BaseVO {

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
