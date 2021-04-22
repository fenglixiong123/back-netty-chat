package com.flx.netty.chat.user.console.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.flx.netty.chat.common.utils.page.PageQuery;
import com.flx.netty.chat.common.entity.UpdateState;
import com.flx.netty.chat.common.utils.page.PageVO;
import com.flx.netty.chat.user.api.vo.WebLabelVO;

import java.util.List;
import java.util.Map;

/**
 * @Author: Fenglixiong
 * @Date: 2020/7/24 16:33
 * @Description:
 */
public interface LabelService {

    /**
     * 新增
     */
    Long add(WebLabelVO entity) throws Exception;

    /**
     * 删除
     */
    Integer delete(Long id) throws Exception;

    /**
     * 更新
     */
    Integer update(WebLabelVO entity) throws Exception;

    /**
     * 状态修改
     */
    boolean updateState(UpdateState entity) throws Exception;

    /**
     * 查询
     */
    WebLabelVO get(Long id) throws Exception;

    /**
     * 模糊分页查询
     */
    PageVO<WebLabelVO> queryPage(PageQuery pageQuery) throws Exception;

    /**
     * 通过Map模糊查询
     */
    List<WebLabelVO> query(Map<String, Object> query) throws Exception;

    /**
     * 查询指定字段
     */
    List<WebLabelVO> querySome(Map<String, Object> query, String[] columns) throws Exception;

    /**
     * 查询所有
     */
    List<WebLabelVO> queryAll(Map<String, Object> query) throws Exception;



}
