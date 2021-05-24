package com.flx.netty.chat.auth.console.service.impl;

import com.flx.netty.chat.auth.api.vo.AuthClientVO;
import com.flx.netty.chat.auth.crud.entity.AuthClient;
import com.flx.netty.chat.auth.console.service.AuthClientService;
import com.flx.netty.chat.auth.crud.manager.AuthClientManager;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

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
public class AuthClientServiceImpl implements AuthClientService {

    @Autowired
    private AuthClientManager clientDetailManager;

    private void convertVO(List<AuthClientVO> entityList){
   
    }
   
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long add(AuthClientVO entityVO) throws Exception {
        AuthClient entity = clientDetailManager.get(entityVO.getId());
        if(entity!=null){
            throw new Exception("不能重复添加!");
        }
        return clientDetailManager.add(BeanUtils.copyProperties(entityVO, AuthClient.class));
    }
   
    @Override
    public Integer delete(Long id) throws Exception {
        return clientDetailManager.delete(id);
    }
   
    @Override
    public Integer update(AuthClientVO entityVO) throws Exception {
        if(entityVO.getId()==null){
            throw new Exception("Id不能为空!");
        }
        return clientDetailManager.update(BeanUtils.copyProperties(entityVO, AuthClient.class));
    }
   
    @Override
    public boolean updateState(UpdateState entityVO) throws Exception {
        for (Long id : entityVO.getIds()){
            AuthClient entity = new AuthClient();
            entity.setId(id);
            entity.setState(entityVO.getState());
            entity.setUpdateUser(entityVO.getUpdateUser());
            clientDetailManager.updateState(entity);
        }
        return true;
    }
   
    @Override
    public AuthClientVO get(Long id) throws Exception {
        AuthClient entity = clientDetailManager.get(id);
        if(entity==null){
            return null;
        }
        return BeanUtils.copyProperties(entity, AuthClientVO.class);
    }
   
    @Override
    public PageVO<AuthClientVO> queryPage(PageQuery pageQuery) throws Exception {
        IPage<AuthClient> iPage = clientDetailManager.queryPage(pageQuery.getPageNum(),pageQuery.getPageSize(),pageQuery.getQuery());
        PageVO<AuthClientVO> pageVO = PageConvert.pageConvert(iPage, AuthClientVO.class);
        convertVO(pageVO.getRecords());
        return pageVO;
    }
   
    @Override
    public List<AuthClientVO> query(Map<String, Object> query) throws Exception {
        return clientDetailManager.query(query).parallelStream().map(e -> BeanUtils.copyProperties(e, AuthClientVO.class)).collect(Collectors.toList());
    }
   
    @Override
    public List<AuthClientVO> querySome(Map<String, Object> query, String[] columns) throws Exception {
        return clientDetailManager.querySome(query,columns).parallelStream().map(e -> BeanUtils.copyProperties(e, AuthClientVO.class)).collect(Collectors.toList());
    }
   
    @Override
    public List<AuthClientVO> queryAll(Map<String, Object> query) throws Exception {
        return clientDetailManager.queryAll(query).parallelStream().map(e -> BeanUtils.copyProperties(e, AuthClientVO.class)).collect(Collectors.toList());
    }
 
}
