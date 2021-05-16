package com.flx.netty.chat.auth.console.service.impl;

import com.flx.netty.chat.auth.api.vo.WebPermissionVO;
import com.flx.netty.chat.auth.crud.entity.WebPermission;
import com.flx.netty.chat.auth.console.service.PermissionService;
import com.flx.netty.chat.auth.crud.entity.WebRolePermission;
import com.flx.netty.chat.auth.crud.manager.PermissionManager;
import com.flx.netty.chat.auth.crud.manager.RolePermissionManager;
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
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionManager permissionManager;
    @Autowired
    private RolePermissionManager rolePermissionManager;

    private void convertVO(List<WebPermissionVO> entityList){
   
    }
   
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long add(WebPermissionVO entityVO) throws Exception {
        WebPermission entity = permissionManager.get(entityVO.getId());
        if(entity!=null){
            throw new Exception("不能重复添加!");
        }
        return permissionManager.add(BeanUtils.copyProperties(entityVO, WebPermission.class));
    }
   
    @Override
    public Integer delete(Long id) throws Exception {
        return permissionManager.delete(id);
    }
   
    @Override
    public Integer update(WebPermissionVO entityVO) throws Exception {
        if(entityVO.getId()==null){
            throw new Exception("Id不能为空!");
        }
        return permissionManager.update(BeanUtils.copyProperties(entityVO, WebPermission.class));
    }
   
    @Override
    public boolean updateState(UpdateState entityVO) throws Exception {
        for (Long id : entityVO.getIds()){
            WebPermission entity = new WebPermission();
            entity.setId(id);
            entity.setState(entityVO.getState());
            entity.setUpdateUser(entityVO.getUpdateUser());
            permissionManager.updateState(entity);
        }
        return true;
    }
   
    @Override
    public WebPermissionVO get(Long id) throws Exception {
        WebPermission entity = permissionManager.get(id);
        if(entity==null){
            return null;
        }
        return BeanUtils.copyProperties(entity, WebPermissionVO.class);
    }
   
    @Override
    public PageVO<WebPermissionVO> queryPage(PageQuery pageQuery) throws Exception {
        IPage<WebPermission> iPage = permissionManager.queryPage(pageQuery.getPageNum(),pageQuery.getPageSize(),pageQuery.getQuery());
        PageVO<WebPermissionVO> pageVO = PageConvert.pageConvert(iPage, WebPermissionVO.class);
        convertVO(pageVO.getRecords());
        return pageVO;
    }
   
    @Override
    public List<WebPermissionVO> query(Map<String, Object> query) throws Exception {
        return permissionManager.query(query).parallelStream().map(e -> BeanUtils.copyProperties(e, WebPermissionVO.class)).collect(Collectors.toList());
    }
   
    @Override
    public List<WebPermissionVO> querySome(Map<String, Object> query, String[] columns) throws Exception {
        return permissionManager.querySome(query,columns).parallelStream().map(e -> BeanUtils.copyProperties(e, WebPermissionVO.class)).collect(Collectors.toList());
    }
   
    @Override
    public List<WebPermissionVO> queryAll(Map<String, Object> query) throws Exception {
        return permissionManager.queryAll(query).parallelStream().map(e -> BeanUtils.copyProperties(e, WebPermissionVO.class)).collect(Collectors.toList());
    }

    @Override
    public List<WebPermissionVO> getByRoleId(Long roleId) throws Exception {
        List<WebRolePermission> rolePermissions = rolePermissionManager.getByRoleId(roleId, State.effective.name());
        Set<Long> permissionIds = rolePermissions.stream().map(WebRolePermission::getPermissionId).collect(Collectors.toSet());
        if(CollectionUtils.isNotEmpty(permissionIds)){
            return permissionManager.getByIds(permissionIds,State.effective.name()).parallelStream().map(e -> BeanUtils.copyProperties(e, WebPermissionVO.class)).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    @Override
    public List<WebPermissionVO> getByRoleIds(Set<Long> roleIds) throws Exception {
        List<WebRolePermission> rolePermissions = rolePermissionManager.getByRoleIds(roleIds, State.effective.name());
        Set<Long> permissionIds = rolePermissions.stream().map(WebRolePermission::getPermissionId).collect(Collectors.toSet());
        if(CollectionUtils.isNotEmpty(permissionIds)){
            //根据java8的map流去重，如果map的key重复了保留第一个
            return  permissionManager.getByIds(permissionIds,State.effective.name())
                    .parallelStream().collect(Collectors.toMap(WebPermission::getCode,e->e,(o1,o2)->o1)).values()//去重
                    .parallelStream().map(e -> BeanUtils.copyProperties(e, WebPermissionVO.class)).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

}
