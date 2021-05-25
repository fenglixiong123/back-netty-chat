package com.flx.netty.chat.admin.dao;

import com.flx.netty.chat.admin.common.annotation.DaoMapper;
import com.flx.netty.chat.admin.entity.SystemRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;


/**
 *  Mapper 接口
 *
 * @author Fenglixiong
 * @since 2021-05-21
 */
@DaoMapper
@Repository
public interface SystemRoleDao extends BaseMapper<SystemRole> {

    @Select("<script>" +
            "select `id`,`code`,`name`,`order`,`state` " +
            "from web_role " +
            "where id in " +
            "<foreach item='id' index='index' collection='ids' open='(' separator=',' close=')'> #{id} </foreach> " +
            "and state = #{state}" +
            "</script>")
    List<SystemRole> getByIds(@Param("ids") Set<Long> ids, @Param("state")String state) throws Exception;

}
