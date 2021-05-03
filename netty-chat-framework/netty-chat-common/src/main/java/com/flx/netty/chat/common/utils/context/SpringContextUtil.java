package com.flx.netty.chat.common.utils.context;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @Author: Fenglixiong
 * @Date: 2020/5/22 18:26
 * @Description:
 */
@Slf4j
public class SpringContextUtil implements ApplicationContextAware, InitializingBean {

    private static ApplicationContext applicationContext;

    /**
     * 注入spring上下文对象
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextUtil.applicationContext = applicationContext;
    }

    /**
     * 获取spring容器中的bean,通过bean名称获取
     * @param beanName
     * @return
     */
    public static Object getBean(String beanName){
        return applicationContext.getBean(beanName);
    }

    /**
     * 获取spring容器中的bean, 通过bean类型获取
     * @param beanClass
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> beanClass){
        return applicationContext.getBean(beanClass);
    }

    /**
     * 获取spring容器中的bean, 通过bean名称和bean类型精确获取
     * @param beanName
     * @param beanClass
     * @param <T>
     * @return
     */
    public static <T> T getBean(String beanName,Class<T> beanClass){
        return applicationContext.getBean(beanName,beanClass);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("=============注册SpringContextUtil成功===========");
    }
}
