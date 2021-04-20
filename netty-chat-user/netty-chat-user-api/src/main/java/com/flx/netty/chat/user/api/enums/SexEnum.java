package com.flx.netty.chat.user.api.enums;

/**
 * @Author: Fenglixiong
 * @Date: 2021/4/9 19:02
 * @Description:
 */
public enum SexEnum {

    male("男"),//男
    female("女");//女

    SexEnum(String desc) {
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
