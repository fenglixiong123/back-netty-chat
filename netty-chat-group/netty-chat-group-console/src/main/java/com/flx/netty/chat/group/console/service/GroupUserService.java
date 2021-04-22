package com.flx.netty.chat.group.console.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.flx.netty.chat.common.entity.UpdateState;
import com.flx.netty.chat.common.utils.page.PageQuery;
import com.flx.netty.chat.common.utils.page.PageVO;
import com.flx.netty.chat.group.api.vo.WebGroupUserVO;

import java.util.List;
import java.util.Map;

/**
 * @Author: Fenglixiong
 * @Date: 2020/7/24 16:33
 * @Description:
 */
public interface GroupUserService {

    /**
     * 新增
     */
    Long add(WebGroupUserVO entityVO) throws Exception;

    /**
     * 删除
     */
    Integer delete(Long id) throws Exception;

    /**
     * 更新
     */
    Integer update(WebGroupUserVO entityVO) throws Exception;

    /**
     * 状态修改
     */
    boolean updateState(UpdateState entityVO) throws Exception;

    /**
     * 查询
     */
    WebGroupUserVO get(Long id) throws Exception;

    /**
     * 模糊分页查询
     */
    PageVO<WebGroupUserVO> queryPage(PageQuery query) throws Exception;

    /**
     * 通过Map模糊查询
     */
    List<WebGroupUserVO> query(Map<String, Object> query) throws Exception;

    /**
     * 查询指定字段
     */
    List<WebGroupUserVO> querySome(Map<String, Object> query, String[] columns) throws Exception;

    /**
     * 查询所有
     */
    List<WebGroupUserVO> queryAll(Map<String, Object> query) throws Exception;


}
