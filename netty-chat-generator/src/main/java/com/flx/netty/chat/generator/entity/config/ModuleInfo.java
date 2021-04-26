package com.flx.netty.chat.generator.entity.config;

import org.apache.commons.lang3.StringUtils;

/**
 * @Author: Fenglixiong
 * @Date: 2021/4/26 18:50
 * @Description: 模块设置
 */
public class ModuleInfo {

    /**
     * 父类模块
     */
    private String parentModule;

    /**
     * 业务模块
     */
    private String consoleModule;

    /**
     * crud模块
     */
    private String crudModule;

    /**
     * api模块
     */
    private String apiModule;

    //具体的模块

    private String controllerModule;

    private String serviceModule;

    private String serviceImplModule;

    private String managerModule;

    private String daoModule;

    private String entityModule;

    private String mapperModule;

    private String voModule;

    private String clientModule;

    public String getParentModule() {
        return parentModule;
    }

    public ModuleInfo setParentModule(String parentModule) {
        this.parentModule = parentModule;
        return this;
    }

    public String getConsoleModule() {
        return consoleModule;
    }

    public ModuleInfo setConsoleModule(String consoleModule) {
        this.consoleModule = consoleModule;
        return this;
    }

    public String getCrudModule() {
        return crudModule;
    }

    public ModuleInfo setCrudModule(String crudModule) {
        this.crudModule = crudModule;
        return this;
    }

    public String getApiModule() {
        return apiModule;
    }

    public ModuleInfo setApiModule(String apiModule) {
        this.apiModule = apiModule;
        return this;
    }

    public String getControllerModule() {
        if(StringUtils.isBlank(controllerModule)){
            return consoleModule;
        }
        return controllerModule;
    }

    public ModuleInfo setControllerModule(String controllerModule) {
        this.controllerModule = controllerModule;
        return this;
    }

    public String getServiceModule() {
        if(StringUtils.isBlank(serviceModule)){
            return consoleModule;
        }
        return serviceModule;
    }

    public ModuleInfo setServiceModule(String serviceModule) {
        this.serviceModule = serviceModule;
        return this;
    }

    public String getServiceImplModule() {
        if(StringUtils.isBlank(serviceImplModule)){
            return consoleModule;
        }
        return serviceImplModule;
    }

    public ModuleInfo setServiceImplModule(String serviceImplModule) {
        this.serviceImplModule = serviceImplModule;
        return this;
    }

    public String getManagerModule() {
        if(StringUtils.isBlank(managerModule)){
            return crudModule;
        }
        return managerModule;
    }

    public ModuleInfo setManagerModule(String managerModule) {
        this.managerModule = managerModule;
        return this;
    }

    public String getDaoModule() {
        if(StringUtils.isBlank(daoModule)){
            return crudModule;
        }
        return daoModule;
    }

    public ModuleInfo setDaoModule(String daoModule) {
        this.daoModule = daoModule;
        return this;
    }

    public String getEntityModule() {
        if(StringUtils.isBlank(entityModule)){
            return crudModule;
        }
        return entityModule;
    }

    public ModuleInfo setEntityModule(String entityModule) {
        this.entityModule = entityModule;
        return this;
    }

    public String getMapperModule() {
        if(StringUtils.isBlank(mapperModule)){
            return crudModule;
        }
        return mapperModule;
    }

    public ModuleInfo setMapperModule(String mapperModule) {
        this.mapperModule = mapperModule;
        return this;
    }

    public String getVoModule() {
        if(StringUtils.isBlank(voModule)){
            return apiModule;
        }
        return voModule;
    }

    public ModuleInfo setVoModule(String voModule) {
        this.voModule = voModule;
        return this;
    }

    public String getClientModule() {
        if(StringUtils.isBlank(clientModule)){
            return apiModule;
        }
        return clientModule;
    }

    public ModuleInfo setClientModule(String clientModule) {
        this.clientModule = clientModule;
        return this;
    }
}
