package com.flx.netty.chat.auth.crud.dao;

import com.flx.netty.chat.auth.crud.entity.AuthUser;
import com.flx.netty.chat.plugin.annotion.mybatis.DaoMapper;
import com.flx.netty.chat.plugin.plugins.mybatis.base.BaseDao;
import org.apache.ibatis.annotations.Select;

/**
 * @Author: Fenglixiong
 * @Date: 2020/7/24 17:25
 * @Description:
 */
@DaoMapper
public interface AuthUserDao extends BaseDao<AuthUser> {

    @Select("select count(1) from auth_user")
    int countWebUser();

}
