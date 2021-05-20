package com.flx.netty.chat.admin.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.flx.netty.chat.admin.utils.PageConvert;
import com.flx.netty.chat.admin.vo.WebPermissionVO;
import com.flx.netty.chat.admin.entity.WebPermission;
import com.flx.netty.chat.admin.dao.WebPermissionDao;
import com.flx.netty.chat.admin.service.WebPermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.flx.netty.chat.common.entity.UpdateState;
import com.flx.netty.chat.common.enums.State;
import com.flx.netty.chat.common.utils.page.PageQuery;
import com.flx.netty.chat.common.utils.page.PageVO;
import com.flx.netty.chat.common.utils.servlet.BeanUtils;

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
public class WebPermissionServiceImpl extends ServiceImpl<WebPermissionDao, WebPermission> implements WebPermissionService {

    public boolean add(WebPermissionVO entityVO) throws Exception{
        return super.save(BeanUtils.copyProperties(entityVO, WebPermission.class));
    }
    
    public boolean delete(Long id){
        return super.removeById(id);
    }
    
    public boolean update(WebPermissionVO entityVO) throws Exception{
        if(entityVO.getId()==null){
            throw new Exception("Id不能为空！");
        }
        return super.updateById(BeanUtils.copyProperties(entityVO, WebPermission.class));
    }
    
    @Override
    public boolean updateState(UpdateState entityVO) throws Exception {
        for (Long id:entityVO.getIds()){
            if(entityVO.getState().equals(State.deleted.name())){
                super.removeById(id);
            }else {
                WebPermission entity = new WebPermission();
                entity.setId(id);
                entity.setState(entity.getState());
                entity.setUpdateUser(entityVO.getUpdateUser());
                super.updateById(entity);
            }
        }
        return true;
    }
    
    public WebPermissionVO get(Long id) throws Exception{
        return BeanUtils.copyProperties(super.getById(id),WebPermissionVO.class);
    }
    
    public PageVO<WebPermissionVO> queryPage(PageQuery pageQuery) throws Exception{
        Page<WebPermission> page = super.page(new Page<>(pageQuery.getPageNum(), pageQuery.getPageSize()));
        return PageConvert.pageConvert(page,WebPermissionVO.class);
    }

   public List<WebPermissionVO> query(Map<String,Object> columnMap) throws Exception{
       return super.listByMap(columnMap).parallelStream().map(e->BeanUtils.copyProperties(e,WebPermissionVO.class)).collect(Collectors.toList());
   }

   public List<WebPermissionVO> queryAll() throws Exception{
       return super.list().parallelStream().map(e->BeanUtils.copyProperties(e,WebPermissionVO.class)).collect(Collectors.toList());
   }

}
