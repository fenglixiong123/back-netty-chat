package com.flx.netty.chat.admin.service;

import com.flx.netty.chat.admin.vo.SystemPermissionVO;

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
public interface SystemPermissionService {
    /**
    * 新增
    */
    boolean add(SystemPermissionVO entityVO) throws Exception;
    
    /**
    * 删除
    */
    boolean delete(Long id) throws Exception;
    
    /**
    * 更新
    */
    boolean update(SystemPermissionVO entityVO) throws Exception;
    
    /**
    * 状态修改
    */
    boolean updateState(UpdateState entityVO) throws Exception;
    
    /**
    * 查询
    */
    SystemPermissionVO get(Long id) throws Exception;
    
    /**
    * 分页查询
    */
    PageVO<SystemPermissionVO> queryPage(PageQuery pageQuery) throws Exception;

    /**
    * 通过Map查询
    */
    List<SystemPermissionVO> query(Map<String, Object> query) throws Exception;

    /**
    * 查询所有
    */
    List<SystemPermissionVO> queryAll() throws Exception;
   
}
