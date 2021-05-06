package com.flx.netty.chat.auth.crud.entity;

import com.flx.netty.chat.plugin.annotion.mybatis.TableName;
import com.flx.netty.chat.plugin.plugins.mybatis.base.BaseDO;
import lombok.Data;

import java.util.Date;

/**
 * @Author Fenglixiong
 * @Create 2021/4/9 2:21
 * @Description
 **/
@Data
@TableName(value = "web_user")
public class WebUser extends BaseDO {

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 密码问题
     */
    private String passwordQuestion;

    /**
     * 密码答案
     */
    private String passwordAnswer;

    /**
     * 头像
     */
    private String icon;

    /**
     * 个性签名
     */
    private String signature;

    /**
     * 电话
     */
    private String phone;

    /**
     * 性别
     */
    private String sex;

    /**
     * 生日
     */
    private Date birthday;

    /**
     * 教育
     */
    private String education;

    /**
     * 学校
     */
    private String school;

    /**
     * 邮件
     */
    private String email;

    /**
     * 地址
     */
    private String address;

    /**
     * 过期时间
     */
    private Date expireTime;


}
