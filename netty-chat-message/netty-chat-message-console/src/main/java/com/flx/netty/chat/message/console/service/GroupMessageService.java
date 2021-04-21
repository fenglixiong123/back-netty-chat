package com.flx.netty.chat.message.console.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.flx.netty.chat.common.entity.UpdateState;
import com.flx.netty.chat.common.utils.page.PageQuery;
import com.flx.netty.chat.message.api.vo.WebGroupMessageVO;

import java.util.List;
import java.util.Map;

/**
 * @Author: Fenglixiong
 * @Date: 2020/7/24 16:33
 * @Description:
 */
public interface GroupMessageService {

    /**
     * 新增
     */
    Long add(WebGroupMessageVO entity) throws Exception;

    /**
     * 删除
     */
    Integer delete(Long id) throws Exception;

    /**
     * 更新
     */
    Integer update(WebGroupMessageVO entity) throws Exception;

    /**
     * 状态修改
     */
    boolean updateState(UpdateState entityVO) throws Exception;

    /**
     * 查询
     */
    WebGroupMessageVO get(Long id) throws Exception;

    /**
     * 模糊分页查询
     */
    IPage<WebGroupMessageVO> queryPage(PageQuery query) throws Exception;

    /**
     * 通过Map模糊查询
     */
    List<WebGroupMessageVO> query(Map<String, Object> query) throws Exception;

    /**
     * 查询所有
     */
    List<WebGroupMessageVO> queryAll(Map<String, Object> query) throws Exception;

    /**
     * 查询指定字段
     */
    List<WebGroupMessageVO> querySome(Map<String, Object> query, String[] columns) throws Exception;

}
