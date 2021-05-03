package com.flx.netty.chat.generator.entity.config;

import org.apache.commons.lang3.StringUtils;

/**
 * @Author: Fenglixiong
 * @Date: 2021/4/26 18:47
 * @Description: 包设置
 */
public class PackageInfo {

    /**
     * 父类包
     */
    private String parentPackage;

    /**
     * 业务包
     */
    private String consolePackage;

    /**
     * crud包
     */
    private String crudPackage;

    /**
     * api包
     */
    private String apiPackage;

    //具体的包

    private String controllerPackage;

    private String servicePackage;

    private String serviceImplPackage;

    private String managerPackage;

    private String daoPackage;

    private String entityPackage;

    private String voPackage;

    private String clientPackage;

    public String getParentPackage() {
        return parentPackage;
    }

    public PackageInfo setParentPackage(String parentPackage) {
        this.parentPackage = parentPackage;
        return this;
    }

    public String getConsolePackage() {
        return consolePackage;
    }

    public PackageInfo setConsolePackage(String consolePackage) {
        this.consolePackage = consolePackage;
        return this;
    }

    public String getCrudPackage() {
        return crudPackage;
    }

    public PackageInfo setCrudPackage(String crudPackage) {
        this.crudPackage = crudPackage;
        return this;
    }

    public String getApiPackage() {
        return apiPackage;
    }

    public PackageInfo setApiPackage(String apiPackage) {
        this.apiPackage = apiPackage;
        return this;
    }

    public String getControllerPackage() {
        if(StringUtils.isBlank(controllerPackage)){
            return consolePackage;
        }
        return controllerPackage;
    }

    public PackageInfo setControllerPackage(String controllerPackage) {
        this.controllerPackage = controllerPackage;
        return this;
    }

    public String getServicePackage() {
        if(StringUtils.isBlank(servicePackage)){
            return consolePackage;
        }
        return servicePackage;
    }

    public PackageInfo setServicePackage(String servicePackage) {
        this.servicePackage = servicePackage;
        return this;
    }

    public String getServiceImplPackage() {
        if(StringUtils.isBlank(serviceImplPackage)){
            return consolePackage;
        }
        return serviceImplPackage;
    }

    public PackageInfo setServiceImplPackage(String serviceImplPackage) {
        this.serviceImplPackage = serviceImplPackage;
        return this;
    }

    public String getManagerPackage() {
        if(StringUtils.isBlank(managerPackage)){
            return crudPackage;
        }
        return managerPackage;
    }

    public PackageInfo setManagerPackage(String managerPackage) {
        this.managerPackage = managerPackage;
        return this;
    }

    public String getDaoPackage() {
        if(StringUtils.isBlank(daoPackage)){
            return crudPackage;
        }
        return daoPackage;
    }

    public PackageInfo setDaoPackage(String daoPackage) {
        this.daoPackage = daoPackage;
        return this;
    }

    public String getEntityPackage() {
        if(StringUtils.isBlank(entityPackage)){
            return crudPackage;
        }
        return entityPackage;
    }

    public PackageInfo setEntityPackage(String entityPackage) {
        this.entityPackage = entityPackage;
        return this;
    }

    public String getVoPackage() {
        if(StringUtils.isBlank(voPackage)){
            return apiPackage;
        }
        return voPackage;
    }

    public PackageInfo setVoPackage(String voPackage) {
        this.voPackage = voPackage;
        return this;
    }

    public String getClientPackage() {
        if(StringUtils.isBlank(clientPackage)){
            return apiPackage;
        }
        return clientPackage;
    }

    public PackageInfo setClientPackage(String clientPackage) {
        this.clientPackage = clientPackage;
        return this;
    }
}
