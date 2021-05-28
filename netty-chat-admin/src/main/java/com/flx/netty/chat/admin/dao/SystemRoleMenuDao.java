package com.flx.netty.chat.admin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.flx.netty.chat.admin.common.annotation.DaoMapper;
import com.flx.netty.chat.admin.entity.SystemRoleMenu;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;


/**
 * 角色菜单关系 Mapper 接口
 *
 * @author Fenglixiong
 * @since 2021-05-28
 */
@DaoMapper
@Repository
public interface SystemRoleMenuDao extends BaseMapper<SystemRoleMenu> {

    /**
     * 通过角色id获取菜单id
     * @param roleId 角色id
     * @param state 数据状态
     * @return 角色菜单关系
     */
    @Select("select id,role_id,menu_id,state from system_role_menu where role_id = #{roleId} and state = #{state} ")
    List<SystemRoleMenu> getByRoleId(@Param("roleId") Long roleId, @Param("state")String state);

    /**
     * 通过角色id获取权限id
     * @param roleIds 角色id集合
     * @param state 数据状态
     * @return 角色菜单关系
     */
    @Select("<script>" +
            "select `id`,`role_id`,`menu_id`,`state` " +
            "from system_role_menu " +
            "where role_id in " +
            "<foreach item='roleId' index='index' collection='roleIds' open='(' separator=',' close=')'> #{roleId} </foreach> " +
            "and state = #{state} " +
            "</script>")
    List<SystemRoleMenu> getByRoleIds(@Param("roleIds") Set<Long> roleIds, @Param("state")String state)throws Exception;


}
