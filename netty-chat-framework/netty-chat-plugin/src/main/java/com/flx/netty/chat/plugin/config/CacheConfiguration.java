package com.flx.netty.chat.plugin.config;

import com.flx.netty.chat.plugin.plugins.cache.CustomCacheManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import java.time.Duration;

/**
 * @Author Fenglixiong
 * @Create 2021/4/21 0:39
 * @Description 利用redis缓存中心来实现缓存
 *
 * @ConditionalOnBean 当redis存在时候才能实现缓存配置实例化
 *
 * 2.使用案例：
 *
 * @Cacheable(value = "user",key = "'user_'.concat(#root.args[0])",sync=true)
 * @CacheExpire(expire = 60)
 * public List<Map<String,Object>> getUser(Long userID){
 *     StringBuilder sql = new StringBuilder("");
 *     System.out.print(122);
 *     sql.append("select * from tbl_sys_user t where t.user_id = ? ");
 *     return jdbcTemplate.queryForList(sql.toString(),userID);
 * }
 *
 **/
@Slf4j
@EnableCaching
@Configuration
@ConditionalOnBean(value = RedisConfiguration.class)
public class CacheConfiguration extends CachingConfigurerSupport {

    @Autowired
    private RedisConnectionFactory factory;

    /**
     * 自定义生成缓存名称
     * @return
     */
    @Bean
    @Override
    public KeyGenerator keyGenerator() {
        return (self, method, objects) -> {
            StringBuilder sb = new StringBuilder(32);
            sb.append(self.getClass().getSimpleName());
            sb.append(".");
            sb.append(method.getName());
            if (objects.length > 0) {
                sb.append("#");
            }
            String sp = "";
            for (Object object : objects) {
                sb.append(sp);
                if (object == null) {
                    sb.append("NULL");
                } else {
                    sb.append(object.toString());
                }
                sp = ".";
            }
            return sb.toString();
        };
    }

    /**
     * 由于自己写了缓存管理中心，缓存的时候就会自动使用redis，
     * 而不会在用spring默认的缓存机制
     */
    @Bean
    public RedisCacheManager redisCacheManager(){

        RedisCacheWriter cacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(factory);

        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(5))//设置缓存的默认过期时间，也是使用Duration设置
                .serializeKeysWith(CustomCacheManager.STRING_PAIR)
                .serializeValuesWith(CustomCacheManager.FAST_JSON_PAIR)
                .disableCachingNullValues();

        return new CustomCacheManager(cacheWriter,config);
    }

}
