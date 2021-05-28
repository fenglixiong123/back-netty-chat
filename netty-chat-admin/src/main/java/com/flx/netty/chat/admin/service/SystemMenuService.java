package com.flx.netty.chat.admin.service;

import com.flx.netty.chat.admin.vo.SystemMenuVO;
import com.flx.netty.chat.common.entity.UpdateState;
import com.flx.netty.chat.common.utils.page.PageQuery;
import com.flx.netty.chat.common.utils.page.PageVO;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 菜单信息 服务类
 *
 * @author Fenglixiong
 * @since 2021-05-28
 */
public interface SystemMenuService {
    /**
    * 新增
    */
    boolean add(SystemMenuVO entityVO) throws Exception;
    
    /**
    * 删除
    */
    boolean delete(Long id) throws Exception;
    
    /**
    * 更新
    */
    boolean update(SystemMenuVO entityVO) throws Exception;
    
    /**
    * 状态修改
    */
    boolean updateState(UpdateState entityVO) throws Exception;
    
    /**
    * 查询
    */
    SystemMenuVO get(Long id) throws Exception;
    
    /**
    * 分页查询
    */
    PageVO<SystemMenuVO> queryPage(PageQuery pageQuery) throws Exception;

    /**
    * 通过Map查询
    */
    List<SystemMenuVO> query(Map<String, Object> query) throws Exception;

    /**
    * 查询所有
    */
    List<SystemMenuVO> queryAll() throws Exception;

    /**
     * 根据角色id查询
     */
    List<SystemMenuVO> getByIds(Set<Long> ids)throws Exception;

}
