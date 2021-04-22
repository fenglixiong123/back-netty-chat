package com.flx.netty.chat.admin.console.service.impl;

import com.flx.netty.chat.admin.console.service.WebGroupMessageService;
import com.flx.netty.chat.common.entity.UpdateState;
import com.flx.netty.chat.common.utils.page.PageQuery;
import com.flx.netty.chat.common.utils.page.PageVO;
import com.flx.netty.chat.message.api.service.IGroupMessageService;
import com.flx.netty.chat.message.api.vo.WebGroupMessageVO;
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
public class WebGroupMessageServiceImpl implements WebGroupMessageService {

    @Autowired
    private IGroupMessageService groupMessageService;

    @Override
    public Long add(WebGroupMessageVO entityVO) throws Exception {
        return getResult(groupMessageService.add(entityVO));
    }

    @Override
    public Integer delete(Long id) throws Exception {
        return getResult(groupMessageService.delete(id));
    }

    @Override
    public Integer update(WebGroupMessageVO entityVO) throws Exception {
        return getResult(groupMessageService.update(entityVO));
    }

    @Override
    public boolean updateState(UpdateState entityVO) throws Exception {
        return getResult(groupMessageService.updateState(entityVO));
    }

    @Override
    public WebGroupMessageVO get(Long id) throws Exception {
        return getResult(groupMessageService.get(id));
    }

    @Override
    public PageVO<WebGroupMessageVO> queryPage(PageQuery pageQuery) throws Exception {
        return getResult(groupMessageService.queryPage(pageQuery));
    }

    @Override
    public List<WebGroupMessageVO> query(Map<String, Object> query) throws Exception {
        return getResult(groupMessageService.query(query));
    }

    @Override
    public List<WebGroupMessageVO> querySome(Map<String, Object> query,String[] columns) throws Exception {
        return getResult(groupMessageService.querySome(query,columns));
    }

    @Override
    public List<WebGroupMessageVO> queryAll(Map<String, Object> query) throws Exception {
        return getResult(groupMessageService.queryAll(query));
    }

    
}
