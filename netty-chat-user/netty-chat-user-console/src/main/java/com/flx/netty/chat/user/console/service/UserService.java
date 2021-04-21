package com.flx.netty.chat.user.console.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.flx.netty.chat.common.utils.page.PageQuery;
import com.flx.netty.chat.common.entity.UpdateState;
import com.flx.netty.chat.user.api.vo.WebUserVO;

import java.util.List;
import java.util.Map;

/**
 * @Author: Fenglixiong
 * @Date: 2020/7/24 16:33
 * @Description:
 */
public interface UserService {

    /**
     * 新增
     */
    Long add(WebUserVO entity) throws Exception;

    /**
     * 删除
     */
    Integer delete(Long id) throws Exception;

    /**
     * 更新
     */
    Integer update(WebUserVO entity) throws Exception;

    /**
     * 状态修改
     */
    boolean updateState(UpdateState entity) throws Exception;

    /**
     * 查询
     */
    WebUserVO get(Long id) throws Exception;

    /**
     * 模糊分页查询
     */
    IPage<WebUserVO> queryPage(PageQuery pageQuery) throws Exception;

    /**
     * 通过Map模糊查询
     */
    List<WebUserVO> query(Map<String, Object> query) throws Exception;

    /**
     * 查询所有
     */
    List<WebUserVO> queryAll(Map<String, Object> query) throws Exception;

    /**
     * 查询指定字段
     */
    List<WebUserVO> querySome(Map<String, Object> query,String[] columns) throws Exception;

}
