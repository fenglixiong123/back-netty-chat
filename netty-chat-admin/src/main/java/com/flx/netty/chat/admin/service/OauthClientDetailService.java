package com.flx.netty.chat.admin.service;

import com.flx.netty.chat.admin.entity.OauthClientDetail;
import com.flx.netty.chat.admin.vo.OauthClientDetailVO;

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
public interface OauthClientDetailService {
    /**
    * 新增
    */
    boolean add(OauthClientDetailVO entityVO) throws Exception;
    
    /**
    * 删除
    */
    boolean delete(Long id) throws Exception;
    
    /**
    * 更新
    */
    boolean update(OauthClientDetailVO entityVO) throws Exception;
    
    /**
    * 状态修改
    */
    boolean updateState(UpdateState entityVO) throws Exception;
    
    /**
    * 查询
    */
    OauthClientDetailVO get(Long id) throws Exception;
    
    /**
    * 分页查询
    */
    PageVO<OauthClientDetailVO> queryPage(PageQuery pageQuery) throws Exception;

    /**
    * 通过Map查询
    */
    List<OauthClientDetailVO> query(Map<String, Object> query) throws Exception;

    /**
    * 查询所有
    */
    List<OauthClientDetailVO> queryAll() throws Exception;

    /**
     * 根据clientId查询客户端详情
     */
    OauthClientDetail getByClientId(String clientId) throws Exception;
   
}
