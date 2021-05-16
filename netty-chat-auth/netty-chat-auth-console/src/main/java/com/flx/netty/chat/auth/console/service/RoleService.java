package com.flx.netty.chat.auth.console.service;

import com.flx.netty.chat.auth.api.vo.RoleVO;

import com.flx.netty.chat.common.entity.UpdateState;
import com.flx.netty.chat.common.utils.page.PageQuery;
import com.flx.netty.chat.common.utils.page.PageVO;

import java.util.List;
import java.util.Map;

/**
 *  Service服务类
 *
 * @author Fenglixiong
 * @since 2021-05-16
 */
public interface RoleService {

    /**
    * 新增
    */
    Long add(RoleVO entity) throws Exception;
    
    /**
    * 删除
    */
    Integer delete(Long id) throws Exception;
    
    /**
    * 更新
    */
    Integer update(RoleVO entity) throws Exception;
    
    /**
    * 状态修改
    */
    boolean updateState(UpdateState entity) throws Exception;
    
    /**
    * 查询
    */
    RoleVO get(Long id) throws Exception;
    
    /**
    * 模糊分页查询
    */
    PageVO<RoleVO> queryPage(PageQuery pageQuery) throws Exception;
    
    /**
    * 通过Map模糊查询
    */
    List<RoleVO> query(Map<String, Object> query) throws Exception;
    
    /**
    * 查询指定字段
    */
    List<RoleVO> querySome(Map<String, Object> query,String[] columns) throws Exception;
  
    /**
    * 查询所有
    */
    List<RoleVO> queryAll(Map<String, Object> query) throws Exception;


}
