package com.flx.netty.chat.group.api.vo;

import com.flx.netty.chat.sdk.vo.BaseVO;
import lombok.Data;

/**
 * @Author: Fenglixiong
 * @Date: 2021/4/12 0:44
 * @Description: 群用户
 */
@Data
public class WebGroupUserVO extends BaseVO {

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
