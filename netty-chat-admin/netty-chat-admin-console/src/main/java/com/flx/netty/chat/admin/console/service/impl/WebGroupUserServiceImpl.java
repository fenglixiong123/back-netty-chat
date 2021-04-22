package com.flx.netty.chat.admin.console.service.impl;

import com.flx.netty.chat.admin.console.service.WebGroupUserService;
import com.flx.netty.chat.common.entity.UpdateState;
import com.flx.netty.chat.common.utils.page.PageQuery;
import com.flx.netty.chat.common.utils.page.PageVO;
import com.flx.netty.chat.group.api.service.IGroupUserService;
import com.flx.netty.chat.group.api.vo.WebGroupUserVO;
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
public class WebGroupUserServiceImpl implements WebGroupUserService {

    @Autowired
    private IGroupUserService groupUserService;

    @Override
    public Long add(WebGroupUserVO entityVO) throws Exception {
        return getResult(groupUserService.add(entityVO));
    }

    @Override
    public Integer delete(Long id) throws Exception {
        return getResult(groupUserService.delete(id));
    }

    @Override
    public Integer update(WebGroupUserVO entityVO) throws Exception {
        return getResult(groupUserService.update(entityVO));
    }

    @Override
    public boolean updateState(UpdateState entityVO) throws Exception {
        return getResult(groupUserService.updateState(entityVO));
    }

    @Override
    public WebGroupUserVO get(Long id) throws Exception {
        return getResult(groupUserService.get(id));
    }

    @Override
    public PageVO<WebGroupUserVO> queryPage(PageQuery pageQuery) throws Exception {
        return getResult(groupUserService.queryPage(pageQuery));
    }

    @Override
    public List<WebGroupUserVO> query(Map<String, Object> query) throws Exception {
        return getResult(groupUserService.query(query));
    }

    @Override
    public List<WebGroupUserVO> querySome(Map<String, Object> query,String[] columns) throws Exception {
        return getResult(groupUserService.querySome(query,columns));
    }

    @Override
    public List<WebGroupUserVO> queryAll(Map<String, Object> query) throws Exception {
        return getResult(groupUserService.queryAll(query));
    }

    
}
