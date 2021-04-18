package com.flx.netty.chat.common.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.flx.netty.chat.common.annotion.DaoMapper;
import com.flx.netty.chat.common.utils.system.PropertyUtils;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Slf4j
@Configuration
public class MybatisPlusConfiguration {

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
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

}
