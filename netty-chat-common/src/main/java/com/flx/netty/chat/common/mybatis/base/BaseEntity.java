package com.flx.netty.chat.common.mybatis.base;

import lombok.Data;

import java.util.Date;

/**
 * @Author: Fenglixiong
 * @Date: 2020/8/5 18:44
 * @Description:
 */
@Data
public class BaseEntity {

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
