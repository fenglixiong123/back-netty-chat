package com.flx.netty.chat.user.console.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.flx.netty.chat.common.plugins.mybatis.entity.StateVO;
import com.flx.netty.chat.common.plugins.mybatis.page.PageConvert;
import com.flx.netty.chat.common.plugins.mybatis.page.QueryAndPage;
import com.flx.netty.chat.common.utils.servlet.BeanUtils;
import com.flx.netty.chat.user.api.vo.WebFriendVO;
import com.flx.netty.chat.user.console.service.FriendService;
import com.flx.netty.chat.user.crud.entity.WebFriend;
import com.flx.netty.chat.user.crud.manager.FriendManager;
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
public class FriendServiceImpl implements FriendService {

    @Autowired
    private FriendManager friendManager;

    private void convertVO(List<WebFriendVO> entityList){

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long add(WebFriendVO entityVO) throws Exception {
        return friendManager.add(BeanUtils.copyProperties(entityVO, WebFriend.class));
    }

    @Override
    public Integer delete(Long id) throws Exception {
        return friendManager.delete(id);
    }

    @Override
    public Integer update(WebFriendVO entityVO) throws Exception {
        return friendManager.update(BeanUtils.copyProperties(entityVO, WebFriend.class));
    }

    @Override
    public boolean updateState(StateVO stateVO) throws Exception {
        for (Long id : stateVO.getIds()){
            WebFriend entity = new WebFriend();
            entity.setId(id);
            entity.setState(stateVO.getState());
            entity.setUpdateUser(stateVO.getUpdateUser());
            friendManager.updateState(entity);
        }
        return true;
    }

    @Override
    public WebFriendVO get(Long id) throws Exception {
        WebFriend entity = friendManager.get(id);
        if(entity==null){
            return null;
        }
        return BeanUtils.copyProperties(entity, WebFriendVO.class);
    }

    @Override
    public IPage<WebFriendVO> queryPage(QueryAndPage queryAndPage) throws Exception {
        IPage<WebFriend> iPage = friendManager.queryPage(queryAndPage.getPageNum(),queryAndPage.getPageSize(),queryAndPage.getQuery());
        IPage<WebFriendVO> voPage = PageConvert.pageConvert(iPage, WebFriendVO.class);
        convertVO(voPage.getRecords());
        return voPage;
    }

    @Override
    public List<WebFriendVO> query(Map<String, Object> query) throws Exception {
        return friendManager.query(query).parallelStream().map(e -> BeanUtils.copyProperties(e, WebFriendVO.class)).collect(Collectors.toList());
    }

    @Override
    public List<WebFriendVO> queryAll(Map<String, Object> query) throws Exception {
        return friendManager.queryAll(query).parallelStream().map(e -> BeanUtils.copyProperties(e, WebFriendVO.class)).collect(Collectors.toList());
    }

    @Override
    public List<WebFriendVO> querySome(Map<String, Object> query,String[] columns) throws Exception {
        return friendManager.querySome(query,columns).parallelStream().map(e -> BeanUtils.copyProperties(e, WebFriendVO.class)).collect(Collectors.toList());
    }

    
}
