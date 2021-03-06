package ${package.ServiceImpl};

import ${cfg.parentPackage}.${cfg.voPackage}.${entity}VO;
import ${package.Entity}.${entity};
import ${package.Mapper}.${table.mapperName};
import ${package.Service}.${table.serviceName};
import ${package.Xml}.${table.xmlName};
import ${superServiceImplClassPackage};
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
 * ${table.comment!} Service服务实现类
 *
 * @author ${author}
 * @since ${date}
 */
@Slf4j
@Service
public class ${table.serviceImplName} implements ${table.serviceName} {

    @Autowired
    private ${entity}Manager ${entity?uncap_first}Manager;

    private void convertVO(List<${entity}VO> entityList){
   
    }
   
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long add(${entity}VO entityVO) throws Exception {
        ${entity} entity = ${entity?uncap_first}Manager.get(entityVO.getId());
        if(entity!=null){
            throw new Exception("不能重复添加!");
        }
        return ${entity?uncap_first}Manager.add(BeanUtils.copyProperties(entityVO, ${entity}.class));
    }
   
    @Override
    public Integer delete(Long id) throws Exception {
        return ${entity?uncap_first}Manager.delete(id);
    }
   
    @Override
    public Integer update(${entity}VO entityVO) throws Exception {
        if(entityVO.getId()==null){
            throw new Exception("Id不能为空!");
        }
        return ${entity?uncap_first}Manager.update(BeanUtils.copyProperties(entityVO, ${entity}.class));
    }
   
    @Override
    public boolean updateState(UpdateState entityVO) throws Exception {
        for (Long id : entityVO.getIds()){
            ${entity} entity = new ${entity}();
            entity.setId(id);
            entity.setState(entityVO.getState());
            entity.setUpdateUser(entityVO.getUpdateUser());
            ${entity?uncap_first}Manager.updateState(entity);
        }
        return true;
    }
   
    @Override
    public ${entity}VO get(Long id) throws Exception {
        ${entity} entity = ${entity?uncap_first}Manager.get(id);
        if(entity==null){
            return null;
        }
        return BeanUtils.copyProperties(entity, ${entity}VO.class);
    }
   
    @Override
    public PageVO<${entity}VO> queryPage(PageQuery pageQuery) throws Exception {
        IPage<${entity}> iPage = ${entity?uncap_first}Manager.queryPage(pageQuery.getPageNum(),pageQuery.getPageSize(),pageQuery.getQuery());
        PageVO<${entity}VO> pageVO = PageConvert.pageConvert(iPage, ${entity}VO.class);
        convertVO(pageVO.getRecords());
        return pageVO;
    }
   
    @Override
    public List<${entity}VO> query(Map<String, Object> query) throws Exception {
        return ${entity?uncap_first}Manager.query(query).parallelStream().map(e -> BeanUtils.copyProperties(e, ${entity}VO.class)).collect(Collectors.toList());
    }
   
    @Override
    public List<${entity}VO> querySome(Map<String, Object> query,String[] columns) throws Exception {
        return ${entity?uncap_first}Manager.querySome(query,columns).parallelStream().map(e -> BeanUtils.copyProperties(e, ${entity}VO.class)).collect(Collectors.toList());
    }
   
    @Override
    public List<${entity}VO> queryAll(Map<String, Object> query) throws Exception {
        return ${entity?uncap_first}Manager.queryAll(query).parallelStream().map(e -> BeanUtils.copyProperties(e, ${entity}VO.class)).collect(Collectors.toList());
    }
 
}
