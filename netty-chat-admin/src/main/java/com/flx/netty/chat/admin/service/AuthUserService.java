package com.flx.netty.chat.admin.service;

import com.flx.netty.chat.auth.api.vo.AuthPermissionVO;
import com.flx.netty.chat.auth.api.vo.AuthUserVO;
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
public interface AuthUserService {

    /**
     * 新增
     */
    Long add(AuthUserVO entity) throws Exception;

    /**
     * 删除
     */
    Integer delete(Long id) throws Exception;

    /**
     * 更新
     */
    Integer update(AuthUserVO entity) throws Exception;

    /**
     * 状态修改
     */
    boolean updateState(UpdateState entity) throws Exception;

    /**
     * 查询
     */
    AuthUserVO get(Long id) throws Exception;

    /**
     * 模糊分页查询
     */
    PageVO<AuthUserVO> queryPage(PageQuery pageQuery) throws Exception;

    /**
     * 通过Map模糊查询
     */
    List<AuthUserVO> query(Map<String, Object> query) throws Exception;

    /**
     * 查询指定字段
     */
    List<AuthUserVO> querySome(Map<String, Object> query, String[] columns) throws Exception;

    /**
     * 查询所有
     */
    List<AuthUserVO> queryAll(Map<String, Object> query) throws Exception;

    /**
     * 根据用户名查询
     * @param username
     * @return
     */
    AuthUserVO getByUsername(String username) throws Exception;

    /**
     * 用户是否存在
     */
    boolean isExist(String username) throws Exception;

    /**
     * 验证密码是否正确
     */
    AuthUserVO validateUser(String username, String password) throws Exception;

    /**
     * 通过用户id获取用户权限
     * @param id
     * @return
     */
    List<AuthPermissionVO> getPermissionById(Long id) throws Exception;
   
}
