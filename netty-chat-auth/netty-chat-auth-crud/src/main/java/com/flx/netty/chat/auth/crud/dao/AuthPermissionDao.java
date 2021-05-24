package com.flx.netty.chat.auth.crud.dao;

import com.flx.netty.chat.auth.crud.entity.AuthPermission;
import com.flx.netty.chat.plugin.plugins.mybatis.base.BaseDao;

import com.flx.netty.chat.plugin.annotion.mybatis.DaoMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Set;


/**
 *  Dao层操作类
 *
 * @author Fenglixiong
 * @since 2021-05-16
 */

@DaoMapper
public interface AuthPermissionDao extends BaseDao<AuthPermission> {

    @Select("<script>" +
            "select `id`,`pid`,`code`,`name`,`path`,`method`,`icon`,`order`,`state` " +
            "from web_permission " +
            "where id in " +
            "<foreach item='id' index='index' collection='ids' open='(' separator=',' close=')'>#{id}</foreach> " +
            "and state = #{state} " +
            "</script>")
    List<AuthPermission> getByIds(@Param("ids") Set<Long> ids, @Param("state")String state)throws Exception;

}
