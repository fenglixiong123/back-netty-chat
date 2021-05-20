package com.flx.netty.chat.admin.common.config;

import com.flx.netty.chat.admin.common.property.DruidProperties;
import com.flx.netty.chat.admin.common.property.FlywayProperties;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

/**
 * @Author Fenglixiong
 * @Create 2021/5/20 3:02
 * @Description
 **/
@Slf4j
@Configuration
@EnableConfigurationProperties({FlywayProperties.class, DruidProperties.class})
public class FlywayConfiguration implements InitializingBean {

    @Autowired
    private DruidProperties druidProperties;
    @Autowired
    private FlywayProperties flywayProperties;

    @Transactional(rollbackFor = Exception.class)
    public void initFlyway(){
        FluentConfiguration fluentConfiguration = Flyway.configure()
                .dataSource(druidProperties.getUrl(),
                        druidProperties.getUsername(),
                        druidProperties.getPassword())
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

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info(">>>>>>>>>>>>>Flyway Successful<<<<<<<<<<<<");
    }
}
