package com.flx.netty.chat.generator.entity;

import lombok.Data;

/**
 * @Author: Fenglixiong
 * @Date: 2021/4/26 17:03
 * @Description:
 */
@Data
public class CustomEntity {

    //数据源设置
    private String ip;
    private String port;
    private String username;
    private String password;
    private String database;

    //表设置
    private String tablePrefix;
    private String tables;

    //模块设置
    private String parentModule;
    private String consoleModule;
    private String crudModule;
    private String clientModule;

    //包设置
    private String parentPackage;
    private String consolePackage;
    private String crudPackage;
    private String clientPackage;

    //其他设置
    private boolean override;
    private String removeColumn;

}
