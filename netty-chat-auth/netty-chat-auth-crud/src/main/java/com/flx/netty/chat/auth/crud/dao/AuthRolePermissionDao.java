package com.flx.netty.chat.auth.crud.dao;

import com.flx.netty.chat.auth.crud.entity.AuthRolePermission;
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
public interface AuthRolePermissionDao extends BaseDao<AuthRolePermission> {

    /**
     * 通过角色id获取权限id
     * @param roleId 角色id
     * @param state 数据状态
     * @return 角色权限关系
     */
    @Select("select id,role_id,permission_id,state from web_role_permission where role_id = #{roleId} and state = #{state} ")
    List<AuthRolePermission> getByRoleId(@Param("roleId") Long roleId, @Param("state")String state);

    /**
     * 通过角色id获取权限id
     * @param roleIds 角色id集合
     * @param state 数据状态
     * @return 角色权限关系
     */
    @Select("<script>" +
            "select `id`,`role_id`,`permission_id`,`state` " +
            "from web_role_permission " +
            "where role_id in " +
            "<foreach item='roleId' index='index' collection='roleIds' open='(' separator=',' close=')'> #{roleId} </foreach> " +
            "and state = #{state} " +
            "</script>")
    List<AuthRolePermission> getByRoleIds(@Param("roleIds") Set<Long> roleIds, @Param("state")String state)throws Exception;

}
