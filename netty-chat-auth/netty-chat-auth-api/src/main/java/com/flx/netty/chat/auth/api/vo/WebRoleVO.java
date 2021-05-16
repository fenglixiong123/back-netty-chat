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
public class WebRoleVO extends BaseVO {

    /**
     * 角色编号
     */
    private String code;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 排序
     */
    private Integer order;

}
