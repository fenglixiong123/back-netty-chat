package com.flx.netty.chat.generator.service;

import com.flx.netty.chat.generator.constants.Module;
import com.flx.netty.chat.generator.entity.ConfigInfo;
import com.flx.netty.chat.generator.entity.config.*;
import com.flx.netty.chat.generator.manager.CustomGeneratorManager;
import com.flx.netty.chat.generator.utils.property.custom.PropertyUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: Fenglixiong
 * @Date: 2021/4/23 11:43
 * @Description: 自定义自动生成代码
 */
@Slf4j
@Service
@SuppressWarnings("all")
public class CustomGeneratorService {

    @Autowired
    private CustomGeneratorManager customManager;

    /**
     * 网络传参方式生成代码
     * @param configInfo
     */
    public void generator(ConfigInfo configInfo){
        customManager.generator(configInfo);
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

        customManager.generator(new ConfigInfo()
                .setGlobal(new GlobalInfo()
                        .setOverride(override))
                .setDataSource(new DataSourceInfo()
                        .setUrl(url)
                        .setUsername(username)
                        .setPassword(password)
                        .setDriveName(driverName))
                .setModule(new ModuleInfo()
                        .setParentModule(parentModule)
                        .setControllerModule(this.getModuleName(Module.CONTROLLER))
                        .setServiceModule(this.getModuleName(Module.SERVICE))
                        .setServiceImplModule(this.getModuleName(Module.SERVICE_IMPL))
                        .setManagerModule(this.getModuleName(Module.MANAGER))
                        .setDaoModule(this.getModuleName(Module.DAO))
                        .setEntityModule(this.getModuleName(Module.ENTITY))
                        .setVoModule(this.getModuleName(Module.VO))
                        .setClientModule(this.getModuleName(Module.CLIENT)))
                .setPack(new PackageInfo()
                        .setParentPackage(parentPackage)
                        .setControllerPackage(this.getModulePackage(Module.CONTROLLER))
                        .setServicePackage(this.getModulePackage(Module.SERVICE))
                        .setServiceImplPackage(this.getModulePackage(Module.SERVICE_IMPL))
                        .setManagerPackage(this.getModulePackage(Module.MANAGER))
                        .setDaoPackage(this.getModulePackage(Module.DAO))
                        .setEntityPackage(this.getModulePackage(Module.ENTITY))
                        .setVoPackage(this.getModulePackage(Module.VO))
                        .setClientPackage(this.getModulePackage(Module.CLIENT)))
                .setTable(new TableInfo()
                        .setTablePrefix(tablePrefix)
                        .setTables(tables)
                        .setRemoveColumn(removeColumns)
                        .setDeleted(delete)
                        .setVersion(version)));
        
    }

    /**
     * 获取各层模块的名字
     * @param key
     * @return
     */
    private String getModuleName(String key){
        return PropertyUtils.get("flx.generator.module." + key + ".name");
    }

    /**
     * 获取各层模块的包名
     * @param key
     * @return
     */
    private String getModulePackage(String key){
        return PropertyUtils.get("flx.generator.package.module." + key + ".name")+"."+key;
    }
    

}
