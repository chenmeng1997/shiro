package com.cm.shirotest.config.shiro;

import lombok.extern.log4j.Log4j2;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.redisson.api.RedissonClient;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author 陈萌
 * @Date 2021/12/11 0011 20:18
 * @ProjectName shiro-test
 */
@Log4j2
public class ShiroDefaultWebSessionManager
        extends DefaultWebSessionManager {

    @Resource(name = "redissonClientForShiro")
    private RedissonClient redissonClient;

    /**
     * 会话校验
     *
     * @param session 会话
     * @throws InvalidSessionException 无效的会话异常
     */
    @Override
    protected void doValidate(Session session) throws InvalidSessionException {

        super.doValidate(session);

        if (Objects.nonNull(session)) {
            String sessionKey = (String) session.getAttribute("sessionKey");
            long timeToLive = redissonClient.getBucket(sessionKey).remainTimeToLive();
            if (timeToLive <= 0) {
                log.error("会话过期,session: {}", session);
                throw new IllegalStateException("会话过期！");
            }
        } else {
            String msg = "会话不存在！";
            log.error("会话不存在,session: {}", session);
            throw new IllegalStateException(msg);
        }
    }

}
