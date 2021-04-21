package com.flx.netty.chat.group.crud.entity;

import com.flx.netty.chat.plugin.annotion.mybatis.TableName;
import com.flx.netty.chat.plugin.plugins.mybatis.base.BaseDO;
import lombok.Data;

/**
 * @Author: Fenglixiong
 * @Date: 2021/4/9 18:15
 * @Description: 用户群
 */
@Data
@TableName(value = "web_group")
public class WebGroup extends BaseDO {

    /**
     * 群主
     */
    private Long ownerId;

    /**
     * 群名称
     */
    private String groupName;

    /**
     * 群头像
     */
    private String groupIcon;

    /**
     * 群公告
     */
    private String groupNotice;

    /**
     * 群介绍
     */
    private String groupIntro;

}
