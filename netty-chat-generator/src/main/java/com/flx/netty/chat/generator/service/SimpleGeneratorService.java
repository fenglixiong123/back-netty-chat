package com.flx.netty.chat.generator.service;

import com.flx.netty.chat.generator.entity.ConfigInfo;
import com.flx.netty.chat.generator.entity.config.*;
import com.flx.netty.chat.generator.manager.SimpleGeneratorManager;
import com.flx.netty.chat.generator.utils.property.simple.PropertyUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author: Fenglixiong
 * @Date: 2021/4/23 11:43
 * @Description: 简单的自动生成代码
 */
@Slf4j
@SuppressWarnings("all")
public class SimpleGeneratorService {

    @Autowired
    private SimpleGeneratorManager simpleManager;

    /**
     * 网络传参方式生成代码
     * @param configInfo
     */
    public void generator(ConfigInfo configInfo){
        simpleManager.generator(configInfo);
    }

    /**
     * 利用配置文件生成代码
     */
    public void generator(){
        
        String url = PropertyUtils.get("flx.generator.url");
        String username = PropertyUtils.get("flx.generator.username");
        String password = PropertyUtils.get("flx.generator.password");
        String driverName = PropertyUtils.get("flx.generator.driver-class-name");

        boolean override = PropertyUtils.getBoolean("flx.generator.override",true);

        String parentModule = PropertyUtils.get("flx.generator.module.parent.name");
        String parentPackage = PropertyUtils.get("flx.generator.package.parent.name");

        String tablePrefix = PropertyUtils.get("flx.generator.table.prefix");
        String tables = PropertyUtils.get("flx.generator.tables");
        String removeColumns = PropertyUtils.get("flx.generator.remove.columns");
        String delete = PropertyUtils.get("flx.generator.logic.delete");
        String version = PropertyUtils.get("flx.generator.optimis.version");

        simpleManager.generator(new ConfigInfo()
                .setGlobal(new GlobalInfo()
                        .setOverride(override))
                .setDataSource(new DataSourceInfo()
                        .setUrl(url)
                        .setUsername(username)
                        .setPassword(password)
                        .setDriveName(driverName))
                .setModule(new ModuleInfo()
                        .setParentModule(parentModule))
                .setPack(new PackageInfo()
                        .setParentPackage(parentPackage)
                        .setControllerPackage("controller")
                        .setServicePackage("service")
                        .setServiceImplPackage("service.impl")
                        .setManagerPackage("manager")
                        .setDaoPackage("dao")
                        .setEntityPackage("entity"))
                .setTable(new TableInfo()
                        .setTablePrefix(tablePrefix)
                        .setTables(tables)
                        .setRemoveColumn(removeColumns)
                        .setDeleted(delete)
                        .setVersion(version)));

    }

}
