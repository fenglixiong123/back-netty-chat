package com.flx.netty.chat.auth.console.service.impl;

import com.flx.netty.chat.auth.crud.entity.RolePermission;
import com.flx.netty.chat.auth.crud.manager.RolePermissionManager;
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
public class RolePermissionServiceImpl implements RolePermissionService {

    @Autowired
    private RolePermissionManager rolePermissionManager;

    private void convertVO(List<RolePermissionVO> entityList){
   
    }
   
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long add(RolePermissionVO entityVO) throws Exception {
        RolePermission entity = rolePermissionManager.get(entityVO.getId());
        if(entity!=null){
            throw new Exception("不能重复添加!");
        }
        return rolePermissionManager.add(BeanUtils.copyProperties(entityVO, RolePermission.class));
    }
   
    @Override
    public Integer delete(Long id) throws Exception {
        return rolePermissionManager.delete(id);
    }
   
    @Override
    public Integer update(RolePermissionVO entityVO) throws Exception {
        if(entityVO.getId()==null){
            throw new Exception("Id不能为空!");
        }
        return rolePermissionManager.update(BeanUtils.copyProperties(entityVO, RolePermission.class));
    }
   
    @Override
    public boolean updateState(UpdateState entityVO) throws Exception {
        for (Long id : entityVO.getIds()){
            RolePermission entity = new RolePermission();
            entity.setId(id);
            entity.setState(entityVO.getState());
            entity.setUpdateUser(entityVO.getUpdateUser());
            rolePermissionManager.updateState(entity);
        }
        return true;
    }
   
    @Override
    public RolePermissionVO get(Long id) throws Exception {
        RolePermission entity = rolePermissionManager.get(id);
        if(entity==null){
            return null;
        }
        return BeanUtils.copyProperties(entity, RolePermissionVO.class);
    }
   
    @Override
    public PageVO<RolePermissionVO> queryPage(PageQuery pageQuery) throws Exception {
        IPage<RolePermission> iPage = rolePermissionManager.queryPage(pageQuery.getPageNum(),pageQuery.getPageSize(),pageQuery.getQuery());
        PageVO<RolePermissionVO> pageVO = PageConvert.pageConvert(iPage, RolePermissionVO.class);
        convertVO(pageVO.getRecords());
        return pageVO;
    }
   
    @Override
    public List<RolePermissionVO> query(Map<String, Object> query) throws Exception {
        return rolePermissionManager.query(query).parallelStream().map(e -> BeanUtils.copyProperties(e, RolePermissionVO.class)).collect(Collectors.toList());
    }
   
    @Override
    public List<RolePermissionVO> querySome(Map<String, Object> query,String[] columns) throws Exception {
        return rolePermissionManager.querySome(query,columns).parallelStream().map(e -> BeanUtils.copyProperties(e, RolePermissionVO.class)).collect(Collectors.toList());
    }
   
    @Override
    public List<RolePermissionVO> queryAll(Map<String, Object> query) throws Exception {
        return rolePermissionManager.queryAll(query).parallelStream().map(e -> BeanUtils.copyProperties(e, RolePermissionVO.class)).collect(Collectors.toList());
    }
 
}
