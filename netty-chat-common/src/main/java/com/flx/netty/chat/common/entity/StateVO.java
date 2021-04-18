package com.flx.netty.chat.common.entity;

import lombok.Data;

import java.util.List;

/**
 * @Author: Fenglixiong
 * @Date: 2021/4/12 19:32
 * @Description:
 */
@Data
public class StateVO {

    private List<Long> ids;

    private String state;

    private String updateUser;

}
