package com.flx.netty.chat.plugin.plugins.cache;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.cache.Cache;

import java.util.concurrent.Callable;

/**
 * @Author: Fenglixiong
 * @Date: 2021/4/22 15:26
 * @Description:
 */
@Slf4j
public class RedisCacheWrapper implements Cache {

    private final Cache cache;

    RedisCacheWrapper(Cache cache) {
        this.cache = cache;
    }

    @NotNull
    @Override
    public String getName() {
        try {
            return cache.getName();
        } catch (Exception e) {
            log.error("getName ---> errMsg: {}", e.getMessage(), e);
            return null;
        }
    }

    @NotNull
    @Override
    public Object getNativeCache() {
        try {
            return cache.getNativeCache();
        } catch (Exception e) {
            log.error("getNativeCache ---> errMsg: {}", e.getMessage(), e);
            return null;
        }
    }

    @Override
    public ValueWrapper get(@NotNull Object o) {
        try {
            return cache.get(o);
        } catch (Exception e) {
            log.error("get ---> o: {}, errMsg: {}", o, e.getMessage(), e);
            return null;
        }
    }

    @Override
    public <T> T get(@NotNull Object o, Class<T> aClass) {
        try {
            return cache.get(o, aClass);
        } catch (Exception e) {
            log.error("get ---> o: {}, clazz: {}, errMsg: {}", o, aClass, e.getMessage(), e);
            return null;
        }
    }

    @Override
    public <T> T get(@NotNull Object o, @NotNull Callable<T> callable) {
        try {
            return cache.get(o, callable);
        } catch (Exception e) {
            log.error("get ---> o: {}, errMsg: {}", o, e.getMessage(), e);
            return null;
        }
    }

    @Override
    public void put(@NotNull Object o, Object o1) {
        try {
            cache.put(o, o1);
        } catch (Exception e) {
            log.error("put ---> o: {}, o1: {}, errMsg: {}", o, o1, e.getMessage(), e);
        }
    }

    @Override
    public ValueWrapper putIfAbsent(@NotNull Object o, Object o1) {
        try {
            return cache.putIfAbsent(o, o1);
        } catch (Exception e) {
            log.error("putIfAbsent ---> o: {}, o1: {}, errMsg: {}", o, o1, e.getMessage(), e);
            return null;
        }
    }

    @Override
    public void evict(@NotNull Object o) {
        try {
            cache.evict(o);
        } catch (Exception e) {
            log.error("evict ---> o: {}, errMsg: {}", o, e.getMessage(), e);
        }
    }

    @Override
    public void clear() {
        try {
            cache.clear();
        } catch (Exception e) {
            log.error("clear ---> errMsg: {}", e.getMessage(), e);
        }
    }
    
}
