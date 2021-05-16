package com.flx.netty.chat.auth.crud.dao;

import com.flx.netty.chat.auth.crud.entity.WebRole;
import com.flx.netty.chat.auth.crud.entity.WebRolePermission;
import com.flx.netty.chat.plugin.annotion.mybatis.DaoMapper;
import com.flx.netty.chat.plugin.plugins.mybatis.base.BaseDao;
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
public interface RoleDao extends BaseDao<WebRole> {

    @Select("<script>" +
            "select id,code,name,order,state " +
            "from web_role " +
            "where id in " +
            "<foreach item='id' index='index' collection='ids' open='(' separator=',' close=')'> #{id} </foreach> " +
            "and state = #{state}" +
            "</script>")
    List<WebRole> getByIds(@Param("ids") Set<Long> ids, @Param("state")String state) throws Exception;

}
