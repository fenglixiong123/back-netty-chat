package com.flx.netty.chat.admin.service.impl;

import com.flx.netty.chat.admin.service.AuthPermissionService;
import com.flx.netty.chat.auth.api.client.AuthPermissionClient;
import com.flx.netty.chat.auth.api.vo.AuthPermissionVO;
import com.flx.netty.chat.common.entity.UpdateState;
import com.flx.netty.chat.common.utils.page.PageQuery;
import com.flx.netty.chat.common.utils.page.PageVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.flx.netty.chat.common.utils.result.ResultResponse.getResult;

/**
 *  服务实现类
 *
 * @author Fenglixiong
 * @since 2021-05-19
 */
@Slf4j
@Service
public class AuthPermissionServiceImpl implements AuthPermissionService {

    @Autowired
    private AuthPermissionClient permissionClient;


    @Override
    public Long add(AuthPermissionVO entity) throws Exception {
        return getResult(permissionClient.add(entity));
    }

    @Override
    public Integer delete(Long id) throws Exception {
        return getResult(permissionClient.delete(id));
    }

    @Override
    public Integer update(AuthPermissionVO entity) throws Exception {
        return getResult(permissionClient.update(entity));
    }

    @Override
    public boolean updateState(UpdateState entity) throws Exception {
        return getResult(permissionClient.updateState(entity));
    }

    @Override
    public AuthPermissionVO get(Long id) throws Exception {
        return getResult(permissionClient.get(id));
    }

    @Override
    public PageVO<AuthPermissionVO> queryPage(PageQuery pageQuery) throws Exception {
        return getResult(permissionClient.queryPage(pageQuery));
    }

    @Override
    public List<AuthPermissionVO> query(Map<String, Object> query) throws Exception {
        return getResult(permissionClient.query(query));
    }

    @Override
    public List<AuthPermissionVO> querySome(Map<String, Object> query, String[] columns) throws Exception {
        return getResult(permissionClient.querySome(query,columns));
    }

    @Override
    public List<AuthPermissionVO> queryAll(Map<String, Object> query) throws Exception {
        return getResult(permissionClient.queryAll(query));
    }

    @Override
    public List<AuthPermissionVO> getByRoleId(Long roleId) throws Exception {
        return null;
    }

    @Override
    public List<AuthPermissionVO> getByRoleIds(Set<Long> roleIds) throws Exception {
        return null;
    }
}
