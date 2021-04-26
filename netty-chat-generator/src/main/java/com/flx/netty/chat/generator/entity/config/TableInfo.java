package com.flx.netty.chat.generator.entity.config;

/**
 * @Author: Fenglixiong
 * @Date: 2021/4/26 18:47
 * @Description: 表设置
 */
public class TableInfo {

    /**
     * 去除表前缀
     */
    private String tablePrefix;

    /**
     * 要生成的表
     */
    private String tables;

    /**
     * 排除列
     */
    private String removeColumn;

    /**
     * 逻辑删除字段
     */
    private String deleted;

    /**
     * 乐观锁字段
     */
    private String version;

    public String getTablePrefix() {
        return tablePrefix;
    }

    public TableInfo setTablePrefix(String tablePrefix) {
        this.tablePrefix = tablePrefix;
        return this;
    }

    public String getTables() {
        return tables;
    }

    public TableInfo setTables(String tables) {
        this.tables = tables;
        return this;
    }

    public String getRemoveColumn() {
        return removeColumn;
    }

    public TableInfo setRemoveColumn(String removeColumn) {
        this.removeColumn = removeColumn;
        return this;
    }

    public String getDeleted() {
        return deleted;
    }

    public TableInfo setDeleted(String deleted) {
        this.deleted = deleted;
        return this;
    }

    public String getVersion() {
        return version;
    }

    public TableInfo setVersion(String version) {
        this.version = version;
        return this;
    }
}
