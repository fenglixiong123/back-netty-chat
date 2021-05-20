package com.flx.netty.chat.auth.api.vo;

import com.flx.netty.chat.common.vo.BaseVO;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * @Author: Fenglixiong
 * @Date: 2021/4/11 16:48
 * @Description:
 */
@Data
public class WebUserVO extends BaseVO {

    /**
     * 昵称
     */
    @NotNull
    @Size(max = 64)
    private String nickName;

    /**
     * 用户名
     */
    @NotNull
    @Size(max = 64)
    private String userName;

    /**
     * 用户类型
     */
    @Size(max = 64)
    private String type;

    /**
     * 头像
     */
    @Size(max = 64)
    private String icon;

    /**
     * 个性签名
     */
    @Size(max = 128)
    private String signature;

    /**
     * 电话
     */
    @NotNull
    @Size(max = 64)
    private String phone;

    /**
     * 性别
     */
    @NotNull
    @Size(max = 10)
    private String sex;

    /**
     * 年龄
     */
    private int age;

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
    @NotNull
    @Size(max = 64)
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
