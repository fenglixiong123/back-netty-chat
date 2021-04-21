package com.flx.netty.chat.plugin.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusPropertiesCustomizer;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.flx.netty.chat.plugin.annotion.mybatis.DaoMapper;
import com.flx.netty.chat.common.utils.system.PropertyUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

/**
 * MybatisPlus配置类
 *
 * 可以配置文件中配置flx.mybatis.log=true开启sql日志打印
 *
 */
@Slf4j
@Configuration
public class MybatisPlusConfiguration {
    /**
     * 配置mybatis的配置项
     */
    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
        return configuration -> {
            configuration.setMapUnderscoreToCamelCase(true);
            configuration.setCacheEnabled(false);
            //配置是否打印log日志，默认不打印
            boolean logOpen = PropertyUtils.getBoolean("flx.mybatis.log",false);
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
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(){
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));//避免每次分页都去抓取数据库类型
        return interceptor;
    }

}
