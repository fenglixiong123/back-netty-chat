package com.flx.netty.chat.user.crud.dao;

import com.flx.netty.chat.common.annotion.mybatis.DaoMapper;
import com.flx.netty.chat.common.mybatis.base.BaseDao;
import com.flx.netty.chat.user.crud.entity.WebUser;
import org.apache.ibatis.annotations.Select;

/**
 * @Author: Fenglixiong
 * @Date: 2020/7/24 17:25
 * @Description:
 */
@DaoMapper
public interface WebUserDao extends BaseDao<WebUser> {

    @Select("select count(1) from web_user")
    int countWebUser();

}
