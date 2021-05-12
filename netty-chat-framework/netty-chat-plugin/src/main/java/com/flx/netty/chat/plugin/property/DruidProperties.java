package com.flx.netty.chat.plugin.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author: Fenglixiong
 * @Date: 2021/5/12 19:44
 * @Description:
 */
@Data
@ConfigurationProperties(prefix = "spring.datasource")
public class DruidProperties {

    /**
     * 数据库地址
     */
    private String url = "jdbc:mysql://127.0.0.1:3306/web_auth?serverTimezone=UTC&useUnicode=true&characterEncoding=utf8&useSSL=false";

    /**
     * 用户名
     */
    private String username = "root";

    /**
     * 密码
     */
    private String password = "root123";

    /**
     * 数据库驱动包
     */
    private String driverClassName = "com.mysql.cj.jdbc.Driver";

    /**
     * 初始化
     */
    private int initialSize = 5;

    private int minIdle = 5;

    private int maxActive = 20;

    private int maxWait = 60000;

    private int timeBetweenEvictionRunsMillis = 60000;

    private int minEvictableIdleTimeMillis = 120000;

    private int maxEvictableIdleTimeMillis = 480000;

    private String validationQuery = "select 1";

    private boolean testWhileIdle = true;

    private boolean testOnBorrow = false;

    private boolean testOnReturn = false;

    private boolean keepAlive = true;

    private boolean removeAbandoned = true;

    private int removeAbandonedTimeout = 1800;

    private boolean logAbandoned = true;

    private boolean poolPreparedStatements = true;

    private int maxPoolPreparedStatementPerConnectionSize = 20;

    private int maxOpenPreparedStatements = 100;

    private String connectionProperties = "druid.stat.mergeSql=true;druid.stat.slowSqlMillis=2000";

    private String filters = "stat,wall,log4j";

}
