package com.flx.netty.chat.auth.console.service.impl;

import com.flx.netty.chat.auth.api.vo.RoleVO;
import com.flx.netty.chat.auth.crud.entity.WebRole;
import com.flx.netty.chat.auth.console.service.RoleService;
import com.flx.netty.chat.auth.crud.manager.RoleManager;
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
 *  Service服务实现类
 *
 * @author Fenglixiong
 * @since 2021-05-16
 */
@Slf4j
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleManager roleManager;

    private void convertVO(List<RoleVO> entityList){
   
    }
   
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long add(RoleVO entityVO) throws Exception {
        WebRole entity = roleManager.get(entityVO.getId());
        if(entity!=null){
            throw new Exception("不能重复添加!");
        }
        return roleManager.add(BeanUtils.copyProperties(entityVO, WebRole.class));
    }
   
    @Override
    public Integer delete(Long id) throws Exception {
        return roleManager.delete(id);
    }
   
    @Override
    public Integer update(RoleVO entityVO) throws Exception {
        if(entityVO.getId()==null){
            throw new Exception("Id不能为空!");
        }
        return roleManager.update(BeanUtils.copyProperties(entityVO, WebRole.class));
    }
   
    @Override
    public boolean updateState(UpdateState entityVO) throws Exception {
        for (Long id : entityVO.getIds()){
            WebRole entity = new WebRole();
            entity.setId(id);
            entity.setState(entityVO.getState());
            entity.setUpdateUser(entityVO.getUpdateUser());
            roleManager.updateState(entity);
        }
        return true;
    }
   
    @Override
    public RoleVO get(Long id) throws Exception {
        WebRole entity = roleManager.get(id);
        if(entity==null){
            return null;
        }
        return BeanUtils.copyProperties(entity, RoleVO.class);
    }
   
    @Override
    public PageVO<RoleVO> queryPage(PageQuery pageQuery) throws Exception {
        IPage<WebRole> iPage = roleManager.queryPage(pageQuery.getPageNum(),pageQuery.getPageSize(),pageQuery.getQuery());
        PageVO<RoleVO> pageVO = PageConvert.pageConvert(iPage, RoleVO.class);
        convertVO(pageVO.getRecords());
        return pageVO;
    }
   
    @Override
    public List<RoleVO> query(Map<String, Object> query) throws Exception {
        return roleManager.query(query).parallelStream().map(e -> BeanUtils.copyProperties(e, RoleVO.class)).collect(Collectors.toList());
    }
   
    @Override
    public List<RoleVO> querySome(Map<String, Object> query,String[] columns) throws Exception {
        return roleManager.querySome(query,columns).parallelStream().map(e -> BeanUtils.copyProperties(e, RoleVO.class)).collect(Collectors.toList());
    }
   
    @Override
    public List<RoleVO> queryAll(Map<String, Object> query) throws Exception {
        return roleManager.queryAll(query).parallelStream().map(e -> BeanUtils.copyProperties(e, RoleVO.class)).collect(Collectors.toList());
    }
 
}
