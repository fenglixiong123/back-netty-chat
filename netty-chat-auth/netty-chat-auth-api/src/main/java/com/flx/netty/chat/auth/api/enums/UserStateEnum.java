package com.flx.netty.chat.auth.api.enums;

/**
 * @Author: Fenglixiong
 * @Date: 2021/4/12 0:36
 * @Description: 用户状态
 */
public enum UserStateEnum {

    effective("有效"),//有效
    invalid("无效"),//无效
    deleted("删除"),//删除
    locked("锁定"),//锁定
    expired("过期");//过期

    UserStateEnum(String desc) {
        this.desc = desc;
    }

    private String desc;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}
