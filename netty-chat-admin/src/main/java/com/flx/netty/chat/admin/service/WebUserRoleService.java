package com.flx.netty.chat.admin.service;

import com.flx.netty.chat.admin.vo.WebUserRoleVO;

import com.flx.netty.chat.common.entity.UpdateState;
import com.flx.netty.chat.common.utils.page.PageQuery;
import com.flx.netty.chat.common.utils.page.PageVO;

import java.util.List;
import java.util.Map;

/**
 *  服务类
 *
 * @author Fenglixiong
 * @since 2021-05-19
 */
public interface WebUserRoleService {
    /**
    * 新增
    */
    boolean add(WebUserRoleVO entityVO) throws Exception;
    
    /**
    * 删除
    */
    boolean delete(Long id) throws Exception;
    
    /**
    * 更新
    */
    boolean update(WebUserRoleVO entityVO) throws Exception;
    
    /**
    * 状态修改
    */
    boolean updateState(UpdateState entityVO) throws Exception;
    
    /**
    * 查询
    */
    WebUserRoleVO get(Long id) throws Exception;
    
    /**
    * 分页查询
    */
    PageVO<WebUserRoleVO> queryPage(PageQuery pageQuery) throws Exception;

    /**
    * 通过Map查询
    */
    List<WebUserRoleVO> query(Map<String, Object> query) throws Exception;

    /**
    * 查询所有
    */
    List<WebUserRoleVO> queryAll() throws Exception;
   
}
