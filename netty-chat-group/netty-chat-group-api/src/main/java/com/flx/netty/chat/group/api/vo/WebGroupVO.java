package com.flx.netty.chat.group.api.vo;

import com.flx.netty.chat.sdk.vo.BaseVO;
import lombok.Data;

/**
 * @Author: Fenglixiong
 * @Date: 2021/4/9 18:15
 * @Description: 用户群
 */
@Data
public class WebGroupVO extends BaseVO {

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
