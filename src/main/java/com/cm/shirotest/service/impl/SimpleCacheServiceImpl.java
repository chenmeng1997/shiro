package com.cm.shirotest.service.impl;

import com.cm.shirotest.service.ISimpleCacheService;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author 陈萌
 * @Date 2021/12/9 0009 21:32
 * @ProjectName shiro-test
 */
@Log4j2
@Service
public class SimpleCacheServiceImpl implements ISimpleCacheService {

    @Resource(name = "redissonClientForShiro")
    private RedissonClient redissonClient;
    @Autowired
    private DefaultWebSessionManager sessionManager;

    @Override
    public void createCache(String cacheName, Cache<Object, Object> cache) {
        long timeout = sessionManager.getGlobalSessionTimeout();
        RBucket<Object> bucket = redissonClient.getBucket(cacheName);
        bucket.trySet(cache, timeout, TimeUnit.MILLISECONDS);
    }

    @Override
    public Cache<Object, Object> getCache(String cacheName) {
        RBucket<Object> bucket = redissonClient.getBucket(cacheName);
        Cache<Object, Object> objectObjectCache = (Cache<Object, Object>) bucket.get();
        return objectObjectCache;
    }

    @Override
    public boolean removeCache(String cacheName) {
        RBucket<Object> bucket = redissonClient.getBucket(cacheName);
        return bucket.delete();
    }

    @Override
    public void updateCache(String cacheName, Cache<Object, Object> cache) {
        long timeout = sessionManager.getGlobalSessionTimeout();
        RBucket<Object> bucket = redissonClient.getBucket(cacheName);
        bucket.set(cache, timeout, TimeUnit.MILLISECONDS);
    }

}
