package com.flx.netty.chat.auth.crud.dao;

import com.flx.netty.chat.auth.crud.entity.WebUserRole;
import com.flx.netty.chat.plugin.annotion.mybatis.DaoMapper;
import com.flx.netty.chat.plugin.plugins.mybatis.base.BaseDao;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


/**
 *  Dao层操作类
 *
 * @author Fenglixiong
 * @since 2021-05-16
 */

@DaoMapper
public interface UserRoleDao extends BaseDao<WebUserRole> {

    @Select("select id,user_id,role_id,state from web_user_role where user_id = #{userId} and state = #{state} ")
    List<WebUserRole> getByUserId(@Param("userId") Long userId,@Param("state")String state)throws Exception;

}
