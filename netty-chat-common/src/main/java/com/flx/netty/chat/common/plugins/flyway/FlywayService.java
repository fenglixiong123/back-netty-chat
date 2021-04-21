package com.flx.netty.chat.common.plugins.flyway;

import com.flx.netty.chat.common.property.FlywayMysqlProperties;
import com.flx.netty.chat.common.property.FlywayProperties;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

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
 * 如果出现flyway刷新不及时问题，可以采用ApplicationContextInitializer方式让项目启动首先进行数据初始化工作
 *
 */
@Slf4j
@Component
public class FlywayService {

    @Autowired
    private FlywayMysqlProperties mysqlProperties;
    @Autowired
    private FlywayProperties flywayProperties;

    @Transactional(rollbackFor = Exception.class)
    public void initFlyway(){
        FluentConfiguration fluentConfiguration = Flyway.configure()
                .dataSource(mysqlProperties.getUrl(),
                        mysqlProperties.getUsername(),
                        mysqlProperties.getPassword())
                .baselineVersion(flywayProperties.getBaselineVersion())
                .baselineOnMigrate(flywayProperties.isBaselineOnMigrate())
                .validateOnMigrate(flywayProperties.isValidateOnMigrate())
                .outOfOrder(flywayProperties.isOutOfOrder())
                .encoding(flywayProperties.getEncoding())
                .table(flywayProperties.getTable())
                .locations(flywayProperties.getLocations())
                .cleanDisabled(flywayProperties.isCleanDisabled());
        Flyway flyway=fluentConfiguration.load();
        flyway.migrate();
    }

    @PostConstruct
    public void init(){
        initFlyway();
    }

}
