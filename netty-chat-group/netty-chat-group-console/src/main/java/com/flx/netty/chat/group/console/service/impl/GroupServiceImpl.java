package com.flx.netty.chat.group.console.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.flx.netty.chat.common.entity.UpdateState;
import com.flx.netty.chat.common.utils.page.PageQuery;
import com.flx.netty.chat.common.utils.page.PageVO;
import com.flx.netty.chat.common.utils.servlet.BeanUtils;
import com.flx.netty.chat.group.api.vo.WebGroupVO;
import com.flx.netty.chat.group.console.service.GroupService;
import com.flx.netty.chat.group.crud.entity.WebGroup;
import com.flx.netty.chat.group.crud.manager.GroupManager;
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
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupManager groupManager;

    private void convertVO(List<WebGroupVO> entityList){

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long add(WebGroupVO entityVO) throws Exception {
        return groupManager.add(BeanUtils.copyProperties(entityVO, WebGroup.class));
    }

    @Override
    public Integer delete(Long id) throws Exception {
        return groupManager.delete(id);
    }

    @Override
    public Integer update(WebGroupVO entityVO) throws Exception {
        return groupManager.update(BeanUtils.copyProperties(entityVO, WebGroup.class));
    }

    @Override
    public boolean updateState(UpdateState entityVO) throws Exception {
        for (Long id : entityVO.getIds()){
            WebGroup entity = new WebGroup();
            entity.setId(id);
            entity.setState(entityVO.getState());
            entity.setUpdateUser(entityVO.getUpdateUser());
            groupManager.updateState(entity);
        }
        return true;
    }

    @Override
    public WebGroupVO get(Long id) throws Exception {
        WebGroup entity = groupManager.get(id);
        if(entity==null){
            return null;
        }
        return BeanUtils.copyProperties(entity, WebGroupVO.class);
    }

    @Override
    public PageVO<WebGroupVO> queryPage(PageQuery pageQuery) throws Exception {
        IPage<WebGroup> iPage = groupManager.queryPage(pageQuery.getPageNum(),pageQuery.getPageSize(),pageQuery.getQuery());
        PageVO<WebGroupVO> pageVO = PageConvert.pageConvert(iPage, WebGroupVO.class);
        convertVO(pageVO.getRecords());
        return pageVO;
    }

    @Override
    public List<WebGroupVO> query(Map<String, Object> query) throws Exception {
        return groupManager.query(query).parallelStream().map(e -> BeanUtils.copyProperties(e, WebGroupVO.class)).collect(Collectors.toList());
    }

    @Override
    public List<WebGroupVO> querySome(Map<String, Object> query,String[] columns) throws Exception {
        return groupManager.querySome(query,columns).parallelStream().map(e -> BeanUtils.copyProperties(e, WebGroupVO.class)).collect(Collectors.toList());
    }

    @Override
    public List<WebGroupVO> queryAll(Map<String, Object> query) throws Exception {
        return groupManager.queryAll(query).parallelStream().map(e -> BeanUtils.copyProperties(e, WebGroupVO.class)).collect(Collectors.toList());
    }

    
}
