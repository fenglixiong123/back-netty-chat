package com.flx.netty.chat.common.flyway.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author Fenglixiong
 * @Create 2021/4/20 2:01
 * @Description flyway一些常用的配置项目
 * 最重要的为：url,user,password,table,locations,validateOnMigrate
 **/
@Data
@ConfigurationProperties(prefix = "spring.datasource")
public class FlywayMysqlProperties {

    /**
     * 数据库地址
     */
    private String url = "";

    /**
     * 用户名
     */
    private String username = "root";

    /**
     * 密码
     */
    private String password = "welcome";

}
