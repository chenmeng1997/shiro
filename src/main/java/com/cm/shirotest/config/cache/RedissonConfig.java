package com.cm.shirotest.config.cache;

import lombok.extern.log4j.Log4j2;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author 陈萌
 * @Date 2021/12/9 0009 0:43
 * @ProjectName shiro-test
 */
@Log4j2
@Configuration
@PropertySource(value = "classpath:application-test.yml")
public class RedissonConfig {

    @Bean(name = "redissonClientForShiro")
    public RedissonClient redissonClient() {
        Config config = new Config();
        String url = "redis://127.0.0.1:6379";
        // 这里以单台redis服务器为例
        config.useSingleServer()
                .setAddress(url)
//                .setPassword("Chen1997")
                .setDatabase(0);
        try {
            return Redisson.create(config);
        } catch (Exception e) {
            log.error("RedissonClient init redis url:[{}], Exception:", url, e);
            return null;
        }
    }

}
