package com.flx.netty.chat.generator.entity;

import lombok.Data;

/**
 * @Author: Fenglixiong
 * @Date: 2021/4/26 17:04
 * @Description:
 */
@Data
public class SimpleEntity {

    //数据源设置
    private String ip;
    private String port;
    private String username;
    private String password;
    private String database;

    //表设置
    private String tablePrefix;
    private String tables;

    //路径设置
    private String parentModule;
    private String parentPackage;

    //其他设置
    private boolean override;

}
