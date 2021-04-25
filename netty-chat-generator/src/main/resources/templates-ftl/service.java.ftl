package ${package.Service};

import ${package.Entity}.${entity};
import ${superServiceClassPackage};

import com.flx.netty.chat.common.entity.UpdateState;
import com.flx.netty.chat.common.utils.page.PageQuery;
import com.flx.netty.chat.common.utils.page.PageVO;

import java.util.List;
import java.util.Map;

/**
 * ${table.comment!} 服务类
 *
 * @author ${author}
 * @since ${date}
 */
public interface ${table.serviceName} {

    /**
    * 新增
    */
    Long add(${entity} entity) throws Exception;
    
    /**
    * 删除
    */
    Integer delete(Long id) throws Exception;
    
    /**
    * 更新
    */
    Integer update(${entity} entity) throws Exception;
    
    /**
    * 状态修改
    */
    boolean updateState(UpdateState entity) throws Exception;
    
    /**
    * 查询
    */
    ${entity} get(Long id) throws Exception;
    
    /**
    * 模糊分页查询
    */
    PageVO<${entity}> queryPage(PageQuery pageQuery) throws Exception;
    
    /**
    * 通过Map模糊查询
    */
    List<${entity}> query(Map<String, Object> query) throws Exception;
    
    /**
    * 查询指定字段
    */
    List<${entity}> querySome(Map<String, Object> query,String[] columns) throws Exception;
  
    /**
    * 查询所有
    */
    List<${entity}> queryAll(Map<String, Object> query) throws Exception;


}
