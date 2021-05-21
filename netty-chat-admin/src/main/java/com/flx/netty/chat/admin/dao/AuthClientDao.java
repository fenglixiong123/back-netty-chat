package com.flx.netty.chat.admin.dao;

import com.flx.netty.chat.admin.common.annotation.DaoMapper;
import com.flx.netty.chat.admin.entity.AuthClient;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;


/**
 * 客户端信息表 Mapper 接口
 *
 * @author Fenglixiong
 * @since 2021-05-19
 */
@DaoMapper
public interface AuthClientDao extends BaseMapper<AuthClient> {

}
