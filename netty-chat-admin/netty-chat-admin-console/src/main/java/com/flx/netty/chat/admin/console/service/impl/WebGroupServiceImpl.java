package com.flx.netty.chat.admin.console.service.impl;

import com.flx.netty.chat.admin.console.service.WebGroupService;
import com.flx.netty.chat.common.entity.UpdateState;
import com.flx.netty.chat.common.utils.page.PageQuery;
import com.flx.netty.chat.common.utils.page.PageVO;
import com.flx.netty.chat.group.api.service.IGroupService;
import com.flx.netty.chat.group.api.vo.WebGroupVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static com.flx.netty.chat.common.utils.result.ResponseUtils.getResult;

/**
 * @Author: Fenglixiong
 * @Date: 2020/7/24 16:34
 * @Description:
 */
@Slf4j
@Service
public class WebGroupServiceImpl implements WebGroupService {

    @Autowired
    private IGroupService groupService;

    @Override
    public Long add(WebGroupVO entityVO) throws Exception {
        return getResult(groupService.add(entityVO));
    }

    @Override
    public Integer delete(Long id) throws Exception {
        return getResult(groupService.delete(id));
    }

    @Override
    public Integer update(WebGroupVO entityVO) throws Exception {
        return getResult(groupService.update(entityVO));
    }

    @Override
    public boolean updateState(UpdateState entityVO) throws Exception {
        return getResult(groupService.updateState(entityVO));
    }

    @Override
    public WebGroupVO get(Long id) throws Exception {
        return getResult(groupService.get(id));
    }

    @Override
    public PageVO<WebGroupVO> queryPage(PageQuery pageQuery) throws Exception {
        return getResult(groupService.queryPage(pageQuery));
    }

    @Override
    public List<WebGroupVO> query(Map<String, Object> query) throws Exception {
        return getResult(groupService.query(query));
    }

    @Override
    public List<WebGroupVO> querySome(Map<String, Object> query,String[] columns) throws Exception {
        return getResult(groupService.querySome(query,columns));
    }

    @Override
    public List<WebGroupVO> queryAll(Map<String, Object> query) throws Exception {
        return getResult(groupService.queryAll(query));
    }

    
}
