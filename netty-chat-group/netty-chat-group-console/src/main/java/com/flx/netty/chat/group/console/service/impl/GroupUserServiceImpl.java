package com.flx.netty.chat.group.console.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.flx.netty.chat.common.entity.UpdateState;
import com.flx.netty.chat.common.utils.page.PageQuery;
import com.flx.netty.chat.common.utils.servlet.BeanUtils;
import com.flx.netty.chat.group.api.vo.WebGroupUserVO;
import com.flx.netty.chat.group.console.service.GroupUserService;
import com.flx.netty.chat.group.crud.entity.WebGroupUser;
import com.flx.netty.chat.group.crud.manager.GroupUserManager;
import com.flx.netty.chat.plugin.plugins.mybatis.page.PageConvert;
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
public class GroupUserServiceImpl implements GroupUserService {

    @Autowired
    private GroupUserManager groupManager;

    private void convertVO(List<WebGroupUserVO> entityList){

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long add(WebGroupUserVO entityVO) throws Exception {
        return groupManager.add(BeanUtils.copyProperties(entityVO, WebGroupUser.class));
    }

    @Override
    public Integer delete(Long id) throws Exception {
        return groupManager.delete(id);
    }

    @Override
    public Integer update(WebGroupUserVO entityVO) throws Exception {
        return groupManager.update(BeanUtils.copyProperties(entityVO, WebGroupUser.class));
    }

    @Override
    public boolean updateState(UpdateState entityVO) throws Exception {
        for (Long id : entityVO.getIds()){
            WebGroupUser entity = new WebGroupUser();
            entity.setId(id);
            entity.setState(entityVO.getState());
            entity.setUpdateUser(entityVO.getUpdateUser());
            groupManager.updateState(entity);
        }
        return true;
    }

    @Override
    public WebGroupUserVO get(Long id) throws Exception {
        WebGroupUser entity = groupManager.get(id);
        if(entity==null){
            return null;
        }
        return BeanUtils.copyProperties(entity, WebGroupUserVO.class);
    }

    @Override
    public IPage<WebGroupUserVO> queryPage(PageQuery pageQuery) throws Exception {
        IPage<WebGroupUser> iPage = groupManager.queryPage(pageQuery.getPageNum(),pageQuery.getPageSize(),pageQuery.getQuery());
        IPage<WebGroupUserVO> voPage = PageConvert.pageConvert(iPage, WebGroupUserVO.class);
        convertVO(voPage.getRecords());
        return voPage;
    }

    @Override
    public List<WebGroupUserVO> query(Map<String, Object> query) throws Exception {
        return groupManager.query(query).parallelStream().map(e -> BeanUtils.copyProperties(e, WebGroupUserVO.class)).collect(Collectors.toList());
    }

    @Override
    public List<WebGroupUserVO> queryAll(Map<String, Object> query) throws Exception {
        return groupManager.queryAll(query).parallelStream().map(e -> BeanUtils.copyProperties(e, WebGroupUserVO.class)).collect(Collectors.toList());
    }

    @Override
    public List<WebGroupUserVO> querySome(Map<String, Object> query,String[] columns) throws Exception {
        return groupManager.querySome(query,columns).parallelStream().map(e -> BeanUtils.copyProperties(e, WebGroupUserVO.class)).collect(Collectors.toList());
    }

    
}
