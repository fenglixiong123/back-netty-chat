package com.flx.netty.chat.auth.console.service.impl;

import com.flx.netty.chat.auth.api.vo.ClientDetailVO;
import com.flx.netty.chat.auth.crud.entity.ClientDetail;
import com.flx.netty.chat.auth.crud.dao.ClientDetailDao;
import com.flx.netty.chat.auth.console.service.ClientDetailService;
import com.flx.netty.chat.auth.crud.manager.ClientDetailManager;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.flx.netty.chat.common.enums.State;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.flx.netty.chat.common.utils.page.PageQuery;
import com.flx.netty.chat.common.utils.page.PageVO;
import com.flx.netty.chat.common.utils.servlet.BeanUtils;
import com.flx.netty.chat.common.entity.UpdateState;
import com.flx.netty.chat.plugin.plugins.mybatis.page.PageConvert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 客户端信息表 Service服务实现类
 *
 * @author Fenglixiong
 * @since 2021-05-07
 */
@Slf4j
@Service
public class ClientDetailServiceImpl implements ClientDetailService {

    @Autowired
    private ClientDetailManager clientDetailManager;

    private void convertVO(List<ClientDetailVO> entityList){
   
    }
   
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long add(ClientDetailVO entityVO) throws Exception {
        ClientDetail entity = clientDetailManager.get(entityVO.getId());
        if(entity!=null){
            throw new Exception("不能重复添加!");
        }
        return clientDetailManager.add(BeanUtils.copyProperties(entityVO, ClientDetail.class));
    }
   
    @Override
    public Integer delete(Long id) throws Exception {
        return clientDetailManager.delete(id);
    }
   
    @Override
    public Integer update(ClientDetailVO entityVO) throws Exception {
        if(entityVO.getId()==null){
            throw new Exception("Id不能为空!");
        }
        return clientDetailManager.update(BeanUtils.copyProperties(entityVO, ClientDetail.class));
    }
   
    @Override
    public boolean updateState(UpdateState entityVO) throws Exception {
        for (Long id : entityVO.getIds()){
            ClientDetail entity = new ClientDetail();
            entity.setId(id);
            entity.setState(entityVO.getState());
            entity.setUpdateUser(entityVO.getUpdateUser());
            clientDetailManager.updateState(entity);
        }
        return true;
    }
   
    @Override
    public ClientDetailVO get(Long id) throws Exception {
        ClientDetail entity = clientDetailManager.get(id);
        if(entity==null){
            return null;
        }
        return BeanUtils.copyProperties(entity, ClientDetailVO.class);
    }
   
    @Override
    public PageVO<ClientDetailVO> queryPage(PageQuery pageQuery) throws Exception {
        IPage<ClientDetail> iPage = clientDetailManager.queryPage(pageQuery.getPageNum(),pageQuery.getPageSize(),pageQuery.getQuery());
        PageVO<ClientDetailVO> pageVO = PageConvert.pageConvert(iPage, ClientDetailVO.class);
        convertVO(pageVO.getRecords());
        return pageVO;
    }
   
    @Override
    public List<ClientDetailVO> query(Map<String, Object> query) throws Exception {
        return clientDetailManager.query(query).parallelStream().map(e -> BeanUtils.copyProperties(e, ClientDetailVO.class)).collect(Collectors.toList());
    }
   
    @Override
    public List<ClientDetailVO> querySome(Map<String, Object> query,String[] columns) throws Exception {
        return clientDetailManager.querySome(query,columns).parallelStream().map(e -> BeanUtils.copyProperties(e, ClientDetailVO.class)).collect(Collectors.toList());
    }
   
    @Override
    public List<ClientDetailVO> queryAll(Map<String, Object> query) throws Exception {
        return clientDetailManager.queryAll(query).parallelStream().map(e -> BeanUtils.copyProperties(e, ClientDetailVO.class)).collect(Collectors.toList());
    }
 
}
