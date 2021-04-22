package com.flx.netty.chat.message.console.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.flx.netty.chat.common.entity.UpdateState;
import com.flx.netty.chat.common.utils.page.PageQuery;
import com.flx.netty.chat.common.utils.page.PageVO;
import com.flx.netty.chat.common.utils.servlet.BeanUtils;
import com.flx.netty.chat.message.api.vo.WebGroupMessageVO;
import com.flx.netty.chat.message.console.service.GroupMessageService;
import com.flx.netty.chat.message.crud.entity.WebGroupMessage;
import com.flx.netty.chat.message.crud.manager.GroupMessageManager;
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
public class GroupMessageServiceImpl implements GroupMessageService {

    @Autowired
    private GroupMessageManager groupMessageManager;

    private void convertVO(List<WebGroupMessageVO> entityList){

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long add(WebGroupMessageVO entityVO) throws Exception {
        return groupMessageManager.add(BeanUtils.copyProperties(entityVO, WebGroupMessage.class));
    }

    @Override
    public Integer delete(Long id) throws Exception {
        return groupMessageManager.delete(id);
    }

    @Override
    public Integer update(WebGroupMessageVO entityVO) throws Exception {
        return groupMessageManager.update(BeanUtils.copyProperties(entityVO, WebGroupMessage.class));
    }

    @Override
    public boolean updateState(UpdateState entityVO) throws Exception {
        for (Long id : entityVO.getIds()){
            WebGroupMessage entity = new WebGroupMessage();
            entity.setId(id);
            entity.setState(entityVO.getState());
            entity.setUpdateUser(entityVO.getUpdateUser());
            groupMessageManager.updateState(entity);
        }
        return true;
    }

    @Override
    public WebGroupMessageVO get(Long id) throws Exception {
        WebGroupMessage entity = groupMessageManager.get(id);
        if(entity==null){
            return null;
        }
        return BeanUtils.copyProperties(entity, WebGroupMessageVO.class);
    }

    @Override
    public PageVO<WebGroupMessageVO> queryPage(PageQuery pageQuery) throws Exception {
        IPage<WebGroupMessage> iPage = groupMessageManager.queryPage(pageQuery.getPageNum(),pageQuery.getPageSize(),pageQuery.getQuery());
        PageVO<WebGroupMessageVO> pageVO = PageConvert.pageConvert(iPage, WebGroupMessageVO.class);
        convertVO(pageVO.getRecords());
        return pageVO;
    }

    @Override
    public List<WebGroupMessageVO> query(Map<String, Object> query) throws Exception {
        return groupMessageManager.query(query).parallelStream().map(e -> BeanUtils.copyProperties(e, WebGroupMessageVO.class)).collect(Collectors.toList());
    }

    @Override
    public List<WebGroupMessageVO> querySome(Map<String, Object> query,String[] columns) throws Exception {
        return groupMessageManager.querySome(query,columns).parallelStream().map(e -> BeanUtils.copyProperties(e, WebGroupMessageVO.class)).collect(Collectors.toList());
    }

    @Override
    public List<WebGroupMessageVO> queryAll(Map<String, Object> query) throws Exception {
        return groupMessageManager.queryAll(query).parallelStream().map(e -> BeanUtils.copyProperties(e, WebGroupMessageVO.class)).collect(Collectors.toList());
    }

    
}
