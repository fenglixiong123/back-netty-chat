package com.flx.netty.chat.admin.service;

import com.flx.netty.chat.admin.entity.AuthClient;
import com.flx.netty.chat.admin.vo.AuthClientVO;

import com.flx.netty.chat.common.entity.UpdateState;
import com.flx.netty.chat.common.utils.page.PageQuery;
import com.flx.netty.chat.common.utils.page.PageVO;

import java.util.List;
import java.util.Map;

/**
 * 客户端信息表 服务类
 *
 * @author Fenglixiong
 * @since 2021-05-19
 */
public interface AuthClientService {
    /**
    * 新增
    */
    boolean add(AuthClientVO entityVO) throws Exception;
    
    /**
    * 删除
    */
    boolean delete(Long id) throws Exception;
    
    /**
    * 更新
    */
    boolean update(AuthClientVO entityVO) throws Exception;
    
    /**
    * 状态修改
    */
    boolean updateState(UpdateState entityVO) throws Exception;
    
    /**
    * 查询
    */
    AuthClientVO get(Long id) throws Exception;
    
    /**
    * 分页查询
    */
    PageVO<AuthClientVO> queryPage(PageQuery pageQuery) throws Exception;

    /**
    * 通过Map查询
    */
    List<AuthClientVO> query(Map<String, Object> query) throws Exception;

    /**
    * 查询所有
    */
    List<AuthClientVO> queryAll() throws Exception;

    /**
     * 根据clientId查询客户端详情
     */
    AuthClient getByClientId(String clientId) throws Exception;
   
}
