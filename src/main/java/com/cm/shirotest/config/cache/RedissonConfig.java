package com.cm.shirotest.config.cache;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 陈萌
 * @Date 2021/12/9 0009 0:43
 * @ProjectName shiro-test
 */
@Log4j2
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties({ShiroRedisProperties.class})
public class RedissonConfig {

    private final ShiroRedisProperties shiroRedisProperties;

    @Bean(name = "redissonClientForShiro")
    public RedissonClient getRedissonClient() {

        // 节点信息
        String[] nodes = shiroRedisProperties.getNodes().split(",");
        // 配置信息
        Config config = new Config();
        if (nodes.length == 1) {
            // 单机
            config.useSingleServer()
                    .setDatabase(0)
                    .setAddress(nodes[0])
                    .setConnectTimeout(shiroRedisProperties.getConnectTimeOut())
                    .setConnectionMinimumIdleSize(shiroRedisProperties.getConnectionMinimumIdleSize())
                    .setConnectionPoolSize(shiroRedisProperties.getConnectPoolSize())
                    .setTimeout(shiroRedisProperties.getTimeOut());
        } else if (nodes.length > 0) {
            // 集群
            config.useClusterServers()
                    .addNodeAddress(nodes)
                    .setConnectTimeout(shiroRedisProperties.getConnectTimeOut())
                    .setMasterConnectionMinimumIdleSize(shiroRedisProperties.getConnectionMinimumIdleSize())
                    .setMasterConnectionPoolSize(shiroRedisProperties.getConnectPoolSize())
                    .setTimeout(shiroRedisProperties.getTimeOut());
        } else {
            log.error("redission节点信息为空!");
            return null;
        }
        // redis客户端交给Spring容器处理
        return Redisson.create(config);
    }

}
