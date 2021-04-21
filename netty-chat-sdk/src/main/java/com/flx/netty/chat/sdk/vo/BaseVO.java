package com.flx.netty.chat.sdk.vo;

import lombok.Data;

import java.util.Date;

/**
 * @Author Fenglixiong
 * @Create 2021/4/21 2:16
 * @Description
 **/
@Data
public class BaseVO {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 状态
     */
    private String state;

    /**
     * 创建用户
     */
    private String createUser;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新用户
     */
    private String updateUser;

    /**
     * 更新时间
     */
    private Date updateTime;

}
