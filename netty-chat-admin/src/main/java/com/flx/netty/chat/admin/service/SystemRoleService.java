package com.flx.netty.chat.admin.service;

import com.flx.netty.chat.admin.vo.SystemRoleVO;

import com.flx.netty.chat.common.entity.UpdateState;
import com.flx.netty.chat.common.utils.page.PageQuery;
import com.flx.netty.chat.common.utils.page.PageVO;

import java.util.List;
import java.util.Map;

/**
 *  服务类
 *
 * @author Fenglixiong
 * @since 2021-05-21
 */
public interface SystemRoleService {
    /**
    * 新增
    */
    boolean add(SystemRoleVO entityVO) throws Exception;
    
    /**
    * 删除
    */
    boolean delete(Long id) throws Exception;
    
    /**
    * 更新
    */
    boolean update(SystemRoleVO entityVO) throws Exception;
    
    /**
    * 状态修改
    */
    boolean updateState(UpdateState entityVO) throws Exception;
    
    /**
    * 查询
    */
    SystemRoleVO get(Long id) throws Exception;
    
    /**
    * 分页查询
    */
    PageVO<SystemRoleVO> queryPage(PageQuery pageQuery) throws Exception;

    /**
    * 通过Map查询
    */
    List<SystemRoleVO> query(Map<String, Object> query) throws Exception;

    /**
    * 查询所有
    */
    List<SystemRoleVO> queryAll() throws Exception;
   
}
