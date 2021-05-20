package com.flx.netty.chat.admin.common.property;

import lombok.Data;
import org.flywaydb.core.api.MigrationVersion;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author Fenglixiong
 * @Create 2021/4/20 2:01
 * @Description flyway一些常用的配置项目
 * 最重要的为：url,user,password,table,locations,validateOnMigrate
 **/
@Data
@ConfigurationProperties(prefix = "flx.flyway",ignoreInvalidFields = true)
public class FlywayProperties {

    /**
     * 数据库地址
     */
    private String url;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 数据库驱动包
     */
    private String driverClassName = "com.mysql.cj.jdbc.Driver";

    /**
     * flyway表名字
     */
    private String table = "flyway_history";

    /**
     * flyway存放位置
     */
    private String locations = "classpath:db/migration";

    /**
     * 是否自动执行基准迁移，默认false.
     */
    private boolean baselineOnMigrate = true;

    /**
     * 迁移时是否校验
     */
    private boolean validateOnMigrate = false;

    /**
     * 是否允许无序迁移
     */
    private boolean outOfOrder = true;

    /**
     * 禁止清理表
     */
    private boolean cleanDisabled = true;

    /**
     * 迁移时的编码
     */
    private String encoding = "UTF-8";

    /**
     * 开始执行基准迁移时对现有的schema的版本打标签
     */
    private MigrationVersion baselineVersion = MigrationVersion.fromVersion("1");

}
