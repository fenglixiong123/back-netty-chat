package ${package.ServiceImpl};

import ${cfg.parentPackage}.api.vo.${entity}VO;
import ${package.Entity}.${entity};
import ${package.Mapper}.${table.mapperName};
import ${package.Service}.${table.serviceName};
import ${superServiceImplClassPackage};
import org.springframework.stereotype.Service;

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
 * ${table.comment!} 服务实现类
 *
 * @author ${author}
 * @since ${date}
 */
@Slf4j
@Service
public class ${table.serviceImplName} extends ${superServiceImplClass}<${entity}, ${table.mapperName}> implements ${table.serviceName} {

    private void convertVO(List<${entity}VO> entityList){
   
    }
   
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long add(${entity}VO entityVO) throws Exception {
        return super.add(BeanUtils.copyProperties(entityVO, ${entity}.class));
    }
   
    @Override
    public Integer delete(Long id) throws Exception {
        return super.delete(id);
    }
   
    @Override
    public Integer update(${entity}VO entityVO) throws Exception {
        if(entityVO.getId()==null){
            throw new Exception("Id不能为空!");
        }
        return super.update(BeanUtils.copyProperties(entityVO, ${entity}.class));
    }
   
    @Override
    public boolean updateState(UpdateState entityVO) throws Exception {
        for (Long id : entityVO.getIds()){
            ${entity} entity = new ${entity}();
            entity.setId(id);
            entity.setState(entityVO.getState());
            entity.setUpdateUser(entityVO.getUpdateUser());
            if(entity.getState().equals(State.deleted.name())){
                super.delete(entity.getId());
            }else {
                super.update(entity);
            }
        }
        return true;
    }
   
    @Override
    public ${entity}VO get(Long id) throws Exception {
        ${entity} entity = super.get(id);
        if(entity==null){
            return null;
        }
        return BeanUtils.copyProperties(entity, ${entity}VO.class);
    }
   
    @Override
    public PageVO<${entity}VO> queryPage(PageQuery pageQuery) throws Exception {
        IPage<${entity}> iPage = super.queryPage(pageQuery.getPageNum(),pageQuery.getPageSize(),pageQuery.getQuery());
        PageVO<${entity}VO> pageVO = PageConvert.pageConvert(iPage, ${entity}VO.class);
        convertVO(pageVO.getRecords());
        return pageVO;
    }
   
    @Override
    public List<${entity}VO> query(Map<String, Object> query) throws Exception {
        return super.query(query).parallelStream().map(e -> BeanUtils.copyProperties(e, ${entity}VO.class)).collect(Collectors.toList());
    }
   
    @Override
    public List<${entity}VO> querySome(Map<String, Object> query,String[] columns) throws Exception {
        return super.querySome(query,columns).parallelStream().map(e -> BeanUtils.copyProperties(e, ${entity}VO.class)).collect(Collectors.toList());
    }
   
    @Override
    public List<${entity}VO> queryAll(Map<String, Object> query) throws Exception {
        return super.queryAll(query).parallelStream().map(e -> BeanUtils.copyProperties(e, ${entity}VO.class)).collect(Collectors.toList());
    }
 
}
