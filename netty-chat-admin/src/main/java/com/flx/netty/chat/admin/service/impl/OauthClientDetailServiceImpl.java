package com.flx.netty.chat.admin.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.flx.netty.chat.admin.utils.PageConvert;
import com.flx.netty.chat.admin.vo.OauthClientDetailVO;
import com.flx.netty.chat.admin.entity.OauthClientDetail;
import com.flx.netty.chat.admin.dao.OauthClientDetailDao;
import com.flx.netty.chat.admin.service.OauthClientDetailService;
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
public class OauthClientDetailServiceImpl extends ServiceImpl<OauthClientDetailDao, OauthClientDetail> implements OauthClientDetailService {

    public boolean add(OauthClientDetailVO entityVO) throws Exception{
        return super.save(BeanUtils.copyProperties(entityVO, OauthClientDetail.class));
    }
    
    public boolean delete(Long id){
        return super.removeById(id);
    }
    
    public boolean update(OauthClientDetailVO entityVO) throws Exception{
        if(entityVO.getId()==null){
            throw new Exception("Id不能为空！");
        }
        return super.updateById(BeanUtils.copyProperties(entityVO, OauthClientDetail.class));
    }
    
    @Override
    public boolean updateState(UpdateState entityVO) throws Exception {
        for (Long id:entityVO.getIds()){
            if(entityVO.getState().equals(State.deleted.name())){
                super.removeById(id);
            }else {
                OauthClientDetail entity = new OauthClientDetail();
                entity.setId(id);
                entity.setState(entity.getState());
                entity.setUpdateUser(entityVO.getUpdateUser());
                super.updateById(entity);
            }
        }
        return true;
    }
    
    public OauthClientDetailVO get(Long id) throws Exception{
        return BeanUtils.copyProperties(super.getById(id),OauthClientDetailVO.class);
    }
    
    public PageVO<OauthClientDetailVO> queryPage(PageQuery pageQuery) throws Exception{
        Page<OauthClientDetail> page = super.page(new Page<>(pageQuery.getPageNum(), pageQuery.getPageSize()));
        return PageConvert.pageConvert(page,OauthClientDetailVO.class);
    }

   public List<OauthClientDetailVO> query(Map<String,Object> columnMap) throws Exception{
       return super.listByMap(columnMap).parallelStream().map(e->BeanUtils.copyProperties(e,OauthClientDetailVO.class)).collect(Collectors.toList());
   }

   public List<OauthClientDetailVO> queryAll() throws Exception{
       return super.list().parallelStream().map(e->BeanUtils.copyProperties(e,OauthClientDetailVO.class)).collect(Collectors.toList());
   }

    @Override
    public OauthClientDetail getByClientId(String clientId) throws Exception {
        QueryWrapper<OauthClientDetail> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(OauthClientDetail.CLIENT_ID,clientId)
                .eq(OauthClientDetail.STATE,State.effective.name());
        return super.getOne(queryWrapper);
    }

}
