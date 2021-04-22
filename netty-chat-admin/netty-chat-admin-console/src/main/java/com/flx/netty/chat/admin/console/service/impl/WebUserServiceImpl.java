package com.flx.netty.chat.admin.console.service.impl;

import com.flx.netty.chat.admin.console.service.WebUserService;
import com.flx.netty.chat.common.entity.UpdateState;
import com.flx.netty.chat.common.utils.page.PageQuery;
import com.flx.netty.chat.common.utils.page.PageVO;
import com.flx.netty.chat.user.api.service.IUserService;
import com.flx.netty.chat.user.api.vo.ValidatePassVO;
import com.flx.netty.chat.user.api.vo.WebUserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class WebUserServiceImpl implements WebUserService {

    @Autowired
    private IUserService userService;

    @Override
    public Long add(WebUserVO entityVO) throws Exception {
        return getResult(userService.add(entityVO));
    }

    @Override
    public Integer delete(Long id) throws Exception {
        return getResult(userService.delete(id));
    }

    @Override
    public Integer update(WebUserVO entityVO) throws Exception {
        return getResult(userService.update(entityVO));
    }

    @Override
    public boolean updateState(UpdateState entityVO) throws Exception {
        return getResult(userService.updateState(entityVO));
    }

    @Override
    public WebUserVO get(Long id) throws Exception {
        return getResult(userService.get(id));
    }

    @Override
    public PageVO<WebUserVO> queryPage(PageQuery pageQuery) throws Exception {
        return getResult(userService.queryPage(pageQuery));
    }

    @Override
    public List<WebUserVO> query(Map<String, Object> query) throws Exception {
        return getResult(userService.query(query));
    }

    @Override
    public List<WebUserVO> queryAll(Map<String, Object> query) throws Exception {
        return getResult(userService.queryAll(query));
    }

    @Override
    public List<WebUserVO> querySome(Map<String, Object> query,String[] columns) throws Exception {
        return getResult(userService.querySome(query,columns));
    }

    @Override
    public WebUserVO getByUsername(String username) throws Exception{
        return getResult(userService.getByUsername(username));
    }

    @Override
    public boolean isExist(String username) throws Exception{
        return getResult(userService.existByUsername(username));
    }

    @Override
    public WebUserVO validateUser(String username, String password) throws Exception {
        return getResult(userService.validateUser(ValidatePassVO.build(username,password)));
    }


}
