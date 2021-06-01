package com.flx.netty.chat.generator.manager;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.flx.netty.chat.common.constants.FileConstant;
import com.flx.netty.chat.generator.constants.Module;
import com.flx.netty.chat.generator.output.SimpleFileOutput;
import com.flx.netty.chat.generator.entity.ConfigInfo;
import com.flx.netty.chat.generator.entity.config.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Fenglixiong
 * @Date: 2021/4/26 20:21
 * @Description:
 */
@Slf4j
@Service
@SuppressWarnings("all")
public class SimpleGeneratorManager {

    public void generator(ConfigInfo config) {

        new AutoGenerator()
                .setGlobalConfig(this.getGlobalConfig(config))// 全局配置
                .setDataSource(this.getDataSourceConfig(config))//数据源配置
                .setPackageInfo(this.getPackageConfig(config))//代码包配置
                .setStrategy(this.getStrategyConfig(config))//表配置
                .setTemplate(this.getTemplateConfig())//设置模板
                .setTemplateEngine(new FreemarkerTemplateEngine())//设置模板引擎
                .setCfg(this.getInjectionConfig(config))//自定义配置
                .execute();

    }

    /**
     * 全局配置
     */
    private GlobalConfig getGlobalConfig(ConfigInfo config) {
        GlobalInfo global = config.getGlobal();
        ModuleInfo module = config.getModule();
        GlobalConfig globalConfig = new GlobalConfig();
        String baseOutputPath = global.getBasePath() + module.getParentModule() + FileConstant.pathSeparator;
        String outputPath = baseOutputPath + "src/main/java";
        log.info("BaseOutputPath = {}",baseOutputPath);
        log.info("FileOutputPath = {}",outputPath);
        globalConfig.setAuthor(global.getAuthor());// 设置作者
        globalConfig.setOutputDir(outputPath);//设置输出文件路径
        globalConfig.setFileOverride(global.isOverride());//是否覆盖代码
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
    private DataSourceConfig getDataSourceConfig(ConfigInfo config){
        DataSourceInfo dataSource = config.getDataSource();
        DataSourceConfig dbConfig = new DataSourceConfig();
        dbConfig.setUrl(dataSource.getUrl());
        dbConfig.setUsername(dataSource.getUsername());
        dbConfig.setPassword(dataSource.getPassword());
        dbConfig.setDriverName(dataSource.getDriveName());
        dbConfig.setDbType(DbType.MYSQL);
        return dbConfig;
    }

    /**
     * 包配置
     */
    private PackageConfig getPackageConfig(ConfigInfo config){
        PackageInfo pack = config.getPack();
        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setParent(pack.getParentPackage());//生成代码的包名
        packageConfig.setController(Module.CONTROLLER);//生成代码的controller类包名
        packageConfig.setService(Module.SERVICE);//生成代码的service类包名
        packageConfig.setServiceImpl(Module.SERVICE_IMPL);//生成代码的serviceImpl类包名
        packageConfig.setMapper(Module.DAO);//生成代码的dao类包名
        packageConfig.setEntity(Module.ENTITY);//生成代码的实体类包名
        return packageConfig;
    }

    /**
     * 配置策略
     * versionFieldName 乐观锁属性名称
     * logicDeleteFieldName 逻辑删除属性名称
     */
    private StrategyConfig getStrategyConfig(ConfigInfo config){
        TableInfo table = config.getTable();
        StrategyConfig strategy = new StrategyConfig();
        //----->表设置
        if(table.getTablePrefix()!=null) {
            strategy.setTablePrefix(table.getTablePrefix().split(","));//去除表名前缀
        }
        strategy.setInclude(table.getTables().split(","));// 需要生成的表
        strategy.setNaming(NamingStrategy.underline_to_camel);// 表名生成策略
        strategy.setRestControllerStyle(true);//生成RestController模型
        strategy.setControllerMappingHyphenStyle(false);//驼峰转连字符@RequestMapping("/groupUser")===>@RequestMapping("/group-user")
        //----->字段设置
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);//字段生成策略
        strategy.setEntityColumnConstant(true);//是否生成字段常量，public static final String ID = "test_id";
        strategy.setEntityTableFieldAnnotationEnable(true);//生成字段注解
        strategy.setEntitySerialVersionUID(true);//实体是否生成serialVersionUID
        strategy.setEntityLombokModel(true);//是否为lombok模型
        strategy.setChainModel(false);//实体类的链式模型
        strategy.setEntityBooleanColumnRemoveIsPrefix(false);//Boolean类型字段是否移除is前缀
        if(table.getDeleted()!=null) {
            strategy.setLogicDeleteFieldName(table.getDeleted());//逻辑删除字段 @TableLogic
        }
        if(table.getVersion()!=null) {
            strategy.setVersionFieldName(table.getVersion());//乐观锁字段 @Version
        }
        //----->标记自动填充字段
        /*
        strategy.setTableFillList(Arrays.asList(
                new TableFill("state", FieldFill.INSERT),
                new TableFill("create_user", FieldFill.INSERT),
                new TableFill("update_user", FieldFill.INSERT_UPDATE),
                new TableFill("create_time", FieldFill.INSERT),
                new TableFill("update_time", FieldFill.INSERT_UPDATE)
        ));
         */
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
    private InjectionConfig getInjectionConfig(ConfigInfo config){
        PackageInfo pack = config.getPack();
        InjectionConfig injectionConfig = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<>();
                map.put("parentPackage", pack.getParentPackage());
                this.setMap(map);
            }
        };

        injectionConfig.setFileOutConfigList(new SimpleFileOutput().fileOutConfigList(config));

        return injectionConfig;
    }

    /**
     * 代码模板设置
     * 置空后使用自定义的
     * @return
     */
    private TemplateConfig getTemplateConfig(){
        return new TemplateConfig()
                .setController(null)
                .setService(null)
                .setServiceImpl(null)
                .setEntity(null)
                .setMapper(null)
                .setXml(null);
    }

}
