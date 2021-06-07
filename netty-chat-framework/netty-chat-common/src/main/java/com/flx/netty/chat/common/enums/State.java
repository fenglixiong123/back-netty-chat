package com.flx.netty.chat.common.enums;

/**
 * 数据状态
 * @author fenglixiong
 * @date2018-12-03-10:22
 */
public enum State {
    effective("有效"),  //有效
    invalid("无效"),    //无效
    deleted("删除");     //删除

    State(String desc){
        this.desc = desc;
    }

    private String desc;

    public String getDesc(){
        return this.desc;
    }

    public void setDesc(String desc){
        this.desc = desc;
    }

}
