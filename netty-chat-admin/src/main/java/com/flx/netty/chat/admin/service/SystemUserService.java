package com.flx.netty.chat.admin.service;

import com.flx.netty.chat.admin.vo.SystemPermissionVO;
import com.flx.netty.chat.admin.vo.SystemUserVO;

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
public interface SystemUserService {
    /**
    * 新增
    */
    boolean add(SystemUserVO entityVO) throws Exception;
    
    /**
    * 删除
    */
    boolean delete(Long id) throws Exception;
    
    /**
    * 更新
    */
    boolean update(SystemUserVO entityVO) throws Exception;
    
    /**
    * 状态修改
    */
    boolean updateState(UpdateState entityVO) throws Exception;
    
    /**
    * 查询
    */
    SystemUserVO get(Long id) throws Exception;
    
    /**
    * 分页查询
    */
    PageVO<SystemUserVO> queryPage(PageQuery pageQuery) throws Exception;

    /**
    * 通过Map查询
    */
    List<SystemUserVO> query(Map<String, Object> query) throws Exception;

    /**
    * 查询所有
    */
    List<SystemUserVO> queryAll() throws Exception;

    /**
     * 根据用户查询用户
     */
    SystemUserVO getByUsername(String username) throws Exception;

    /**
     * 根据用户id获取权限
     */
    List<SystemPermissionVO> getPermissionById(Long id) throws Exception;
   
}
