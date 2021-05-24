package com.flx.netty.chat.auth.crud.dao;

import com.flx.netty.chat.auth.crud.entity.AuthClient;
import com.flx.netty.chat.plugin.plugins.mybatis.base.BaseDao;

import com.flx.netty.chat.plugin.annotion.mybatis.DaoMapper;


/**
 * 客户端信息表 Dao层操作类
 *
 * @author Fenglixiong
 * @since 2021-05-07
 */

@DaoMapper
public interface AuthClientDao extends BaseDao<AuthClient> {

}
