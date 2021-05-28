package com.flx.netty.chat.admin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.flx.netty.chat.admin.common.annotation.DaoMapper;
import com.flx.netty.chat.admin.entity.SystemMenu;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;


/**
 * 菜单信息 Mapper 接口
 *
 * @author Fenglixiong
 * @since 2021-05-28
 */
@DaoMapper
@Repository
public interface SystemMenuDao extends BaseMapper<SystemMenu> {

    @Select("<script>" +
            "select `id`,`pid`,`code`,`name`,`path`,`icon`,`order`,`state` " +
            "from system_menu " +
            "where id in " +
            "<foreach item='id' index='index' collection='ids' open='(' separator=',' close=')'>#{id}</foreach> " +
            "and state = #{state} " +
            "</script>")
    List<SystemMenu> getByIds(@Param("ids") Set<Long> ids, @Param("state")String state)throws Exception;


}
