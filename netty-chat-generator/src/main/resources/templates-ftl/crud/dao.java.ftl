package ${package.Mapper};

import ${package.Entity}.${entity};
import ${superMapperClassPackage};

import com.flx.netty.chat.plugin.annotion.mybatis.DaoMapper;


/**
 * ${table.comment!} Dao层操作类
 *
 * @author ${author}
 * @since ${date}
 */

@DaoMapper
public interface ${table.mapperName} extends ${superMapperClass}<${entity}> {

}
