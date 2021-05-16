package com.flx.netty.chat.auth.api.vo;

import com.flx.netty.chat.common.vo.BaseVO;
import lombok.Data;

/**
 *  VO实体类
 *
 * @author Fenglixiong
 * @since 2021-05-16
 */

@Data
public class PermissionVO extends BaseVO {

    /**
     * 父权限id
     */
    private Long pid;

    /**
     * 权限编码
     */
    private String code;

    /**
     * 权限名称
     */
    private String name;

    /**
     * 权限路径
     */
    private String path;

    /**
     * 权限图标
     */
    private String icon;

    /**
     * 排序
     */
    private Integer order;

}
