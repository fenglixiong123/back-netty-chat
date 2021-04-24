package com.flx.netty.chat.generator.service;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.IColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.flx.netty.chat.common.constants.FileConstant;
import com.flx.netty.chat.common.utils.date.DateUtils;
import com.flx.netty.chat.common.utils.system.PropertyUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * @Author: Fenglixiong
 * @Date: 2021/4/23 11:43
 * @Description:
 */
@Slf4j
public class GeneratorService {

    /**
     * 输出跟目录
     */
    private static String baseOutputPath = "/";

    public static void generator() {

        new AutoGenerator()
                .setGlobalConfig(globalConfig())// 全局配置
                .setDataSource(dataSourceConfig())//数据源配置
                .setPackageInfo(packageConfig())//代码包配置
                .setStrategy(strategyConfig())//表配置
                .setTemplate(templateConfig())//设置模板
                .setTemplateEngine(new FreemarkerTemplateEngine())//设置模板引擎
                .setCfg(injectionConfig())//自定义配置
                .execute();

    }

    /**
     * 全局配置
     */
    private static GlobalConfig globalConfig() {
        GlobalConfig globalConfig = new GlobalConfig();
        baseOutputPath = System.getProperty("user.dir")+ FileConstant.pathSeparator;//user.dir为项目跟目录
        String moduleName = PropertyUtils.get("flx.generator.module.name");
        if(StringUtils.isNotBlank(moduleName)){
            baseOutputPath += moduleName + FileConstant.pathSeparator;
        }
        String outputPath = baseOutputPath + "src/main/java";
        log.info("BaseOutputPath = {}",baseOutputPath);
        log.info("FileOutputPath = {}",outputPath);
        globalConfig.setAuthor("Fenglixiong");// 设置作者
        globalConfig.setOutputDir(outputPath);//设置输出文件路径
        globalConfig.setFileOverride(PropertyUtils.getBoolean("flx.generator.override",true));//是否覆盖代码
        globalConfig.setActiveRecord(true);// 实体类只需继承 Model 类即可实现基本 CRUD 操作
        globalConfig.setEnableCache(false);// 是否开启二级缓存
        globalConfig.setBaseResultMap(true);// XML ResultMap
        globalConfig.setBaseColumnList(true);// XML columList
        globalConfig.setOpen(false);//是否打开输出文件
        globalConfig.setIdType(IdType.AUTO);//数据库ID类型
        globalConfig.setDateType(DateType.ONLY_DATE);//配置时间采用utils包下的时间
        globalConfig.setSwagger2(true); //实体属性 Swagger2 注解
        //自定义文件命名,%s会自动填充表实体属性
        globalConfig.setMapperName("%sMapper");//自定义Dao名称
        globalConfig.setXmlName("%sMapper");//自定义mapper名称
        globalConfig.setServiceName("%sService");//自定义service名称
        globalConfig.setServiceImplName("%sServiceImpl");//自定义serviceImpl名称
        globalConfig.setControllerName("%sController");//自定义Controller名称
        return globalConfig;
    }

    /**
     * 数据源配置
     */
    private static DataSourceConfig dataSourceConfig(){
        DataSourceConfig dbConfig = new DataSourceConfig();
        dbConfig.setUrl(PropertyUtils.get("spring.datasource.url"));
        dbConfig.setUsername(PropertyUtils.get("spring.datasource.username"));
        dbConfig.setPassword(PropertyUtils.get("spring.datasource.password"));
        dbConfig.setDriverName(PropertyUtils.get("spring.datasource.driver-class-name"));
        dbConfig.setDbType(DbType.MYSQL);
        //自定义类型转换
        dbConfig.setTypeConvert(new MySqlTypeConvert(){
            @Override
            public IColumnType processTypeConvert(GlobalConfig config, String fieldType) {
                //System.out.println("转换类型：" + fieldType);
                // if ( fieldType.toLowerCase().contains( "tinyint" ) ) {
                //    return DbColumnType.BOOLEAN;
                // }
                return super.processTypeConvert(config, fieldType);
            }
        });
        return dbConfig;
    }

    /**
     * 包配置
     */
    private static PackageConfig packageConfig(){
        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setParent(PropertyUtils.get("flx.generator.package.name"));//生成代码的包名
        packageConfig.setModuleName(PropertyUtils.get("flx.generator.package.module.name"));//设置模块名称,会添加到包名后面
        packageConfig.setEntity("entity");//生成代码的实体类包名
        packageConfig.setXml("dao.xml");//生成代码的实体类xml包名
        packageConfig.setMapper("dao");//生成代码的dao类包名
        packageConfig.setService("service");//生成代码的service类包名
        packageConfig.setServiceImpl("service.iml");//生成代码的serviceImpl类包名
        packageConfig.setController("controller");//生成代码的controller类包名
        return packageConfig;
    }

    /**
     * 配置策略
     * versionFieldName 乐观锁属性名称
     * logicDeleteFieldName 逻辑删除属性名称
     */
    private static StrategyConfig strategyConfig(){
        StrategyConfig strategy = new StrategyConfig();
        //----->表设置
        boolean removePrefix = PropertyUtils.getBoolean("flx.generator.table.prefix.remove",false);
        if(removePrefix) {
            strategy.setTablePrefix(PropertyUtils.get("flx.generator.table.prefix"));//去除表名前缀
        }
        strategy.setInclude(PropertyUtils.get("flx.generator.tables").split(","));// 需要生成的表
        strategy.setNaming(NamingStrategy.underline_to_camel);// 表名生成策略
        strategy.setRestControllerStyle(true);//生成RestController模型
        strategy.setControllerMappingHyphenStyle(false);//驼峰转连字符@RequestMapping("/groupUser")===>@RequestMapping("/group-user")
        //----->字段设置
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);//字段生成策略
        strategy.setEntityColumnConstant(false);//是否生成字段常量，public static final String ID = "test_id";
        strategy.setEntityTableFieldAnnotationEnable(true);//生成字段注解
        strategy.setEntitySerialVersionUID(true);//实体是否生成serialVersionUID
        strategy.setEntityLombokModel(true);//是否为lombok模型
        strategy.setChainModel(false);//实体类的链式模型
        strategy.setEntityBooleanColumnRemoveIsPrefix(false);//Boolean类型字段是否移除is前缀
        strategy.setLogicDeleteFieldName("deleted");//逻辑删除字段 @TableLogic
        strategy.setVersionFieldName("version");//乐观锁字段 @Version
        //----->标记自动填充字段
        strategy.setTableFillList(Arrays.asList(
                new TableFill("state", FieldFill.INSERT),
                new TableFill("create_user", FieldFill.INSERT),
                new TableFill("update_user", FieldFill.INSERT_UPDATE),
                new TableFill("create_time", FieldFill.INSERT),
                new TableFill("update_time", FieldFill.INSERT_UPDATE)
        ));
        //strategy.setSuperControllerClass("");//Controller继承的父类
        //strategy.setSuperServiceClass("com.baomidou.mybatisplus.extension.service.IService");
        //strategy.setSuperServiceImplClass("com.baomidou.mybatisplus.extension.service.impl.ServiceImpl");
        //strategy.setSuperMapperClass("com.baomidou.mybatisplus.mapper.BaseMapper");//Mapper父类
        //strategy.setSuperEntityClass(BaseEntity.class);//继承的Entity类
        return strategy;
    }

    /**
     * 自定义配置
     * @return
     */
    private static InjectionConfig injectionConfig(){

        InjectionConfig injectionConfig = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<>();
                map.put("dateTime", DateUtils.nowStr());
                this.setMap(map);
            }
        };

        //自定义文件输出位置
        List<FileOutConfig> fileOutConfigList = new ArrayList<>();
        fileOutConfigList.add(new FileOutConfig("/templates/mapper.xml.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return baseOutputPath + "src/main/resources/mapper/" + tableInfo.getEntityName() + "Mapper.xml";
            }
        });
        injectionConfig.setFileOutConfigList(fileOutConfigList);

        return injectionConfig;
    }

    /**
     * 代码模板设置
     * 置空后使用自定义的
     * @return
     */
    private static TemplateConfig templateConfig(){
        return new TemplateConfig()
                .setController(null)
                .setService(null)
                .setServiceImpl(null)
                .setEntity(null)
                .setMapper(null)
                .setXml(null);
    }


}
