package com.flx.netty.chat.admin.service.impl;

import com.flx.netty.chat.admin.service.WebRoleService;
import com.flx.netty.chat.auth.api.client.WebRoleClient;
import com.flx.netty.chat.auth.api.vo.WebRoleVO;
import com.flx.netty.chat.common.entity.UpdateState;
import com.flx.netty.chat.common.utils.page.PageQuery;
import com.flx.netty.chat.common.utils.page.PageVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static com.flx.netty.chat.common.utils.result.ResultResponse.getResult;

/**
 *  服务实现类
 *
 * @author Fenglixiong
 * @since 2021-05-19
 */
@Slf4j
@Service
public class WebRoleServiceImpl implements WebRoleService {

    @Autowired
    private WebRoleClient roleClient;

    @Override
    public Long add(WebRoleVO entity) throws Exception {
        return getResult(roleClient.add(entity));
    }

    @Override
    public Integer delete(Long id) throws Exception {
        return getResult(roleClient.delete(id));
    }

    @Override
    public Integer update(WebRoleVO entity) throws Exception {
        return getResult(roleClient.update(entity));
    }

    @Override
    public boolean updateState(UpdateState entity) throws Exception {
        return getResult(roleClient.updateState(entity));
    }

    @Override
    public WebRoleVO get(Long id) throws Exception {
        return getResult(roleClient.get(id));
    }

    @Override
    public PageVO<WebRoleVO> queryPage(PageQuery pageQuery) throws Exception {
        return getResult(roleClient.queryPage(pageQuery));
    }

    @Override
    public List<WebRoleVO> query(Map<String, Object> query) throws Exception {
        return getResult(roleClient.query(query));
    }

    @Override
    public List<WebRoleVO> querySome(Map<String, Object> query, String[] columns) throws Exception {
        return getResult(roleClient.querySome(query,columns));
    }

    @Override
    public List<WebRoleVO> queryAll(Map<String, Object> query) throws Exception {
        return getResult(roleClient.queryAll(query));
    }

    @Override
    public List<WebRoleVO> getByUserId(Long userId) throws Exception {
        return null;
    }
}
