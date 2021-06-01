package com.flx.netty.chat.admin.common.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusPropertiesCustomizer;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.flx.netty.chat.admin.common.annotation.DaoMapper;
import com.flx.netty.chat.admin.common.property.MybatisProperties;
import com.flx.netty.chat.common.utils.system.PropertyUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

/**
 * @Author Fenglixiong
 * @Create 2021/5/20 3:02
 * @Description
 **/
@Slf4j
@Configuration
@EnableConfigurationProperties({MybatisProperties.class})
public class MybatisConfiguration implements InitializingBean {

    /**
     * 配置mybatis的配置项
     */
    @Bean
    public ConfigurationCustomizer configurationCustomizer(MybatisProperties properties) {
        return configuration -> {
            configuration.setMapUnderscoreToCamelCase(true);
            configuration.setCacheEnabled(false);
            //配置是否打印log日志，默认不打印
            boolean logOpen = properties.isLogOpen();
            log.info("Sql print status >>>>> logOpen = {}",logOpen);
            if(logOpen) {
                configuration.setLogImpl(StdOutImpl.class);
            }
        };
    }

    /**
     * 配置MybatisPlus的配置项
     * @return
     */
    @Bean
    public MybatisPlusPropertiesCustomizer plusPropertiesCustomizer() {
        return plusProperties -> {
            plusProperties.setMapperLocations(new String[]{"classpath:mapper/*.xml"});
            plusProperties.getGlobalConfig().getDbConfig().setIdType(IdType.AUTO);
            plusProperties.getGlobalConfig().getDbConfig().setUpdateStrategy(FieldStrategy.NOT_NULL);
            //增加字段填充处理
            //plusProperties.getGlobalConfig().setMetaObjectHandler(new SimpleMetaObjectHandler());
            //增加逻辑删除支持
            //plusProperties.getGlobalConfig().getDbConfig().setLogicDeleteField("deleted");
            //plusProperties.getGlobalConfig().getDbConfig().setLogicDeleteValue("1");
            //plusProperties.getGlobalConfig().getDbConfig().setLogicDeleteValue("0");
        };
    }


    /**
     * 扫描mybatis的dao层中含有注解@DaoMapper的类
     *
     * 配置扫描 com.flx.dao.package = ${value}
     * 默认扫描 com.flx.**.dao
     *
     * @return MapperScannerConfigurer
     */
    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        String basePackage = PropertyUtils.get("com.flx.dao.package","com.flx.**.dao");
        log.info("MapperScannerConfigurer basePackage = {}",basePackage);
        MapperScannerConfigurer scannerConfigurer = new MapperScannerConfigurer();
        scannerConfigurer.setBasePackage(Objects.requireNonNull(basePackage));
        scannerConfigurer.setAnnotationClass(DaoMapper.class);
        return scannerConfigurer;
    }

    /**
     * 添加自动分页插件，会在mybatis拦截器中添加分页实现
     * 添加乐观锁插件，会在执行updateById,update(entity, wrapper)的时候使用乐观锁
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(){
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));//避免每次分页都去抓取数据库类型
        //interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());//注册乐观锁插件
        return interceptor;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info(">>>>>>>>>>>>>Mybatis Successful<<<<<<<<<<<<");
    }
}
