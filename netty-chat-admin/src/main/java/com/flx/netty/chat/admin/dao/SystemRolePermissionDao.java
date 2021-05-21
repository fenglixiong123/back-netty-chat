package com.flx.netty.chat.admin.dao;

import com.flx.netty.chat.admin.common.annotation.DaoMapper;
import com.flx.netty.chat.admin.entity.SystemRolePermission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Set;


/**
 *  Mapper 接口
 *
 * @author Fenglixiong
 * @since 2021-05-21
 */
@DaoMapper
public interface SystemRolePermissionDao extends BaseMapper<SystemRolePermission> {

    /**
     * 通过角色id获取权限id
     * @param roleId 角色id
     * @param state 数据状态
     * @return 角色权限关系
     */
    @Select("select id,role_id,permission_id,state from web_role_permission where role_id = #{roleId} and state = #{state} ")
    List<SystemRolePermission> getByRoleId(@Param("roleId") Long roleId, @Param("state")String state);

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
    List<SystemRolePermission> getByRoleIds(@Param("roleIds") Set<Long> roleIds, @Param("state")String state)throws Exception;


}
