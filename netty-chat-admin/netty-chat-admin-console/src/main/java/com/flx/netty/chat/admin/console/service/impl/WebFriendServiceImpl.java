package com.flx.netty.chat.admin.console.service.impl;

import com.flx.netty.chat.admin.console.service.WebFriendService;
import com.flx.netty.chat.common.entity.UpdateState;
import com.flx.netty.chat.common.utils.page.PageQuery;
import com.flx.netty.chat.common.utils.page.PageVO;
import com.flx.netty.chat.user.api.service.IFriendService;
import com.flx.netty.chat.user.api.vo.WebFriendVO;
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
public class WebFriendServiceImpl implements WebFriendService {

    @Autowired
    private IFriendService friendService;

    @Override
    public Long add(WebFriendVO entityVO) throws Exception {
        return getResult(friendService.add(entityVO));
    }

    @Override
    public Integer delete(Long id) throws Exception {
        return getResult(friendService.delete(id));
    }

    @Override
    public Integer update(WebFriendVO entityVO) throws Exception {
        return getResult(friendService.update(entityVO));
    }

    @Override
    public boolean updateState(UpdateState entityVO) throws Exception {
        return getResult(friendService.updateState(entityVO));
    }

    @Override
    public WebFriendVO get(Long id) throws Exception {
        return getResult(friendService.get(id));
    }

    @Override
    public PageVO<WebFriendVO> queryPage(PageQuery pageQuery) throws Exception {
        return getResult(friendService.queryPage(pageQuery));
    }

    @Override
    public List<WebFriendVO> query(Map<String, Object> query) throws Exception {
        return getResult(friendService.query(query));
    }

    @Override
    public List<WebFriendVO> querySome(Map<String, Object> query,String[] columns) throws Exception {
        return getResult(friendService.querySome(query,columns));
    }

    @Override
    public List<WebFriendVO> queryAll(Map<String, Object> query) throws Exception {
        return getResult(friendService.queryAll(query));
    }

    
}
