package com.flx.netty.chat.admin.service.impl;

import com.flx.netty.chat.admin.dao.SystemRolePermissionDao;
import com.flx.netty.chat.admin.entity.SystemRolePermission;
import com.flx.netty.chat.admin.utils.PageConvert;
import com.flx.netty.chat.admin.vo.SystemPermissionVO;
import com.flx.netty.chat.admin.entity.SystemPermission;
import com.flx.netty.chat.admin.dao.SystemPermissionDao;
import com.flx.netty.chat.admin.service.SystemPermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.flx.netty.chat.common.utils.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.flx.netty.chat.common.entity.UpdateState;
import com.flx.netty.chat.common.enums.State;
import com.flx.netty.chat.common.utils.page.PageQuery;
import com.flx.netty.chat.common.utils.page.PageVO;
import com.flx.netty.chat.common.utils.servlet.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *  服务实现类
 *
 * @author Fenglixiong
 * @since 2021-05-21
 */
@Slf4j
@Service
public class SystemPermissionServiceImpl extends ServiceImpl<SystemPermissionDao, SystemPermission> implements SystemPermissionService {

    @Autowired
    private SystemRolePermissionDao rolePermissionDao;
    @Autowired
    private SystemPermissionDao permissionDao;

    public boolean add(SystemPermissionVO entityVO) throws Exception{
        return super.save(BeanUtils.copyProperties(entityVO, SystemPermission.class));
    }
    
    public boolean delete(Long id){
        return super.removeById(id);
    }
    
    public boolean update(SystemPermissionVO entityVO) throws Exception{
        if(entityVO.getId()==null){
            throw new Exception("Id不能为空！");
        }
        return super.updateById(BeanUtils.copyProperties(entityVO, SystemPermission.class));
    }
    
    @Override
    public boolean updateState(UpdateState entityVO) throws Exception {
        for (Long id:entityVO.getIds()){
            if(entityVO.getState().equals(State.deleted.name())){
                super.removeById(id);
            }else {
                SystemPermission entity = new SystemPermission();
                entity.setId(id);
                entity.setState(entity.getState());
                entity.setUpdateUser(entityVO.getUpdateUser());
                super.updateById(entity);
            }
        }
        return true;
    }
    
    public SystemPermissionVO get(Long id) throws Exception{
        return BeanUtils.copyProperties(super.getById(id),SystemPermissionVO.class);
    }
    
    public PageVO<SystemPermissionVO> queryPage(PageQuery pageQuery) throws Exception{
        Page<SystemPermission> page = super.page(new Page<>(pageQuery.getPageNum(), pageQuery.getPageSize()));
        return PageConvert.pageConvert(page,SystemPermissionVO.class);
    }

   public List<SystemPermissionVO> query(Map<String,Object> columnMap) throws Exception{
       return super.listByMap(columnMap).parallelStream().map(e->BeanUtils.copyProperties(e,SystemPermissionVO.class)).collect(Collectors.toList());
   }

   public List<SystemPermissionVO> queryAll() throws Exception{
       return super.list().parallelStream().map(e->BeanUtils.copyProperties(e,SystemPermissionVO.class)).collect(Collectors.toList());
   }

    @Override
    public List<SystemPermissionVO> getByRoleId(Long roleId) throws Exception {
        List<SystemRolePermission> rolePermissions = rolePermissionDao.getByRoleId(roleId, State.effective.name());
        Set<Long> permissionIds = rolePermissions.stream().map(SystemRolePermission::getPermissionId).collect(Collectors.toSet());
        if(CollectionUtils.isNotEmpty(permissionIds)){
            return permissionDao.getByIds(permissionIds,State.effective.name()).parallelStream().map(e -> BeanUtils.copyProperties(e, SystemPermissionVO.class)).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    @Override
    public List<SystemPermissionVO> getByRoleIds(Set<Long> roleIds) throws Exception {
        List<SystemRolePermission> rolePermissions = rolePermissionDao.getByRoleIds(roleIds, State.effective.name());
        Set<Long> permissionIds = rolePermissions.stream().map(SystemRolePermission::getPermissionId).collect(Collectors.toSet());
        if(CollectionUtils.isNotEmpty(permissionIds)){
            //根据java8的map流去重，如果map的key重复了保留第一个
            return  permissionDao.getByIds(permissionIds,State.effective.name())
                    .parallelStream().collect(Collectors.toMap(SystemPermission::getCode, e->e,(o1, o2)->o1)).values()//去重
                    .parallelStream().map(e -> BeanUtils.copyProperties(e, SystemPermissionVO.class)).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

}
