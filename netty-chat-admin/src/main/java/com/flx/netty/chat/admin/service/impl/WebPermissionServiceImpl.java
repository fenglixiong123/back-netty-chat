package com.flx.netty.chat.admin.service.impl;

import com.flx.netty.chat.admin.service.WebPermissionService;
import com.flx.netty.chat.auth.api.client.WebPermissionClient;
import com.flx.netty.chat.auth.api.vo.WebPermissionVO;
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
public class WebPermissionServiceImpl implements WebPermissionService {

    @Autowired
    private WebPermissionClient permissionClient;


    @Override
    public Long add(WebPermissionVO entity) throws Exception {
        return getResult(permissionClient.add(entity));
    }

    @Override
    public Integer delete(Long id) throws Exception {
        return getResult(permissionClient.delete(id));
    }

    @Override
    public Integer update(WebPermissionVO entity) throws Exception {
        return getResult(permissionClient.update(entity));
    }

    @Override
    public boolean updateState(UpdateState entity) throws Exception {
        return getResult(permissionClient.updateState(entity));
    }

    @Override
    public WebPermissionVO get(Long id) throws Exception {
        return getResult(permissionClient.get(id));
    }

    @Override
    public PageVO<WebPermissionVO> queryPage(PageQuery pageQuery) throws Exception {
        return getResult(permissionClient.queryPage(pageQuery));
    }

    @Override
    public List<WebPermissionVO> query(Map<String, Object> query) throws Exception {
        return getResult(permissionClient.query(query));
    }

    @Override
    public List<WebPermissionVO> querySome(Map<String, Object> query, String[] columns) throws Exception {
        return getResult(permissionClient.querySome(query,columns));
    }

    @Override
    public List<WebPermissionVO> queryAll(Map<String, Object> query) throws Exception {
        return getResult(permissionClient.queryAll(query));
    }

    @Override
    public List<WebPermissionVO> getByRoleId(Long roleId) throws Exception {
        return null;
    }

    @Override
    public List<WebPermissionVO> getByRoleIds(Set<Long> roleIds) throws Exception {
        return null;
    }
}
