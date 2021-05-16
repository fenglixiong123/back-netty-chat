package com.flx.netty.chat.auth.crud.dao;

import com.flx.netty.chat.auth.crud.entity.Role;
import com.flx.netty.chat.plugin.plugins.mybatis.base.BaseDao;

import com.flx.netty.chat.plugin.annotion.mybatis.DaoMapper;


/**
 *  Dao层操作类
 *
 * @author Fenglixiong
 * @since 2021-05-16
 */

@DaoMapper
public interface RoleDao extends BaseDao<Role> {

}
