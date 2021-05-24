package com.flx.netty.chat.admin.service;

import com.flx.netty.chat.auth.api.vo.AuthPermissionVO;
import com.flx.netty.chat.common.entity.UpdateState;
import com.flx.netty.chat.common.utils.page.PageQuery;
import com.flx.netty.chat.common.utils.page.PageVO;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *  服务类
 *
 * @author Fenglixiong
 * @since 2021-05-19
 */
public interface AuthPermissionService {

    /**
     * 新增
     */
    Long add(AuthPermissionVO entity) throws Exception;

    /**
     * 删除
     */
    Integer delete(Long id) throws Exception;

    /**
     * 更新
     */
    Integer update(AuthPermissionVO entity) throws Exception;

    /**
     * 状态修改
     */
    boolean updateState(UpdateState entity) throws Exception;

    /**
     * 查询
     */
    AuthPermissionVO get(Long id) throws Exception;

    /**
     * 模糊分页查询
     */
    PageVO<AuthPermissionVO> queryPage(PageQuery pageQuery) throws Exception;

    /**
     * 通过Map模糊查询
     */
    List<AuthPermissionVO> query(Map<String, Object> query) throws Exception;

    /**
     * 查询指定字段
     */
    List<AuthPermissionVO> querySome(Map<String, Object> query, String[] columns) throws Exception;

    /**
     * 查询所有
     */
    List<AuthPermissionVO> queryAll(Map<String, Object> query) throws Exception;

    /**
     * 根据角色获取权限
     * @param roleId 角色id
     */
    List<AuthPermissionVO> getByRoleId(Long roleId)throws Exception;

    /**
     * 根据角色集合获取权限
     * @param roleIds 角色id集合
     */
    List<AuthPermissionVO> getByRoleIds(Set<Long> roleIds)throws Exception;
   
}
