package com.flx.netty.chat.common.flyway;

import com.flx.netty.chat.common.utils.system.PropertyUtils;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.MigrationVersion;
import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: Fenglixiong
 * @Date: 2021/4/19 0:52
 * @Description:
 *
 * 配置文件添加如下配置
 * 设置flyway关闭，采用代码方式加载
 * spring.flyway.enabled=false
 * 设置flyway表名，会自动创建
 * spring.flyway.table=scaffold_flyway_history
 * 设置sql文件存放位置
 * spring.flyway.locations=classpath:db/migration
 * 设置flyway连接数据库url
 * spring.flyway.url
 * 设置flyway用使用数据库户名
 * spring.flyway.user
 * 设置flyway使用数据库密码
 * spring.flyway.password
 *
 */
@Slf4j
@Component
public class FlywayService {

    /**
     * 启动flyway会去读取这几个配置项
     * spring.flyway.url
     * spring.flyway.user
     * spring.flyway.password
     * spring.flyway.table
     * spring.flyway.locations
     */
    @Transactional(rollbackFor = Exception.class)
    public void initFlyway(){
        String url = PropertyUtils.getProperty("spring.flyway.url");
        String user = PropertyUtils.getProperty("spring.flyway.user");
        String password = PropertyUtils.getProperty("spring.flyway.password");
        String flywayTable = PropertyUtils.getProperty("spring.flyway.table");
        String locations = PropertyUtils.getProperty("spring.flyway.locations");
        FluentConfiguration fluentConfiguration = Flyway.configure()
                .dataSource(url,user,password)
                .baselineDescription("flyway-baseline init")
                .baselineVersion(MigrationVersion.fromVersion("1"))
                .baselineOnMigrate(true)
                .validateOnMigrate(false)
                .outOfOrder(true)
                .encoding("UTF-8")
                .table(flywayTable)
                .locations(locations)
                .cleanDisabled(true);
        Flyway flyway=fluentConfiguration.load();
        flyway.migrate();
        log.info("*************************************************");
        log.info("*                                               *");
        log.info("*                 Flyway Success                *");
        log.info("*                                               *");
        log.info("*************************************************");
    }


}
