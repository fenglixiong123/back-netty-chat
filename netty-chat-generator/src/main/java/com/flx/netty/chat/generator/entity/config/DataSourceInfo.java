package com.flx.netty.chat.generator.entity.config;

import org.apache.commons.lang3.StringUtils;

/**
 * @Author: Fenglixiong
 * @Date: 2021/4/26 18:48
 * @Description: 数据源设置
 */
public class DataSourceInfo {

    /**
     * IP地址
     */
    private String ip;

    /**
     * 数据库端口
     */
    private String port;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 数据库连接地址
     */
    private String url;

    /**
     * 数据库驱动器
     */
    private String driveName;

    /**
     * 数据库
     */
    private String database;

    public String getIp() {
        return ip;
    }

    public DataSourceInfo setIp(String ip) {
        this.ip = ip;
        return this;
    }

    public String getPort() {
        return port;
    }

    public DataSourceInfo setPort(String port) {
        this.port = port;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public DataSourceInfo setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public DataSourceInfo setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getDatabase() {
        return database;
    }

    public DataSourceInfo setDatabase(String database) {
        this.database = database;
        return this;
    }

    public String getUrl(){
        if(StringUtils.isBlank(url)) {
            return "jdbc:mysql://" + ip + ":" + port + "/" + database + "?serverTimezone=UTC&useUnicode=true&characterEncoding=utf8&useSSL=false";
        }
        return url;
    }

    public DataSourceInfo setUrl(String url) {
        this.url = url;
        return this;
    }

    public DataSourceInfo setDriveName(String driveName) {
        this.driveName = driveName;
        return this;
    }


    public String getDriveName(){
        if(StringUtils.isBlank(driveName)){
            return "com.mysql.cj.jdbc.Driver";
        }
        return driveName;
    }

}
