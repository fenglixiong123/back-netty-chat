package com.flx.netty.chat.auth.console.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.flx.netty.chat.auth.api.vo.AuthPermissionVO;
import com.flx.netty.chat.auth.api.vo.AuthUserVO;
import com.flx.netty.chat.auth.console.service.AuthPermissionService;
import com.flx.netty.chat.auth.console.service.AuthRoleService;
import com.flx.netty.chat.auth.crud.entity.AuthUserRole;
import com.flx.netty.chat.auth.crud.manager.AuthUserManager;
import com.flx.netty.chat.auth.crud.entity.AuthUser;
import com.flx.netty.chat.auth.console.service.AuthUserService;
import com.flx.netty.chat.auth.crud.manager.AuthUserRoleManager;
import com.flx.netty.chat.common.entity.UpdateState;
import com.flx.netty.chat.common.enums.State;
import com.flx.netty.chat.common.utils.CollectionUtils;
import com.flx.netty.chat.common.utils.page.PageQuery;
import com.flx.netty.chat.common.utils.page.PageVO;
import com.flx.netty.chat.common.utils.servlet.BeanUtils;
import com.flx.netty.chat.plugin.plugins.mybatis.page.PageConvert;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author: Fenglixiong
 * @Date: 2020/7/24 16:34
 * @Description:
 */
@Slf4j
@Service
public class AuthUserServiceImpl implements AuthUserService {

    @Autowired
    private AuthUserManager userManager;
    @Autowired
    private AuthRoleService roleService;
    @Autowired
    private AuthPermissionService permissionService;
    @Autowired
    private AuthUserRoleManager userRoleManager;

    private void convertVO(List<AuthUserVO> entityList){

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long add(AuthUserVO entityVO) throws Exception {
        AuthUserVO entity = getByUsername(entityVO.getUserName());
        if(entity!=null){
            throw new Exception("不能重复添加!");
        }
        return userManager.add(BeanUtils.copyProperties(entityVO, AuthUser.class));
    }

    @Override
    public Integer delete(Long id) throws Exception {
        return userManager.delete(id);
    }

    @Override
    public Integer update(AuthUserVO entityVO) throws Exception {
        if(entityVO.getId()==null){
            throw new Exception("Id不能为空!");
        }
        return userManager.update(BeanUtils.copyProperties(entityVO, AuthUser.class));
    }

    @Override
    public boolean updateState(UpdateState entityVO) throws Exception {
        for (Long id : entityVO.getIds()){
            AuthUser entity = new AuthUser();
            entity.setId(id);
            entity.setState(entityVO.getState());
            entity.setUpdateUser(entityVO.getUpdateUser());
            userManager.updateState(entity);
        }
        return true;
    }

    @Override
    public AuthUserVO get(Long id) throws Exception {
        AuthUser entity = userManager.get(id);
        if(entity==null){
            return null;
        }
        return BeanUtils.copyProperties(entity, AuthUserVO.class);
    }

    @Override
    public PageVO<AuthUserVO> queryPage(PageQuery pageQuery) throws Exception {
        IPage<AuthUser> iPage = userManager.queryPage(pageQuery.getPageNum(),pageQuery.getPageSize(),pageQuery.getQuery());
        PageVO<AuthUserVO> pageVO = PageConvert.pageConvert(iPage, AuthUserVO.class);
        convertVO(pageVO.getRecords());
        return pageVO;
    }

    @Override
    public List<AuthUserVO> query(Map<String, Object> query) throws Exception {
        return userManager.query(query).parallelStream().map(e -> BeanUtils.copyProperties(e, AuthUserVO.class)).collect(Collectors.toList());
    }

    @Override
    public List<AuthUserVO> querySome(Map<String, Object> query, String[] columns) throws Exception {
        return userManager.querySome(query,columns).parallelStream().map(e -> BeanUtils.copyProperties(e, AuthUserVO.class)).collect(Collectors.toList());
    }

    @Override
    public List<AuthUserVO> queryAll(Map<String, Object> query) throws Exception {
        return userManager.queryAll(query).parallelStream().map(e -> BeanUtils.copyProperties(e, AuthUserVO.class)).collect(Collectors.toList());
    }

    @Override
    public AuthUserVO getByUsername(String username) throws Exception{
        return BeanUtils.copyProperties(userManager.get("userName", username), AuthUserVO.class);
    }

    @Override
    public boolean isExist(String username) throws Exception{
        return userManager.isExist("userName",username);
    }

    @Override
    public AuthUserVO validateUser(String username, String password) throws Exception {
        if(StringUtils.isBlank(username)){
            throw new Exception("用户名不能为空！");
        }
        if(StringUtils.isBlank(password)){
            throw new Exception("密码不能为空！");
        }
        AuthUser user = userManager.get("userName", username);;
        if(user==null){
            throw new Exception("用户名不存在！");
        }
        if(!user.getPassword().equals(password)){
            throw new Exception("用户密码错误！");
        }
        return BeanUtils.copyProperties(user, AuthUserVO.class);
    }

    @Override
    public List<AuthPermissionVO> getPermissionById(Long id) throws Exception {
        Set<Long> roleIds = userRoleManager.getByUserId(id, State.effective.name()).stream().map(AuthUserRole::getRoleId).collect(Collectors.toSet());
        if(CollectionUtils.isEmpty(roleIds)){
            return new ArrayList<>();
        }
        return permissionService.getByRoleIds(roleIds);
    }


}
