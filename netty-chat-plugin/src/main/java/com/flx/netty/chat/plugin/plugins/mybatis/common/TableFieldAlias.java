package com.flx.netty.chat.plugin.plugins.mybatis.common;

import com.flx.netty.chat.plugin.annotion.mybatis.ColumnName;
import com.flx.netty.chat.plugin.annotion.mybatis.TableName;
import com.flx.netty.chat.common.utils.code.CodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.lang.reflect.Field;
import java.util.*;

/**
 * 获取字段别名，用于Mybatis条件构造器
 * @author fenglixiong
 */
@Slf4j
@Configuration
public class TableFieldAlias implements ResourceLoaderAware {

    /**
     * 实体类包路径
     */
    @Value("${spring.flx.entity.package:com.flx.**.entity}")
    private String entityPackage;

    /**
     * 实体字段数据库名称
     */
    private static Map<String, String> fieldAliasMap = new HashMap<>();
    /**
     * 所有表的集合
     */
    private static Set<String> tableNameSet = new HashSet<>();

    private ResourceLoader resourceLoader;

    @Override
    public void setResourceLoader(@NotNull ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    /**
     * 字段转换为小写
     * 驼峰转下划线
     * 获取别名字段
     * agvPoint--->agv_point
     * @param source 实体中的字段
     * @return 字段在数据库中的名称
     */
    public static String getTableFiledName(String source) {
        if (fieldAliasMap.containsKey(source)) {
            return fieldAliasMap.get(source);
        }
        return CodeUtils.camelToUnder(source);
    }

    /**
     * @return 表的名称集合
     */
    public static Set<String> getTableNameSet(){
        return Collections.unmodifiableSet(tableNameSet);
    }

    /**
     * 启动程序会自动扫描系统中实体字段的别名
     * @throws Exception
     */
    @PostConstruct
    @Transactional(rollbackFor = Exception.class)
    public void getTableFieldValue() throws Exception {
        long start=System.currentTimeMillis();
        log.info("TableFieldAlias entityPackage = "+entityPackage);
        ResourcePatternResolver resolver = ResourcePatternUtils.getResourcePatternResolver(resourceLoader);
        MetadataReaderFactory metaReader = new CachingMetadataReaderFactory(resourceLoader);
        entityPackage = Objects.requireNonNull(entityPackage).replaceAll("\\.","/");
        String realLocations = "classpath*:"+entityPackage+"/*.class";
        Resource[] resources = resolver.getResources(realLocations);
        for (Resource r : resources) {
            MetadataReader reader = metaReader.getMetadataReader(r);
            ClassMetadata entityClass = reader.getClassMetadata();
            Class<?> clazz = Class.forName(entityClass.getClassName());
            if (clazz.getAnnotation(TableName.class) != null) {
                TableName entity = clazz.getAnnotation(TableName.class);
                if (StringUtils.isNotEmpty(entity.value())) {
                    Field[] fields = clazz.getDeclaredFields();
                    for (Field field : fields) {
                        if (field.getAnnotation(ColumnName.class) != null) {
                            ColumnName t = field.getAnnotation(ColumnName.class);
                            if (StringUtils.isNotEmpty(t.value())) {
                                fieldAliasMap.put(field.getName(), t.value());
                            }
                        }
                    }
                    tableNameSet.add(entity.value());
                }
            }

        }

        log.info("GetTable time : {}",System.currentTimeMillis()-start);
        log.info("GetTable alis success : fieldAlias = {}",fieldAliasMap.toString());

    }


}
