package com.flx.netty.chat.message.console.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.flx.netty.chat.plugin.plugins.mybatis.entity.StateVO;
import com.flx.netty.chat.plugin.plugins.mybatis.page.QueryAndPage;
import com.flx.netty.chat.message.api.vo.WebMessageVO;

import java.util.List;
import java.util.Map;

/**
 * @Author: Fenglixiong
 * @Date: 2020/7/24 16:33
 * @Description:
 */
public interface MessageService {

    /**
     * 新增
     */
    Long add(WebMessageVO entity) throws Exception;

    /**
     * 删除
     */
    Integer delete(Long id) throws Exception;

    /**
     * 更新
     */
    Integer update(WebMessageVO entity) throws Exception;

    /**
     * 状态修改
     */
    boolean updateState(StateVO stateVO) throws Exception;

    /**
     * 查询
     */
    WebMessageVO get(Long id) throws Exception;

    /**
     * 模糊分页查询
     */
    IPage<WebMessageVO> queryPage(QueryAndPage queryAndPage) throws Exception;

    /**
     * 通过Map模糊查询
     */
    List<WebMessageVO> query(Map<String, Object> query) throws Exception;

    /**
     * 查询所有
     */
    List<WebMessageVO> queryAll(Map<String, Object> query) throws Exception;

    /**
     * 查询指定字段
     */
    List<WebMessageVO> querySome(Map<String, Object> query, String[] columns) throws Exception;

}
