package com.flx.netty.chat.auth.console.service.impl;

import com.flx.netty.chat.auth.api.vo.AuthPermissionVO;
import com.flx.netty.chat.auth.crud.entity.AuthPermission;
import com.flx.netty.chat.auth.console.service.AuthPermissionService;
import com.flx.netty.chat.auth.crud.entity.AuthRolePermission;
import com.flx.netty.chat.auth.crud.manager.AuthPermissionManager;
import com.flx.netty.chat.auth.crud.manager.AuthRolePermissionManager;
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
public class AuthPermissionServiceImpl implements AuthPermissionService {

    @Autowired
    private AuthPermissionManager permissionManager;
    @Autowired
    private AuthRolePermissionManager rolePermissionManager;

    private void convertVO(List<AuthPermissionVO> entityList){
   
    }
   
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long add(AuthPermissionVO entityVO) throws Exception {
        AuthPermission entity = permissionManager.get(entityVO.getId());
        if(entity!=null){
            throw new Exception("不能重复添加!");
        }
        return permissionManager.add(BeanUtils.copyProperties(entityVO, AuthPermission.class));
    }
   
    @Override
    public Integer delete(Long id) throws Exception {
        return permissionManager.delete(id);
    }
   
    @Override
    public Integer update(AuthPermissionVO entityVO) throws Exception {
        if(entityVO.getId()==null){
            throw new Exception("Id不能为空!");
        }
        return permissionManager.update(BeanUtils.copyProperties(entityVO, AuthPermission.class));
    }
   
    @Override
    public boolean updateState(UpdateState entityVO) throws Exception {
        for (Long id : entityVO.getIds()){
            AuthPermission entity = new AuthPermission();
            entity.setId(id);
            entity.setState(entityVO.getState());
            entity.setUpdateUser(entityVO.getUpdateUser());
            permissionManager.updateState(entity);
        }
        return true;
    }
   
    @Override
    public AuthPermissionVO get(Long id) throws Exception {
        AuthPermission entity = permissionManager.get(id);
        if(entity==null){
            return null;
        }
        return BeanUtils.copyProperties(entity, AuthPermissionVO.class);
    }
   
    @Override
    public PageVO<AuthPermissionVO> queryPage(PageQuery pageQuery) throws Exception {
        IPage<AuthPermission> iPage = permissionManager.queryPage(pageQuery.getPageNum(),pageQuery.getPageSize(),pageQuery.getQuery());
        PageVO<AuthPermissionVO> pageVO = PageConvert.pageConvert(iPage, AuthPermissionVO.class);
        convertVO(pageVO.getRecords());
        return pageVO;
    }
   
    @Override
    public List<AuthPermissionVO> query(Map<String, Object> query) throws Exception {
        return permissionManager.query(query).parallelStream().map(e -> BeanUtils.copyProperties(e, AuthPermissionVO.class)).collect(Collectors.toList());
    }
   
    @Override
    public List<AuthPermissionVO> querySome(Map<String, Object> query, String[] columns) throws Exception {
        return permissionManager.querySome(query,columns).parallelStream().map(e -> BeanUtils.copyProperties(e, AuthPermissionVO.class)).collect(Collectors.toList());
    }
   
    @Override
    public List<AuthPermissionVO> queryAll(Map<String, Object> query) throws Exception {
        return permissionManager.queryAll(query).parallelStream().map(e -> BeanUtils.copyProperties(e, AuthPermissionVO.class)).collect(Collectors.toList());
    }

    @Override
    public List<AuthPermissionVO> getByRoleId(Long roleId) throws Exception {
        List<AuthRolePermission> rolePermissions = rolePermissionManager.getByRoleId(roleId, State.effective.name());
        Set<Long> permissionIds = rolePermissions.stream().map(AuthRolePermission::getPermissionId).collect(Collectors.toSet());
        if(CollectionUtils.isNotEmpty(permissionIds)){
            return permissionManager.getByIds(permissionIds,State.effective.name()).parallelStream().map(e -> BeanUtils.copyProperties(e, AuthPermissionVO.class)).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    @Override
    public List<AuthPermissionVO> getByRoleIds(Set<Long> roleIds) throws Exception {
        List<AuthRolePermission> rolePermissions = rolePermissionManager.getByRoleIds(roleIds, State.effective.name());
        Set<Long> permissionIds = rolePermissions.stream().map(AuthRolePermission::getPermissionId).collect(Collectors.toSet());
        if(CollectionUtils.isNotEmpty(permissionIds)){
            //根据java8的map流去重，如果map的key重复了保留第一个
            return  permissionManager.getByIds(permissionIds,State.effective.name())
                    .parallelStream().collect(Collectors.toMap(AuthPermission::getCode, e->e,(o1, o2)->o1)).values()//去重
                    .parallelStream().map(e -> BeanUtils.copyProperties(e, AuthPermissionVO.class)).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

}
