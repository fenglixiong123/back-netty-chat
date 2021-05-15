package com.flx.netty.chat.gateway.enums;

/**
 * @Author Fenglixiong
 * @Create 2021/5/15 19:42
 * @Description
 **/
public enum LogTypeEnum {

    REQUEST("请求日志");

    private String desc;

    LogTypeEnum(String desc){
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
