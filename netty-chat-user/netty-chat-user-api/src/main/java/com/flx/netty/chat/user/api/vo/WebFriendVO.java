package com.flx.netty.chat.user.api.vo;

import com.flx.netty.chat.common.vo.BaseVO;
import lombok.Data;

/**
 * @Author: Fenglixiong
 * @Date: 2021/4/11 20:19
 * @Description: 好友
 */
@Data
public class WebFriendVO extends BaseVO {

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
