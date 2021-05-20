package com.flx.netty.chat.admin.service.impl;

import com.flx.netty.chat.admin.service.WebUserService;
import com.flx.netty.chat.auth.api.client.WebUserClient;
import com.flx.netty.chat.auth.api.vo.ValidatePassVO;
import com.flx.netty.chat.auth.api.vo.WebPermissionVO;
import com.flx.netty.chat.auth.api.vo.WebUserVO;
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
public class WebUserServiceImpl implements WebUserService {

    @Autowired
    private WebUserClient userClient;

    @Override
    public Long add(WebUserVO entity) throws Exception {
        return getResult(userClient.add(entity));
    }

    @Override
    public Integer delete(Long id) throws Exception {
        return getResult(userClient.delete(id));
    }

    @Override
    public Integer update(WebUserVO entity) throws Exception {
        return getResult(userClient.update(entity));
    }

    @Override
    public boolean updateState(UpdateState entity) throws Exception {
        return getResult(userClient.updateState(entity));
    }

    @Override
    public WebUserVO get(Long id) throws Exception {
        return getResult(userClient.get(id));
    }

    @Override
    public PageVO<WebUserVO> queryPage(PageQuery pageQuery) throws Exception {
        return getResult(userClient.queryPage(pageQuery));
    }

    @Override
    public List<WebUserVO> query(Map<String, Object> query) throws Exception {
        return getResult(userClient.query(query));
    }

    @Override
    public List<WebUserVO> querySome(Map<String, Object> query, String[] columns) throws Exception {
        return getResult(userClient.querySome(query,columns));
    }

    @Override
    public List<WebUserVO> queryAll(Map<String, Object> query) throws Exception {
        return getResult(userClient.queryAll(query));
    }

    @Override
    public WebUserVO getByUsername(String username) throws Exception {
        return getResult(userClient.getByUsername(username));
    }

    @Override
    public boolean isExist(String username) throws Exception {
        return getResult(userClient.existByUsername(username));
    }

    @Override
    public WebUserVO validateUser(String username, String password) throws Exception {
        return getResult(userClient.validateUser(new ValidatePassVO(username,password)));
    }

    @Override
    public List<WebPermissionVO> getPermissionById(Long id) throws Exception {
        return null;
    }
}
