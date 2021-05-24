package com.flx.netty.chat.auth.console.service.impl;

import com.flx.netty.chat.auth.api.vo.AuthRoleVO;
import com.flx.netty.chat.auth.crud.entity.AuthRole;
import com.flx.netty.chat.auth.console.service.AuthRoleService;
import com.flx.netty.chat.auth.crud.entity.AuthUserRole;
import com.flx.netty.chat.auth.crud.manager.AuthRoleManager;
import com.flx.netty.chat.auth.crud.manager.AuthUserRoleManager;
import com.flx.netty.chat.common.enums.State;
import com.flx.netty.chat.common.utils.CollectionUtils;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *  Service服务实现类
 *
 * @author Fenglixiong
 * @since 2021-05-16
 */
@Slf4j
@Service
public class AuthRoleServiceImpl implements AuthRoleService {

    @Autowired
    private AuthRoleManager roleManager;
    @Autowired
    private AuthUserRoleManager userRoleManager;

    private void convertVO(List<AuthRoleVO> entityList){
   
    }
   
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long add(AuthRoleVO entityVO) throws Exception {
        AuthRole entity = roleManager.get(entityVO.getId());
        if(entity!=null){
            throw new Exception("不能重复添加!");
        }
        return roleManager.add(BeanUtils.copyProperties(entityVO, AuthRole.class));
    }
   
    @Override
    public Integer delete(Long id) throws Exception {
        return roleManager.delete(id);
    }
   
    @Override
    public Integer update(AuthRoleVO entityVO) throws Exception {
        if(entityVO.getId()==null){
            throw new Exception("Id不能为空!");
        }
        return roleManager.update(BeanUtils.copyProperties(entityVO, AuthRole.class));
    }
   
    @Override
    public boolean updateState(UpdateState entityVO) throws Exception {
        for (Long id : entityVO.getIds()){
            AuthRole entity = new AuthRole();
            entity.setId(id);
            entity.setState(entityVO.getState());
            entity.setUpdateUser(entityVO.getUpdateUser());
            roleManager.updateState(entity);
        }
        return true;
    }
   
    @Override
    public AuthRoleVO get(Long id) throws Exception {
        AuthRole entity = roleManager.get(id);
        if(entity==null){
            return null;
        }
        return BeanUtils.copyProperties(entity, AuthRoleVO.class);
    }
   
    @Override
    public PageVO<AuthRoleVO> queryPage(PageQuery pageQuery) throws Exception {
        IPage<AuthRole> iPage = roleManager.queryPage(pageQuery.getPageNum(),pageQuery.getPageSize(),pageQuery.getQuery());
        PageVO<AuthRoleVO> pageVO = PageConvert.pageConvert(iPage, AuthRoleVO.class);
        convertVO(pageVO.getRecords());
        return pageVO;
    }
   
    @Override
    public List<AuthRoleVO> query(Map<String, Object> query) throws Exception {
        return roleManager.query(query).parallelStream().map(e -> BeanUtils.copyProperties(e, AuthRoleVO.class)).collect(Collectors.toList());
    }
   
    @Override
    public List<AuthRoleVO> querySome(Map<String, Object> query, String[] columns) throws Exception {
        return roleManager.querySome(query,columns).parallelStream().map(e -> BeanUtils.copyProperties(e, AuthRoleVO.class)).collect(Collectors.toList());
    }
   
    @Override
    public List<AuthRoleVO> queryAll(Map<String, Object> query) throws Exception {
        return roleManager.queryAll(query).parallelStream().map(e -> BeanUtils.copyProperties(e, AuthRoleVO.class)).collect(Collectors.toList());
    }

    @Override
    public List<AuthRoleVO> getByUserId(Long userId) throws Exception {
        List<AuthUserRole> userRoles = userRoleManager.getByUserId(userId, State.effective.name());
        Set<Long> ids = userRoles.stream().map(AuthUserRole::getRoleId).collect(Collectors.toSet());
        if(CollectionUtils.isNotEmpty(ids)){
            return roleManager.getByIds(ids, State.effective.name()).parallelStream().map(e -> BeanUtils.copyProperties(e, AuthRoleVO.class)).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

}
