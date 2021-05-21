package com.flx.netty.chat.admin.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.flx.netty.chat.admin.utils.PageConvert;
import com.flx.netty.chat.admin.vo.AuthClientVO;
import com.flx.netty.chat.admin.entity.AuthClient;
import com.flx.netty.chat.admin.dao.AuthClientDao;
import com.flx.netty.chat.admin.service.AuthClientService;
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
 * 客户端信息表 服务实现类
 *
 * @author Fenglixiong
 * @since 2021-05-19
 */
@Slf4j
@Service
@DS("auth")
public class AuthClientServiceImpl extends ServiceImpl<AuthClientDao, AuthClient> implements AuthClientService {

    public boolean add(AuthClientVO entityVO) throws Exception{
        return super.save(BeanUtils.copyProperties(entityVO, AuthClient.class));
    }
    
    public boolean delete(Long id){
        return super.removeById(id);
    }
    
    public boolean update(AuthClientVO entityVO) throws Exception{
        if(entityVO.getId()==null){
            throw new Exception("Id不能为空！");
        }
        return super.updateById(BeanUtils.copyProperties(entityVO, AuthClient.class));
    }
    
    @Override
    public boolean updateState(UpdateState entityVO) throws Exception {
        for (Long id:entityVO.getIds()){
            if(entityVO.getState().equals(State.deleted.name())){
                super.removeById(id);
            }else {
                AuthClient entity = new AuthClient();
                entity.setId(id);
                entity.setState(entity.getState());
                entity.setUpdateUser(entityVO.getUpdateUser());
                super.updateById(entity);
            }
        }
        return true;
    }
    
    public AuthClientVO get(Long id) throws Exception{
        return BeanUtils.copyProperties(super.getById(id), AuthClientVO.class);
    }
    
    public PageVO<AuthClientVO> queryPage(PageQuery pageQuery) throws Exception{
        Page<AuthClient> page = super.page(new Page<>(pageQuery.getPageNum(), pageQuery.getPageSize()));
        return PageConvert.pageConvert(page, AuthClientVO.class);
    }

   public List<AuthClientVO> query(Map<String,Object> columnMap) throws Exception{
       return super.listByMap(columnMap).parallelStream().map(e->BeanUtils.copyProperties(e, AuthClientVO.class)).collect(Collectors.toList());
   }

   public List<AuthClientVO> queryAll() throws Exception{
       return super.list().parallelStream().map(e->BeanUtils.copyProperties(e, AuthClientVO.class)).collect(Collectors.toList());
   }

    @Override
    public AuthClient getByClientId(String clientId) throws Exception {
        QueryWrapper<AuthClient> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(AuthClient.CLIENT_ID,clientId)
                .eq(AuthClient.STATE,State.effective.name());
        return super.getOne(queryWrapper);
    }

}
