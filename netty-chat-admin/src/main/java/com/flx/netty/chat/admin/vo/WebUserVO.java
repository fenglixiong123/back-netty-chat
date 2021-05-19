package com.flx.netty.chat.admin.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 *  VO实体类
 *
 * @author Fenglixiong
 * @since 2021-05-19
 */

@Data
public class WebUserVO {

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "密码问题")
    private String passwordQuestion;

    @ApiModelProperty(value = "密码答案")
    private String passwordAnswer;

    @ApiModelProperty(value = "头像")
    private String icon;

    @ApiModelProperty(value = "个性签名")
    private String signature;

    @ApiModelProperty(value = "昵称")
    private String phone;

    @ApiModelProperty(value = "性别")
    private String sex;

    @ApiModelProperty(value = "出生年月")
    private Date birthday;

    @ApiModelProperty(value = "教育学历")
    private String education;

    @ApiModelProperty(value = "学校")
    private String school;

    @ApiModelProperty(value = "邮件")
    private String email;

    @ApiModelProperty(value = "家庭住址")
    private String address;

    @ApiModelProperty(value = "过期时间")
    private Date expireTime;

    @ApiModelProperty(value = "状态")
    private String state;

    @ApiModelProperty(value = "创建者")
    private String createUser;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "最后更新者")
    private String updateUser;

    @ApiModelProperty(value = "最后更新时间")
    private Date updateTime;

}
