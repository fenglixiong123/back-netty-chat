package com.flx.netty.chat.generator.service;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.IColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.flx.netty.chat.generator.config.CustomFileOutputConfig;
import com.flx.netty.chat.generator.utils.property.custom.PropertyUtils;
import com.flx.netty.chat.plugin.plugins.mybatis.base.BaseDO;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Fenglixiong
 * @Date: 2021/4/23 11:43
 * @Description: 自定义生成代码
 */
@Slf4j
@SuppressWarnings("all")
public class CustomGeneratorService {

    private static final String CONFIG_FILE = "application-custom.properties";

    //数据库设置
    private static String url;
    private static String username;
    private static String password;
    private static String driverName;

    //是否覆盖代码
    private static boolean override;
    //父模块名称
    private static String parentModule;
    //父类包名
    private static String parentPackage;

    //去除表前缀
    private static String tablePrefix;
    //要生成的表
    private static String tables;
    //要去除的列
    private static String removeColumns;

    //逻辑删除
    private static String delete;
    //乐观锁
    private static String version;

    static {
        url = PropertyUtils.get("spring.datasource.url");
        username = PropertyUtils.get("spring.datasource.username");
        password = PropertyUtils.get("spring.datasource.password");
        driverName = PropertyUtils.get("spring.datasource.driver-class-name");

        override = PropertyUtils.getBoolean("flx.generator.override",true);

        parentModule = PropertyUtils.get("flx.generator.module.parent.name");
        parentPackage = PropertyUtils.get("flx.generator.package.parent.name");

        tablePrefix = PropertyUtils.get("flx.generator.table.prefix");
        tables = PropertyUtils.get("flx.generator.tables");
        removeColumns = PropertyUtils.get("flx.generator.remove.columns");

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
        globalConfig.setAuthor("Fenglixiong");// 设置作者
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
        globalConfig.setXmlName("%sManager");//自定义mapper名称
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
     * 包配置,主要是导包的包名配置
     */
    private static PackageConfig packageConfig(){
        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setParent(parentPackage);//生成代码的包名
        packageConfig.setController(getModeulePackage("controller"));//生成代码的controller类包名
        packageConfig.setService(getModeulePackage("service"));//生成代码的service类包名
        packageConfig.setMapper(getModeulePackage("dao"));//生成代码的dao类包名
        packageConfig.setServiceImpl(getModeulePackage("service.impl"));//生成代码的serviceImpl类包名
        packageConfig.setEntity(getModeulePackage("entity"));//生成代码的实体类包名
        packageConfig.setXml(getModeulePackage("manager"));//生成代码的实体类xml包名
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
            strategy.setTablePrefix(tablePrefix.split(","));//去除表名前缀
        }
        strategy.setInclude(tables.split(","));// 需要生成的表
        strategy.setNaming(NamingStrategy.underline_to_camel);// 表名生成策略
        strategy.setRestControllerStyle(true);//生成RestController模型
        strategy.setControllerMappingHyphenStyle(false);//驼峰转连字符@RequestMapping("/groupUser")===>@RequestMapping("/group-user")
        //----->字段设置
        strategy.setSuperEntityColumns(removeColumns.split(","));
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);//字段生成策略
        strategy.setEntityColumnConstant(true);//是否生成字段常量，public static final String ID = "test_id";
        strategy.setEntityTableFieldAnnotationEnable(true);//生成字段注解
        strategy.setEntitySerialVersionUID(false);//实体是否生成serialVersionUID
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
        strategy.setSuperMapperClass("com.flx.netty.chat.plugin.plugins.mybatis.base.BaseDao");
        strategy.setSuperEntityClass(BaseDO.class);
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
                map.put("parentModule", parentModule);
                map.put("parentPackage", parentPackage);
                this.setMap(map);
            }
        };

        //自定义文件输出位置
        injectionConfig.setFileOutConfigList(CustomFileOutputConfig.fileOutConfigList());

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

    /**
     * 获取各层模块的包名
     * @param key
     * @return
     */
    private static String getModeulePackage(String key){
        return PropertyUtils.get("flx.generator.package.module." + key + ".name")+"."+key;
    }


}
