package com.flx.netty.chat.user.api.vo;

import com.flx.netty.chat.sdk.entity.BaseEntity;
import lombok.Data;

/**
 * @Author: Fenglixiong
 * @Date: 2021/4/11 20:21
 * @Description: 好友分组,好友标签
 */
@Data
public class WebLabelVO extends BaseEntity {

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
