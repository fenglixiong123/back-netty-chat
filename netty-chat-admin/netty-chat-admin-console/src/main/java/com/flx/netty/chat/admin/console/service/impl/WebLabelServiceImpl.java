package com.flx.netty.chat.admin.console.service.impl;

import com.flx.netty.chat.admin.console.service.WebLabelService;
import com.flx.netty.chat.common.entity.UpdateState;
import com.flx.netty.chat.common.utils.page.PageQuery;
import com.flx.netty.chat.common.utils.page.PageVO;
import com.flx.netty.chat.user.api.service.ILabelService;
import com.flx.netty.chat.user.api.vo.WebLabelVO;
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
public class WebLabelServiceImpl implements WebLabelService {

    @Autowired
    private ILabelService labelService;

    @Override
    public Long add(WebLabelVO entityVO) throws Exception {
        return getResult(labelService.add(entityVO));
    }

    @Override
    public Integer delete(Long id) throws Exception {
        return getResult(labelService.delete(id));
    }

    @Override
    public Integer update(WebLabelVO entityVO) throws Exception {
        return getResult(labelService.update(entityVO));
    }

    @Override
    public boolean updateState(UpdateState entityVO) throws Exception {
        return getResult(labelService.updateState(entityVO));
    }

    @Override
    public WebLabelVO get(Long id) throws Exception {
        return getResult(labelService.get(id));
    }

    @Override
    public PageVO<WebLabelVO> queryPage(PageQuery pageQuery) throws Exception {
        return getResult(labelService.queryPage(pageQuery));
    }

    @Override
    public List<WebLabelVO> query(Map<String, Object> query) throws Exception {
        return getResult(labelService.query(query));
    }

    @Override
    public List<WebLabelVO> querySome(Map<String, Object> query,String[] columns) throws Exception {
        return getResult(labelService.querySome(query,columns));
    }

    @Override
    public List<WebLabelVO> queryAll(Map<String, Object> query) throws Exception {
        return getResult(labelService.queryAll(query));
    }

    
}
