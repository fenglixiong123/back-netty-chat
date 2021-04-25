package com.flx.netty.chat.generator.service;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.flx.netty.chat.common.constants.FileConstant;
import com.flx.netty.chat.generator.utils.property.simple.PropertyUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: Fenglixiong
 * @Date: 2021/4/23 11:43
 * @Description: 简单的自动生成代码
 */
@Slf4j
@SuppressWarnings("all")
public class SimpleGeneratorService {

    //数据库设置
    private static String url;
    private static String username;
    private static String password;
    private static String driverName;
    //输出目录
    private static String baseOutputPath;
    //模块目录
    private static String moduleName;
    //是否覆盖代码
    private static boolean override;
    //父类包名
    private static String parentPackage;
    //模块包名
    private static String modulePackage;

    //去除表前缀
    private static String tablePrefix;
    //要生成的表
    private static String tables;

    //逻辑删除
    private static String delete;
    //乐观锁
    private static String version;

    static {
        url = PropertyUtils.get("spring.datasource.url");
        username = PropertyUtils.get("spring.datasource.username");
        password = PropertyUtils.get("spring.datasource.password");
        driverName = PropertyUtils.get("spring.datasource.driver-class-name");

        baseOutputPath = System.getProperty("user.dir") + FileConstant.pathSeparator;
        moduleName = PropertyUtils.get("flx.generator.module.name");
        override = PropertyUtils.getBoolean("flx.generator.override",true);

        parentPackage = PropertyUtils.get("flx.generator.package.parent.name");
        modulePackage = PropertyUtils.get("flx.generator.package.module.name");

        tablePrefix = PropertyUtils.get("flx.generator.table.prefix");
        tables = PropertyUtils.get("flx.generator.tables");

        delete = PropertyUtils.get("flx.generator.logic.delete");
        version = PropertyUtils.get("flx.generator.optimis.version");
    }

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
        baseOutputPath += moduleName + FileConstant.pathSeparator;
        String outputPath = baseOutputPath + "src/main/java";
        log.info("BaseOutputPath = {}",baseOutputPath);
        log.info("FileOutputPath = {}",outputPath);
        globalConfig.setAuthor("Fenglixiong");// 设置作者
        globalConfig.setOutputDir(outputPath);//设置输出文件路径
        globalConfig.setFileOverride(override);//是否覆盖代码
        globalConfig.setActiveRecord(true);// 实体类只需继承 Model 类即可实现基本 CRUD 操作
        globalConfig.setEnableCache(false);// 是否开启二级缓存
        globalConfig.setBaseResultMap(true);// XML ResultMap
        globalConfig.setBaseColumnList(true);// XML columList
        globalConfig.setOpen(false);//是否打开输出文件
        globalConfig.setIdType(IdType.AUTO);//数据库ID类型
        globalConfig.setDateType(DateType.ONLY_DATE);//配置时间采用utils包下的时间
        globalConfig.setSwagger2(true); //实体属性 Swagger2 注解

        globalConfig.setMapperName("%sDao");//自定义Dao名称
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
        dbConfig.setUrl(url);
        dbConfig.setUsername(username);
        dbConfig.setPassword(password);
        dbConfig.setDriverName(driverName);
        dbConfig.setDbType(DbType.MYSQL);
        return dbConfig;
    }

    /**
     * 包配置
     */
    private static PackageConfig packageConfig(){
        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setParent(parentPackage);//生成代码的包名
        packageConfig.setModuleName(modulePackage);//设置模块名称,会添加到包名后面
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
        if(tablePrefix!=null) {
            strategy.setTablePrefix(tablePrefix);//去除表名前缀
        }
        strategy.setInclude(tables.split(","));// 需要生成的表
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
        if(delete!=null) {
            strategy.setLogicDeleteFieldName(delete);//逻辑删除字段 @TableLogic
        }
        if(version!=null) {
            strategy.setVersionFieldName(version);//乐观锁字段 @Version
        }
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
                //todo
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
        return new TemplateConfig().setXml(null);
    }


}
