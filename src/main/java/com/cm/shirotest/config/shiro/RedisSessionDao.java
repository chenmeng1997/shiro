package com.cm.shirotest.config.shiro;

import com.cm.shirotest.config.cache.CacheConstant;
import com.cm.shirotest.utils.RedissonSerializable;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * session 管理
 *
 * @author 陈萌
 * @Date 2021/12/9 0009 23:15
 * @ProjectName shiro-test
 */
@Data
@Log4j2
@Component("redisSessionDao")
public class RedisSessionDao extends AbstractSessionDAO {

    @Resource(name = "redissonClientForShiro")
    private RedissonClient redissonClient;

    /**
     * session 有效时长 毫秒
     */
    @Value("${shiro.globalSessionTimeOut}")
    private String globalSessionTimeOut;

    @Override
    protected Serializable doCreate(Session session) {
        log.debug("---创建全局session开始---");
        // 唯一SessionID
        Serializable sessionId = super.generateSessionId(session);
        assignSessionId(session, sessionId);
        // 放入缓存
        String sessionKey = CacheConstant.GROUP_CAS_SESSION_ID + sessionId.toString();
        try {
            session.setAttribute("sessionKey", sessionKey);
            session.setTimeout(Long.parseLong(globalSessionTimeOut));
            RBucket<String> bucket = redissonClient.getBucket(sessionKey);
            String jsonString = RedissonSerializable.serialize(session);
            bucket.trySet(jsonString, Long.parseLong(globalSessionTimeOut), TimeUnit.MILLISECONDS);
        } catch (RuntimeException e) {
            log.error("---创建全局session结束---e：{}", e.getMessage());
            throw e;
        }
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        log.debug("---读取全局session开始，sessionId：{}---", sessionId);
        String sessionKey = CacheConstant.GROUP_CAS_SESSION_ID + sessionId.toString();
        Session session;
        try {
            RBucket<String> bucket = redissonClient.getBucket(sessionKey);
            String sessionStr = bucket.get();
            session = (Session) RedissonSerializable.deserialize(sessionStr);
        } catch (RuntimeException e) {
            log.error("---读取全局session结束，e：{}---", e.getMessage());
            throw e;
        }
        return session;
    }

    @Override
    public void update(Session session) throws UnknownSessionException {
        log.debug("---全局session修改---");
        String sessionKey = CacheConstant.GROUP_CAS_SESSION_ID + session.getId().toString();
        session.setAttribute("sessionKey", sessionKey);
        session.setTimeout(Long.parseLong(globalSessionTimeOut));
        String jsonString = RedissonSerializable.serialize(session);
        try {
            RBucket<String> bucket = redissonClient.getBucket(sessionKey);
            bucket.set(jsonString, Long.parseLong(globalSessionTimeOut), TimeUnit.MILLISECONDS);
        } catch (RuntimeException e) {
            log.error("---全局session修改结束---e：{}", e.getMessage());
            throw e;
        }
    }

    @Override
    public void delete(Session session) {
        log.debug("---全局session删除---");
        String sessionKey = CacheConstant.GROUP_CAS_SESSION_ID + session.getId().toString();
        RBucket<Session> bucket = redissonClient.getBucket(sessionKey);
        bucket.delete();
        log.debug("---全局session删除结束---");
    }

    /**
     * 统计当前活跃用户数
     *
     * @return Session集合
     */
    @Override
    public Collection<Session> getActiveSessions() {
        return Collections.EMPTY_SET;
    }

}
