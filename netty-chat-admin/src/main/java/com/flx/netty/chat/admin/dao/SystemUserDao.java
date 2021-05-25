package com.flx.netty.chat.admin.dao;

import com.flx.netty.chat.admin.common.annotation.DaoMapper;
import com.flx.netty.chat.admin.entity.SystemUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;


/**
 *  Mapper 接口
 *
 * @author Fenglixiong
 * @since 2021-05-21
 */
@DaoMapper
@Repository
public interface SystemUserDao extends BaseMapper<SystemUser> {

    @Select("select * from system_user where user_name = #{username}")
    SystemUser getByUsername(@Param("username")String username);



}
