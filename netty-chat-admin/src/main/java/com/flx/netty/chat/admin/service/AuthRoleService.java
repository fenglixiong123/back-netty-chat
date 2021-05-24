package com.flx.netty.chat.admin.service;

import com.flx.netty.chat.auth.api.vo.AuthRoleVO;
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
public interface AuthRoleService {

    /**
     * 新增
     */
    Long add(AuthRoleVO entity) throws Exception;

    /**
     * 删除
     */
    Integer delete(Long id) throws Exception;

    /**
     * 更新
     */
    Integer update(AuthRoleVO entity) throws Exception;

    /**
     * 状态修改
     */
    boolean updateState(UpdateState entity) throws Exception;

    /**
     * 查询
     */
    AuthRoleVO get(Long id) throws Exception;

    /**
     * 模糊分页查询
     */
    PageVO<AuthRoleVO> queryPage(PageQuery pageQuery) throws Exception;

    /**
     * 通过Map模糊查询
     */
    List<AuthRoleVO> query(Map<String, Object> query) throws Exception;

    /**
     * 查询指定字段
     */
    List<AuthRoleVO> querySome(Map<String, Object> query, String[] columns) throws Exception;

    /**
     * 查询所有
     */
    List<AuthRoleVO> queryAll(Map<String, Object> query) throws Exception;

    /**
     * 通过用户id获取角色集合
     * @param userId 用户id
     */
    List<AuthRoleVO> getByUserId(Long userId)throws Exception;
   
}
