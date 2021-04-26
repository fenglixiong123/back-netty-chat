package com.flx.netty.chat.generator.entity;

import com.flx.netty.chat.generator.entity.config.*;

/**
 * @Author: Fenglixiong
 * @Date: 2021/4/26 18:55
 * @Description:
 */
public class ConfigInfo {

    /**
     * 数据源配置
     */
    private DataSourceInfo dataSource;

    /**
     * 表配置
     */
    private TableInfo table;

    /**
     * 模块配置
     */
    private ModuleInfo module;

    /**
     * 包配置
     */
    private PackageInfo pack;

    /**
     * 其他配置
     */
    private GlobalInfo global;

    public DataSourceInfo getDataSource() {
        return dataSource;
    }

    public ConfigInfo setDataSource(DataSourceInfo dataSource) {
        this.dataSource = dataSource;
        return this;
    }

    public TableInfo getTable() {
        return table;
    }

    public ConfigInfo setTable(TableInfo table) {
        this.table = table;
        return this;
    }

    public ModuleInfo getModule() {
        return module;
    }

    public ConfigInfo setModule(ModuleInfo module) {
        this.module = module;
        return this;
    }

    public PackageInfo getPack() {
        return pack;
    }

    public ConfigInfo setPack(PackageInfo pack) {
        this.pack = pack;
        return this;
    }

    public GlobalInfo getGlobal() {
        return global;
    }

    public ConfigInfo setGlobal(GlobalInfo global) {
        this.global = global;
        return this;
    }
}
