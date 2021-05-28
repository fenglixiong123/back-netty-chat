package ${package.ServiceImpl};

import ${cfg.parentPackage}.vo.${entity}VO;
import ${package.Entity}.${entity};
import ${package.Mapper}.${table.mapperName};
import ${package.Service}.${table.serviceName};
import ${superServiceImplClassPackage};
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
 * ${table.comment!} 服务实现类
 *
 * @author ${author}
 * @since ${date}
 */
@Slf4j
@Service
public class ${table.serviceImplName} extends ${superServiceImplClass}<${table.mapperName}, ${entity}> implements ${table.serviceName} {

    @Override
    public boolean add(${entity}VO entityVO) throws Exception{
        return super.save(BeanUtils.copyProperties(entityVO, ${entity}.class));
    }

    @Override
    public boolean delete(Long id){
        return super.removeById(id);
    }

    @Override
    public boolean update(${entity}VO entityVO) throws Exception{
        if(entityVO.getId()==null){
            throw new Exception("Id不能为空！");
        }
        return super.updateById(BeanUtils.copyProperties(entityVO, ${entity}.class));
    }
    
    @Override
    public boolean updateState(UpdateState entityVO) throws Exception {
        for (Long id:entityVO.getIds()){
            if(entityVO.getState().equals(State.deleted.name())){
                super.removeById(id);
            }else {
                ${entity} entity = new ${entity}();
                entity.setId(id);
                entity.setState(entity.getState());
                entity.setUpdateUser(entityVO.getUpdateUser());
                super.updateById(entity);
            }
        }
        return true;
    }

    @Override
    public ${entity}VO get(Long id) throws Exception{
        return BeanUtils.copyProperties(super.getById(id),${entity}VO.class);
    }

    @Override
    public PageVO<${entity}VO> queryPage(PageQuery pageQuery) throws Exception{
        Page<${entity}> page = super.page(new Page<>(pageQuery.getPageNum(), pageQuery.getPageSize()));
        return PageConvert.pageConvert(page,${entity}VO.class);
    }

    @Override
    public List<${entity}VO> query(Map<String,Object> columnMap) throws Exception{
        return super.listByMap(columnMap).parallelStream().map(e->BeanUtils.copyProperties(e,${entity}VO.class)).collect(Collectors.toList());
    }

    @Override
    public List<${entity}VO> queryAll() throws Exception{
        return super.list().parallelStream().map(e->BeanUtils.copyProperties(e,${entity}VO.class)).collect(Collectors.toList());
    }

}
