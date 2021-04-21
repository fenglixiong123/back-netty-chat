package com.flx.netty.chat.message.crud.dao;

import com.flx.netty.chat.plugin.annotion.mybatis.DaoMapper;
import com.flx.netty.chat.plugin.plugins.mybatis.base.BaseDao;
import com.flx.netty.chat.message.crud.entity.WebMessage;

/**
 * @Author: Fenglixiong
 * @Date: 2020/7/24 17:25
 * @Description:
 */
@DaoMapper
public interface WebMessageDao extends BaseDao<WebMessage> {

}
