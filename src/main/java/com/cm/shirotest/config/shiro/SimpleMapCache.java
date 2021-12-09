package com.cm.shirotest.config.shiro;

import org.apache.shiro.cache.MapCache;

import java.io.Serializable;
import java.util.Map;

/**
 * @author 陈萌
 * @Date 2021/12/9 0009 23:02
 * @ProjectName shiro-test
 */
public class SimpleMapCache extends MapCache implements Serializable {
    public SimpleMapCache(String name, Map backingMap) {
        super(name, backingMap);
    }
}
