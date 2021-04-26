package ${package.Xml};

import ${package.Entity}.${entity};
import ${package.Mapper}.${table.mapperName};
import org.springframework.stereotype.Service;
import com.flx.netty.chat.plugin.plugins.mybatis.base.BaseManager;
import com.flx.netty.chat.common.enums.State;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
import java.util.Map;

/**
 * ${table.comment!} Manager层操作类
 *
 * @author ${author}
 * @since ${date}
 */
@Slf4j
@Service
public class ${table.xmlName} extends BaseManager<${entity}, ${table.mapperName}> {

    public ${entity} get(Long id) throws Exception {
        return super.get(id);
    }
    
    public IPage<${entity}> queryPage(Integer pageNum, Integer pageSize, Map<String, Object> query) throws Exception {
        return super.queryPage(pageNum, pageSize, query);
    }
    
    
    public IPage<${entity}> queryPage(Integer pageNum, Integer pageSize, Object query) throws Exception {
        return super.queryPage(pageNum, pageSize, query); 
    }
    
    public List<${entity}> query(Object query) throws Exception { 
        return super.query(query); 
    }
    
    public List<${entity}> query(Map<String, Object> query) throws Exception {
        return super.query(query);
    }
    
    public List<${entity}> querySome(Object query,String[] columns) throws Exception {
        return super.querySome(query,columns);
    }
    
    public List<${entity}> querySome(Map<String, Object> query,String[] columns) throws Exception {
        return super.querySome(query,columns);
    }
    
    public List<${entity}> queryAll(Object query) throws Exception {
        return super.queryAll(query);
    }
    
    public List<${entity}> queryAll(Map<String, Object> query) throws Exception {
        return super.queryAll(query);
    }
    
    public Long add(${entity} entity) throws Exception {
        return super.add(entity);
    }
    
    public Integer add(List<${entity}> entityList) throws Exception {
        return super.add(entityList);
    }
    
    public Integer update(${entity} entity) throws Exception {
        return super.update(entity);
    }
    
    public Integer updateNull(${entity} entity) throws Exception {
        return super.updateNull(entity);
    }
    
    public Integer update(${entity} entity,String keyCode,String keyValue) throws Exception {
        return super.update(entity,keyCode,keyValue);
    }
    
    public Integer updateNull(${entity} entity,String keyCode,String keyValue) throws Exception {
        return super.updateNull(entity,keyCode,keyValue);
    }
    
    public Integer updateState(${entity} entity) throws Exception {
        if(entity.getState().equals(State.deleted.name())){
            return super.delete(entity.getId());
        }
        return super.update(entity);
    }

    public Integer delete(Long id) throws Exception {
        return super.delete(id);
    }

    public boolean isExist(String keyCode,String keyValue) throws Exception {
        return super.isExist(keyCode,keyValue);
    }

    public boolean isExist(Long id) throws Exception {
        return super.isExist(id);
    }
     
}
