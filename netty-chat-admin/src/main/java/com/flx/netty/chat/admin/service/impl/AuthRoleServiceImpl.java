package com.flx.netty.chat.admin.service.impl;

import com.flx.netty.chat.admin.service.AuthRoleService;
import com.flx.netty.chat.auth.api.client.AuthRoleClient;
import com.flx.netty.chat.auth.api.vo.AuthRoleVO;
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
public class AuthRoleServiceImpl implements AuthRoleService {

    @Autowired
    private AuthRoleClient roleClient;

    @Override
    public Long add(AuthRoleVO entity) throws Exception {
        return getResult(roleClient.add(entity));
    }

    @Override
    public Integer delete(Long id) throws Exception {
        return getResult(roleClient.delete(id));
    }

    @Override
    public Integer update(AuthRoleVO entity) throws Exception {
        return getResult(roleClient.update(entity));
    }

    @Override
    public boolean updateState(UpdateState entity) throws Exception {
        return getResult(roleClient.updateState(entity));
    }

    @Override
    public AuthRoleVO get(Long id) throws Exception {
        return getResult(roleClient.get(id));
    }

    @Override
    public PageVO<AuthRoleVO> queryPage(PageQuery pageQuery) throws Exception {
        return getResult(roleClient.queryPage(pageQuery));
    }

    @Override
    public List<AuthRoleVO> query(Map<String, Object> query) throws Exception {
        return getResult(roleClient.query(query));
    }

    @Override
    public List<AuthRoleVO> querySome(Map<String, Object> query, String[] columns) throws Exception {
        return getResult(roleClient.querySome(query,columns));
    }

    @Override
    public List<AuthRoleVO> queryAll(Map<String, Object> query) throws Exception {
        return getResult(roleClient.queryAll(query));
    }

    @Override
    public List<AuthRoleVO> getByUserId(Long userId) throws Exception {
        return null;
    }
}
