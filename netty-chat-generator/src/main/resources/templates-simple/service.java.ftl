package ${package.Service};

import ${cfg.parentPackage}.vo.${entity}VO;

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
    boolean add(${entity}VO entityVO) throws Exception;
    
    /**
    * 删除
    */
    boolean delete(Long id) throws Exception;
    
    /**
    * 更新
    */
    boolean update(${entity}VO entityVO) throws Exception;
    
    /**
    * 状态修改
    */
    boolean updateState(UpdateState entityVO) throws Exception;
    
    /**
    * 查询
    */
    ${entity}VO get(Long id) throws Exception;
    
    /**
    * 分页查询
    */
    PageVO<${entity}VO> queryPage(PageQuery pageQuery) throws Exception;

    /**
    * 通过Map查询
    */
    List<${entity}VO> query(Map<String, Object> query) throws Exception;

    /**
    * 查询所有
    */
    List<${entity}VO> queryAll() throws Exception;
   
}
