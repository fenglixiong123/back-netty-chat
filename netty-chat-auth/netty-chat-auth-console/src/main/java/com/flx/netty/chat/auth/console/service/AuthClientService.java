package com.flx.netty.chat.auth.console.service;

import com.flx.netty.chat.auth.api.vo.AuthClientVO;

import com.flx.netty.chat.common.entity.UpdateState;
import com.flx.netty.chat.common.utils.page.PageQuery;
import com.flx.netty.chat.common.utils.page.PageVO;

import java.util.List;
import java.util.Map;

/**
 * 客户端信息表 Service服务类
 *
 * @author Fenglixiong
 * @since 2021-05-07
 */
public interface AuthClientService {

    /**
    * 新增
    */
    Long add(AuthClientVO entity) throws Exception;
    
    /**
    * 删除
    */
    Integer delete(Long id) throws Exception;
    
    /**
    * 更新
    */
    Integer update(AuthClientVO entity) throws Exception;
    
    /**
    * 状态修改
    */
    boolean updateState(UpdateState entity) throws Exception;
    
    /**
    * 查询
    */
    AuthClientVO get(Long id) throws Exception;
    
    /**
    * 模糊分页查询
    */
    PageVO<AuthClientVO> queryPage(PageQuery pageQuery) throws Exception;
    
    /**
    * 通过Map模糊查询
    */
    List<AuthClientVO> query(Map<String, Object> query) throws Exception;
    
    /**
    * 查询指定字段
    */
    List<AuthClientVO> querySome(Map<String, Object> query, String[] columns) throws Exception;
  
    /**
    * 查询所有
    */
    List<AuthClientVO> queryAll(Map<String, Object> query) throws Exception;


}
