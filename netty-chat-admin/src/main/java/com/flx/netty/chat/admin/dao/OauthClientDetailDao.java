package com.flx.netty.chat.admin.dao;

import com.flx.netty.chat.admin.entity.OauthClientDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.flx.netty.chat.plugin.annotion.mybatis.DaoMapper;

/**
 * 客户端信息表 Mapper 接口
 *
 * @author Fenglixiong
 * @since 2021-05-19
 */
@DaoMapper
public interface OauthClientDetailDao extends BaseMapper<OauthClientDetail> {

}
