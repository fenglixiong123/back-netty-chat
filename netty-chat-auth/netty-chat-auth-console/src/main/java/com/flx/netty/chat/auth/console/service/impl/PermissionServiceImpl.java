package com.flx.netty.chat.auth.console.service.impl;

import com.flx.netty.chat.auth.api.vo.PermissionVO;
import com.flx.netty.chat.auth.crud.entity.Permission;
import com.flx.netty.chat.auth.crud.dao.PermissionDao;
import com.flx.netty.chat.auth.console.service.PermissionService;
import com.flx.netty.chat.auth.crud.manager.PermissionManager;
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

    private void convertVO(List<PermissionVO> entityList){
   
    }
   
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long add(PermissionVO entityVO) throws Exception {
        Permission entity = permissionManager.get(entityVO.getId());
        if(entity!=null){
            throw new Exception("不能重复添加!");
        }
        return permissionManager.add(BeanUtils.copyProperties(entityVO, Permission.class));
    }
   
    @Override
    public Integer delete(Long id) throws Exception {
        return permissionManager.delete(id);
    }
   
    @Override
    public Integer update(PermissionVO entityVO) throws Exception {
        if(entityVO.getId()==null){
            throw new Exception("Id不能为空!");
        }
        return permissionManager.update(BeanUtils.copyProperties(entityVO, Permission.class));
    }
   
    @Override
    public boolean updateState(UpdateState entityVO) throws Exception {
        for (Long id : entityVO.getIds()){
            Permission entity = new Permission();
            entity.setId(id);
            entity.setState(entityVO.getState());
            entity.setUpdateUser(entityVO.getUpdateUser());
            permissionManager.updateState(entity);
        }
        return true;
    }
   
    @Override
    public PermissionVO get(Long id) throws Exception {
        Permission entity = permissionManager.get(id);
        if(entity==null){
            return null;
        }
        return BeanUtils.copyProperties(entity, PermissionVO.class);
    }
   
    @Override
    public PageVO<PermissionVO> queryPage(PageQuery pageQuery) throws Exception {
        IPage<Permission> iPage = permissionManager.queryPage(pageQuery.getPageNum(),pageQuery.getPageSize(),pageQuery.getQuery());
        PageVO<PermissionVO> pageVO = PageConvert.pageConvert(iPage, PermissionVO.class);
        convertVO(pageVO.getRecords());
        return pageVO;
    }
   
    @Override
    public List<PermissionVO> query(Map<String, Object> query) throws Exception {
        return permissionManager.query(query).parallelStream().map(e -> BeanUtils.copyProperties(e, PermissionVO.class)).collect(Collectors.toList());
    }
   
    @Override
    public List<PermissionVO> querySome(Map<String, Object> query,String[] columns) throws Exception {
        return permissionManager.querySome(query,columns).parallelStream().map(e -> BeanUtils.copyProperties(e, PermissionVO.class)).collect(Collectors.toList());
    }
   
    @Override
    public List<PermissionVO> queryAll(Map<String, Object> query) throws Exception {
        return permissionManager.queryAll(query).parallelStream().map(e -> BeanUtils.copyProperties(e, PermissionVO.class)).collect(Collectors.toList());
    }
 
}
