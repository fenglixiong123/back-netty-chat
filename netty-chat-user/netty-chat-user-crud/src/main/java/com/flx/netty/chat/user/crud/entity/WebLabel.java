package com.flx.netty.chat.user.crud.entity;

import com.flx.netty.chat.common.annotion.mybatis.TableName;
import com.flx.netty.chat.common.plugins.mybatis.base.BaseDO;
import lombok.Data;

/**
 * @Author: Fenglixiong
 * @Date: 2021/4/11 20:21
 * @Description: 好友分组,好友标签
 */
@Data
@TableName(value = "web_label")
public class WebLabel extends BaseDO {

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 标签名称
     */
    private String labelName;

    /**
     * 标签备注
     */
    private String remark;

}
