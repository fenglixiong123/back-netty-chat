package com.flx.netty.chat.auth.console.service;

import com.flx.netty.chat.auth.api.vo.WebPermissionVO;

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
public interface PermissionService {

    /**
    * 新增
    */
    Long add(WebPermissionVO entity) throws Exception;
    
    /**
    * 删除
    */
    Integer delete(Long id) throws Exception;
    
    /**
    * 更新
    */
    Integer update(WebPermissionVO entity) throws Exception;
    
    /**
    * 状态修改
    */
    boolean updateState(UpdateState entity) throws Exception;
    
    /**
    * 查询
    */
    WebPermissionVO get(Long id) throws Exception;
    
    /**
    * 模糊分页查询
    */
    PageVO<WebPermissionVO> queryPage(PageQuery pageQuery) throws Exception;
    
    /**
    * 通过Map模糊查询
    */
    List<WebPermissionVO> query(Map<String, Object> query) throws Exception;
    
    /**
    * 查询指定字段
    */
    List<WebPermissionVO> querySome(Map<String, Object> query, String[] columns) throws Exception;
  
    /**
    * 查询所有
    */
    List<WebPermissionVO> queryAll(Map<String, Object> query) throws Exception;


}
