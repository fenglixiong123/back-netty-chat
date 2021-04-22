package com.flx.netty.chat.user.console.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.flx.netty.chat.common.utils.page.PageQuery;
import com.flx.netty.chat.common.utils.page.PageVO;
import com.flx.netty.chat.common.utils.servlet.BeanUtils;
import com.flx.netty.chat.common.entity.UpdateState;
import com.flx.netty.chat.plugin.plugins.mybatis.page.PageConvert;
import com.flx.netty.chat.user.api.vo.WebLabelVO;
import com.flx.netty.chat.user.console.service.LabelService;
import com.flx.netty.chat.user.crud.entity.WebLabel;
import com.flx.netty.chat.user.crud.manager.LabelManager;
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
public class LabelServiceImpl implements LabelService {

    @Autowired
    private LabelManager labelManager;

    private void convertVO(List<WebLabelVO> entityList){

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long add(WebLabelVO entityVO) throws Exception {
        return labelManager.add(BeanUtils.copyProperties(entityVO, WebLabel.class));
    }

    @Override
    public Integer delete(Long id) throws Exception {
        return labelManager.delete(id);
    }

    @Override
    public Integer update(WebLabelVO entityVO) throws Exception {
        return labelManager.update(BeanUtils.copyProperties(entityVO, WebLabel.class));
    }

    @Override
    public boolean updateState(UpdateState entityVO) throws Exception {
        for (Long id : entityVO.getIds()){
            WebLabel entity = new WebLabel();
            entity.setId(id);
            entity.setState(entityVO.getState());
            entity.setUpdateUser(entityVO.getUpdateUser());
            labelManager.updateState(entity);
        }
        return true;
    }

    @Override
    public WebLabelVO get(Long id) throws Exception {
        WebLabel entity = labelManager.get(id);
        if(entity==null){
            return null;
        }
        return BeanUtils.copyProperties(entity, WebLabelVO.class);
    }

    @Override
    public PageVO<WebLabelVO> queryPage(PageQuery pageQuery) throws Exception {
        IPage<WebLabel> iPage = labelManager.queryPage(pageQuery.getPageNum(),pageQuery.getPageSize(),pageQuery.getQuery());
        PageVO<WebLabelVO> pageVO = PageConvert.pageConvert(iPage, WebLabelVO.class);
        convertVO(pageVO.getRecords());
        return pageVO;
    }

    @Override
    public List<WebLabelVO> query(Map<String, Object> query) throws Exception {
        return labelManager.query(query).parallelStream().map(e -> BeanUtils.copyProperties(e, WebLabelVO.class)).collect(Collectors.toList());
    }

    @Override
    public List<WebLabelVO> querySome(Map<String, Object> query,String[] columns) throws Exception {
        return labelManager.querySome(query,columns).parallelStream().map(e -> BeanUtils.copyProperties(e, WebLabelVO.class)).collect(Collectors.toList());
    }

    @Override
    public List<WebLabelVO> queryAll(Map<String, Object> query) throws Exception {
        return labelManager.queryAll(query).parallelStream().map(e -> BeanUtils.copyProperties(e, WebLabelVO.class)).collect(Collectors.toList());
    }



    
}
