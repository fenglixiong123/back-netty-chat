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
public class WebUserRoleVO {

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "角色id")
    private Long roleId;

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
