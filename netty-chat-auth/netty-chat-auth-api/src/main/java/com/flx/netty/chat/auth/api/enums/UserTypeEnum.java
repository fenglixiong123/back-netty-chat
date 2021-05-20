package com.flx.netty.chat.auth.api.enums;

/**
 * @Author: Fenglixiong
 * @Date: 2021/5/20 17:48
 * @Description:
 */
public enum UserTypeEnum {

    common("普通用户"),
    master("超级管理员");

    private String desc;

    UserTypeEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
