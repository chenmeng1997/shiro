package com.cm.shirotest.config.cache;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

/**
 * @author 陈萌
 * @Date 2021/12/9 0009 0:10
 * @ProjectName shiro-test
 */
@Data
@ConfigurationProperties(prefix = "shiro.redis")
public class ShiroRedisProperties implements Serializable {

    /**
     * redis链接地址
     */
    private String nodes;

    /**
     * 链接超时时间
     */
    private int connectTimeOut;

    /**
     * 连接池大小
     */
    private int connectPoolSize;

    /**
     * 初始化连接数
     */
    private int connectionMinimumIdleSize;

    /**
     * 返回超时时间
     */
    private int timeOut;

    /**
     * 全局超时时间
     */
    private long globalSessionTimeOut;

}
