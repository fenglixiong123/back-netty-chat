package com.flx.netty.chat.auth.api.enums;

/**
 * @Author: Fenglixiong
 * @Date: 2021/4/9 19:03
 * @Description:
 */
public enum EduEnum {

    doctor("博士"),//博士
    master("硕士"),//硕士
    college("本科"),//本科
    senior("高中"),//高中
    junior("初中"),//初中
    primary("小学");//小学

    EduEnum(String desc) {
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
