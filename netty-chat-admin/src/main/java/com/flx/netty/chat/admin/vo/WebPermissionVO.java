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
public class WebPermissionVO {

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "父权限id")
    private Long pid;

    @ApiModelProperty(value = "权限编码")
    private String code;

    @ApiModelProperty(value = "权限名称")
    private String name;

    @ApiModelProperty(value = "权限路径")
    private String path;

    @ApiModelProperty(value = "请求方法")
    private String method;

    @ApiModelProperty(value = "权限图标")
    private String icon;

    @ApiModelProperty(value = "排序")
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
