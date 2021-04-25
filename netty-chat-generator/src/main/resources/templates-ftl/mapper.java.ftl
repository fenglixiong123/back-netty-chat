package ${package.Mapper};

import ${package.Entity}.${entity};
import ${superMapperClassPackage};

import com.flx.netty.chat.plugin.annotion.mybatis.DaoMapper;


/**
 * <p>
 * ${table.comment!} Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */

@DaoMapper
public interface ${table.mapperName} extends ${superMapperClass}<${entity}> {

}
