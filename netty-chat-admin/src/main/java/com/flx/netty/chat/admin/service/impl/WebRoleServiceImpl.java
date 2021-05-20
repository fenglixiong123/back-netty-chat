package com.flx.netty.chat.admin.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.flx.netty.chat.admin.utils.PageConvert;
import com.flx.netty.chat.admin.vo.WebRoleVO;
import com.flx.netty.chat.admin.entity.WebRole;
import com.flx.netty.chat.admin.dao.WebRoleDao;
import com.flx.netty.chat.admin.service.WebRoleService;
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
public class WebRoleServiceImpl extends ServiceImpl<WebRoleDao, WebRole> implements WebRoleService {

    public boolean add(WebRoleVO entityVO) throws Exception{
        return super.save(BeanUtils.copyProperties(entityVO, WebRole.class));
    }
    
    public boolean delete(Long id){
        return super.removeById(id);
    }
    
    public boolean update(WebRoleVO entityVO) throws Exception{
        if(entityVO.getId()==null){
            throw new Exception("Id不能为空！");
        }
        return super.updateById(BeanUtils.copyProperties(entityVO, WebRole.class));
    }
    
    @Override
    public boolean updateState(UpdateState entityVO) throws Exception {
        for (Long id:entityVO.getIds()){
            if(entityVO.getState().equals(State.deleted.name())){
                super.removeById(id);
            }else {
                WebRole entity = new WebRole();
                entity.setId(id);
                entity.setState(entity.getState());
                entity.setUpdateUser(entityVO.getUpdateUser());
                super.updateById(entity);
            }
        }
        return true;
    }
    
    public WebRoleVO get(Long id) throws Exception{
        return BeanUtils.copyProperties(super.getById(id),WebRoleVO.class);
    }
    
    public PageVO<WebRoleVO> queryPage(PageQuery pageQuery) throws Exception{
        Page<WebRole> page = super.page(new Page<>(pageQuery.getPageNum(), pageQuery.getPageSize()));
        return PageConvert.pageConvert(page,WebRoleVO.class);
    }

   public List<WebRoleVO> query(Map<String,Object> columnMap) throws Exception{
       return super.listByMap(columnMap).parallelStream().map(e->BeanUtils.copyProperties(e,WebRoleVO.class)).collect(Collectors.toList());
   }

   public List<WebRoleVO> queryAll() throws Exception{
       return super.list().parallelStream().map(e->BeanUtils.copyProperties(e,WebRoleVO.class)).collect(Collectors.toList());
   }

}
