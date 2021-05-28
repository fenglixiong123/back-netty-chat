package com.flx.netty.chat.admin.service.impl;

import com.flx.netty.chat.admin.dao.*;
import com.flx.netty.chat.admin.entity.*;
import com.flx.netty.chat.admin.service.SystemPermissionService;
import com.flx.netty.chat.admin.utils.PageConvert;
import com.flx.netty.chat.admin.vo.SystemMenuVO;
import com.flx.netty.chat.admin.vo.SystemPermissionVO;
import com.flx.netty.chat.admin.vo.SystemRoleVO;
import com.flx.netty.chat.admin.service.SystemRoleService;
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
public class SystemRoleServiceImpl extends ServiceImpl<SystemRoleDao, SystemRole> implements SystemRoleService {

    @Autowired
    private SystemRoleDao roleDao;
    @Autowired
    private SystemMenuDao menuDao;
    @Autowired
    private SystemRoleMenuDao roleMenuDao;
    @Autowired
    private SystemPermissionDao permissionDao;
    @Autowired
    private SystemRolePermissionDao rolePermissionDao;

    @Override
    public boolean add(SystemRoleVO entityVO) throws Exception{
        return super.save(BeanUtils.copyProperties(entityVO, SystemRole.class));
    }

    @Override
    public boolean delete(Long id){
        return super.removeById(id);
    }
    
    public boolean update(SystemRoleVO entityVO) throws Exception{
        if(entityVO.getId()==null){
            throw new Exception("Id不能为空！");
        }
        return super.updateById(BeanUtils.copyProperties(entityVO, SystemRole.class));
    }
    
    @Override
    public boolean updateState(UpdateState entityVO) throws Exception {
        for (Long id:entityVO.getIds()){
            if(entityVO.getState().equals(State.deleted.name())){
                super.removeById(id);
            }else {
                SystemRole entity = new SystemRole();
                entity.setId(id);
                entity.setState(entity.getState());
                entity.setUpdateUser(entityVO.getUpdateUser());
                super.updateById(entity);
            }
        }
        return true;
    }

    @Override
    public SystemRoleVO get(Long id) throws Exception{
        return BeanUtils.copyProperties(super.getById(id),SystemRoleVO.class);
    }

    @Override
    public PageVO<SystemRoleVO> queryPage(PageQuery pageQuery) throws Exception{
        Page<SystemRole> page = super.page(new Page<>(pageQuery.getPageNum(), pageQuery.getPageSize()));
        return PageConvert.pageConvert(page,SystemRoleVO.class);
    }

    @Override
    public List<SystemRoleVO> query(Map<String,Object> columnMap) throws Exception{
        return super.listByMap(columnMap).parallelStream().map(e->BeanUtils.copyProperties(e,SystemRoleVO.class)).collect(Collectors.toList());
    }

    @Override
    public List<SystemRoleVO> queryAll() throws Exception{
        return super.list().parallelStream().map(e->BeanUtils.copyProperties(e,SystemRoleVO.class)).collect(Collectors.toList());
    }

    @Override
    public List<SystemRoleVO> getRoleByIds(Set<Long> ids) throws Exception {
        return roleDao.getByIds(ids,State.effective.name()).parallelStream().map(e->BeanUtils.copyProperties(e,SystemRoleVO.class)).collect(Collectors.toList());
    }

    @Override
    public List<SystemPermissionVO> getPermissionById(Long id) throws Exception {
        List<SystemRolePermission> rolePermissions = rolePermissionDao.getByRoleId(id, State.effective.name());
        Set<Long> permissionIds = rolePermissions.parallelStream().map(SystemRolePermission::getPermissionId).collect(Collectors.toSet());
        if(CollectionUtils.isNotEmpty(permissionIds)){
            return permissionDao.getByIds(permissionIds,State.effective.name()).parallelStream().map(e -> BeanUtils.copyProperties(e, SystemPermissionVO.class)).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    @Override
    public List<SystemPermissionVO> getPermissionByIds(Set<Long> ids) throws Exception {
        if(CollectionUtils.isEmpty(ids)){
            return new ArrayList<>();
        }
        List<SystemRolePermission> rolePermissions = rolePermissionDao.getByRoleIds(ids, State.effective.name());
        Set<Long> permissionIds = rolePermissions.stream().map(SystemRolePermission::getPermissionId).collect(Collectors.toSet());
        if(CollectionUtils.isNotEmpty(permissionIds)){
            //根据java8的map流去重，如果map的key重复了保留第一个
            return  permissionDao.getByIds(permissionIds,State.effective.name())
                    .parallelStream().collect(Collectors.toMap(SystemPermission::getCode, e->e,(o1, o2)->o1)).values()//去重
                    .parallelStream().map(e -> BeanUtils.copyProperties(e, SystemPermissionVO.class)).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    @Override
    public List<SystemMenuVO> getMenuById(Long id) throws Exception {
        List<SystemRoleMenu> roleMenus = roleMenuDao.getByRoleId(id, State.effective.name());
        Set<Long> menuIds = roleMenus.stream().map(SystemRoleMenu::getMenuId).collect(Collectors.toSet());
        if(CollectionUtils.isNotEmpty(menuIds)){
            return menuDao.getByIds(menuIds,State.effective.name()).parallelStream().map(e -> BeanUtils.copyProperties(e, SystemMenuVO.class)).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    @Override
    public List<SystemMenuVO> getMenuByIds(Set<Long> ids) throws Exception {
        if(CollectionUtils.isEmpty(ids)){
            return new ArrayList<>();
        }
        List<SystemRoleMenu> roleMenus = roleMenuDao.getByRoleIds(ids, State.effective.name());
        Set<Long> menuIds = roleMenus.stream().map(SystemRoleMenu::getMenuId).collect(Collectors.toSet());
        if(CollectionUtils.isNotEmpty(menuIds)){
            //根据java8的map流去重，如果map的key重复了保留第一个
            return  menuDao.getByIds(menuIds,State.effective.name())
                    .parallelStream().collect(Collectors.toMap(SystemMenu::getCode, e->e,(o1, o2)->o1)).values()//去重
                    .parallelStream().map(e -> BeanUtils.copyProperties(e, SystemMenuVO.class)).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

}
