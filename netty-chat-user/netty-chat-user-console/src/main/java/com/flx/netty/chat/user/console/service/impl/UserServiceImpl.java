package com.flx.netty.chat.user.console.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.flx.netty.chat.common.plugins.mybatis.entity.StateVO;
import com.flx.netty.chat.common.plugins.mybatis.page.PageConvert;
import com.flx.netty.chat.common.plugins.mybatis.page.QueryAndPage;
import com.flx.netty.chat.common.utils.servlet.BeanUtils;
import com.flx.netty.chat.user.api.vo.WebUserVO;
import com.flx.netty.chat.user.console.service.UserService;
import com.flx.netty.chat.user.crud.entity.WebUser;
import com.flx.netty.chat.user.crud.manager.UserManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: Fenglixiong
 * @Date: 2020/7/24 16:34
 * @Description:
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserManager userManager;

    private void convertVO(List<WebUserVO> entityList){

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long add(WebUserVO entityVO) throws Exception {
        return userManager.add(BeanUtils.copyProperties(entityVO, WebUser.class));
    }

    @Override
    public Integer delete(Long id) throws Exception {
        return userManager.delete(id);
    }

    @Override
    public Integer update(WebUserVO entityVO) throws Exception {
        return userManager.update(BeanUtils.copyProperties(entityVO, WebUser.class));
    }

    @Override
    public boolean updateState(StateVO stateVO) throws Exception {
        for (Long id : stateVO.getIds()){
            WebUser entity = new WebUser();
            entity.setId(id);
            entity.setState(stateVO.getState());
            entity.setUpdateUser(stateVO.getUpdateUser());
            userManager.updateState(entity);
        }
        return true;
    }

    @Override
    public WebUserVO get(Long id) throws Exception {
        WebUser entity = userManager.get(id);
        if(entity==null){
            return null;
        }
        return BeanUtils.copyProperties(entity, WebUserVO.class);
    }

    @Override
    public IPage<WebUserVO> queryPage(QueryAndPage queryAndPage) throws Exception {
        IPage<WebUser> iPage = userManager.queryPage(queryAndPage.getPageNum(),queryAndPage.getPageSize(),queryAndPage.getQuery());
        IPage<WebUserVO> voPage = PageConvert.pageConvert(iPage, WebUserVO.class);
        convertVO(voPage.getRecords());
        return voPage;
    }

    @Override
    public List<WebUserVO> query(Map<String, Object> query) throws Exception {
        return userManager.query(query).parallelStream().map(e -> BeanUtils.copyProperties(e, WebUserVO.class)).collect(Collectors.toList());
    }

    @Override
    public List<WebUserVO> queryAll(Map<String, Object> query) throws Exception {
        return userManager.queryAll(query).parallelStream().map(e -> BeanUtils.copyProperties(e, WebUserVO.class)).collect(Collectors.toList());
    }

    @Override
    public List<WebUserVO> querySome(Map<String, Object> query,String[] columns) throws Exception {
        return userManager.querySome(query,columns).parallelStream().map(e -> BeanUtils.copyProperties(e, WebUserVO.class)).collect(Collectors.toList());
    }

    
}
