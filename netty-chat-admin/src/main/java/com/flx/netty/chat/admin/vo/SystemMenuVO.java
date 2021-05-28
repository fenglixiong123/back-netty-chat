package com.flx.netty.chat.admin.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 菜单信息 VO实体类
 *
 * @author Fenglixiong
 * @since 2021-05-28
 */

@Data
public class SystemMenuVO {

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "父菜单id")
    private Long pid;

    @ApiModelProperty(value = "菜单编码")
    private String code;

    @ApiModelProperty(value = "菜单名称")
    private String name;

    @ApiModelProperty(value = "权限路径")
    private String path;

    @ApiModelProperty(value = "菜单图标")
    private String icon;

    @ApiModelProperty(value = "菜单排序")
    private Integer order;

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
