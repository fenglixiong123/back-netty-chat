package ${cfg.parentPackage}.${cfg.voPackage};

import com.flx.netty.chat.common.vo.BaseVO;
import lombok.Data;

/**
 * ${table.comment!} VO实体类
 *
 * @author ${author}
 * @since ${date}
 */

@Data
public class ${entity}VO extends BaseVO {
<#-- ----------  BEGIN 字段循环遍历  ---------->
<#list table.fields as field>

    <#if field.comment!?length gt 0>
    /**
     * ${field.comment}
     */
    </#if>
    private ${field.propertyType} ${field.propertyName};
</#list>
<#------------  END 字段循环遍历  ---------->

}
