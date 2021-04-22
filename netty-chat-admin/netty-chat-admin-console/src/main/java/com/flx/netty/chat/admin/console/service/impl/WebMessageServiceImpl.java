package com.flx.netty.chat.admin.console.service.impl;

import com.flx.netty.chat.admin.console.service.WebMessageService;
import com.flx.netty.chat.common.entity.UpdateState;
import com.flx.netty.chat.common.utils.page.PageQuery;
import com.flx.netty.chat.common.utils.page.PageVO;
import com.flx.netty.chat.message.api.service.IMessageService;
import com.flx.netty.chat.message.api.vo.WebMessageVO;
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
public class WebMessageServiceImpl implements WebMessageService {

    @Autowired
    private IMessageService messageService;

    @Override
    public Long add(WebMessageVO entityVO) throws Exception {
        return getResult(messageService.add(entityVO));
    }

    @Override
    public Integer delete(Long id) throws Exception {
        return getResult(messageService.delete(id));
    }

    @Override
    public Integer update(WebMessageVO entityVO) throws Exception {
        return getResult(messageService.update(entityVO));
    }

    @Override
    public boolean updateState(UpdateState entityVO) throws Exception {
        return getResult(messageService.updateState(entityVO));
    }

    @Override
    public WebMessageVO get(Long id) throws Exception {
        return getResult(messageService.get(id));
    }

    @Override
    public PageVO<WebMessageVO> queryPage(PageQuery pageQuery) throws Exception {
        return getResult(messageService.queryPage(pageQuery));
    }

    @Override
    public List<WebMessageVO> query(Map<String, Object> query) throws Exception {
        return getResult(messageService.query(query));
    }

    @Override
    public List<WebMessageVO> querySome(Map<String, Object> query,String[] columns) throws Exception {
        return getResult(messageService.querySome(query,columns));
    }

    @Override
    public List<WebMessageVO> queryAll(Map<String, Object> query) throws Exception {
        return getResult(messageService.queryAll(query));
    }


    
}
