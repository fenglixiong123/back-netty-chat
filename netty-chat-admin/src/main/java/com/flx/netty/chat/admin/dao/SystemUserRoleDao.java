package com.flx.netty.chat.admin.dao;

import com.flx.netty.chat.admin.common.annotation.DaoMapper;
import com.flx.netty.chat.admin.entity.SystemUserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 *  Mapper 接口
 *
 * @author Fenglixiong
 * @since 2021-05-21
 */
@DaoMapper
@Repository
public interface SystemUserRoleDao extends BaseMapper<SystemUserRole> {

    @Select("select `id`,`user_id`,`role_id`,`state` from system_user_role where user_id = #{userId} and state = #{state} ")
    List<SystemUserRole> getByUserId(@Param("userId") Long userId, @Param("state")String state)throws Exception;

}
