package com.cm.shirotest.service;

import org.apache.shiro.cache.Cache;

/**
 * @author 陈萌
 * @Date 2021/12/9 0009 1:35
 * @ProjectName shiro-test
 */
public interface ISimpleCacheService {

    /**
     * 添加缓存
     * @param cacheName 缓存名
     * @param cache 缓存
     */
    void createCache(String cacheName, Cache<Object, Object> cache);

    /**
     * 获取缓存
     * @param cacheName 缓存名
     * @return 缓存
     */
    Cache<Object, Object> getCache(String cacheName);

    /**
     * 删除缓存
     * @param cacheName 缓存名
     */
    boolean removeCache(String cacheName);

    /**
     * 更新缓存
     * @param cacheName 缓存名
     * @param cache 缓存
     */
    void updateCache(String cacheName, Cache<Object, Object> cache);

}
