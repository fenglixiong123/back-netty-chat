package com.flx.netty.chat.admin.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.flx.netty.chat.admin.vo.WebRolePermissionVO;
import com.flx.netty.chat.admin.entity.WebRolePermission;
import com.flx.netty.chat.admin.dao.WebRolePermissionDao;
import com.flx.netty.chat.admin.service.WebRolePermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.flx.netty.chat.common.entity.UpdateState;
import com.flx.netty.chat.common.enums.State;
import com.flx.netty.chat.common.utils.page.PageQuery;
import com.flx.netty.chat.common.utils.page.PageVO;
import com.flx.netty.chat.common.utils.servlet.BeanUtils;
import com.flx.netty.chat.plugin.plugins.mybatis.page.PageConvert;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *  服务实现类
 *
 * @author Fenglixiong
 * @since 2021-05-19
 */
@Slf4j
@Service
@DS("auth")
public class WebRolePermissionServiceImpl extends ServiceImpl<WebRolePermissionDao, WebRolePermission> implements WebRolePermissionService {

    public boolean add(WebRolePermissionVO entityVO) throws Exception{
        return super.save(BeanUtils.copyProperties(entityVO, WebRolePermission.class));
    }
    
    public boolean delete(Long id){
        return super.removeById(id);
    }
    
    public boolean update(WebRolePermissionVO entityVO) throws Exception{
        if(entityVO.getId()==null){
            throw new Exception("Id不能为空！");
        }
        return super.updateById(BeanUtils.copyProperties(entityVO, WebRolePermission.class));
    }
    
    @Override
    public boolean updateState(UpdateState entityVO) throws Exception {
        for (Long id:entityVO.getIds()){
            if(entityVO.getState().equals(State.deleted.name())){
                super.removeById(id);
            }else {
                WebRolePermission entity = new WebRolePermission();
                entity.setId(id);
                entity.setState(entity.getState());
                entity.setUpdateUser(entityVO.getUpdateUser());
                super.updateById(entity);
            }
        }
        return true;
    }
    
    public WebRolePermissionVO get(Long id) throws Exception{
        return BeanUtils.copyProperties(super.getById(id),WebRolePermissionVO.class);
    }
    
    public PageVO<WebRolePermissionVO> queryPage(PageQuery pageQuery) throws Exception{
        Page<WebRolePermission> page = super.page(new Page<>(pageQuery.getPageNum(), pageQuery.getPageSize()));
        return PageConvert.pageConvert(page,WebRolePermissionVO.class);
    }

   public List<WebRolePermissionVO> query(Map<String,Object> columnMap) throws Exception{
       return super.listByMap(columnMap).parallelStream().map(e->BeanUtils.copyProperties(e,WebRolePermissionVO.class)).collect(Collectors.toList());
   }

   public List<WebRolePermissionVO> queryAll() throws Exception{
       return super.list().parallelStream().map(e->BeanUtils.copyProperties(e,WebRolePermissionVO.class)).collect(Collectors.toList());
   }

}
