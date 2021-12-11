package com.cm.shirotest.config.shiro;

import com.alibaba.fastjson.JSONObject;
import com.cm.shirotest.config.cache.CacheConstant;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;

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
public class RedisSessionDao extends AbstractSessionDAO {

    @Resource(name = "redissonClientForShiro")
    private RedissonClient redissonClient;

    /**
     * session 有效时长 毫秒
     */
    private long globalSessionTimeOut;

    @Override
    protected Serializable doCreate(Session session) {
        log.debug("---创建全局session开始---");
        // 唯一SessionID
        Serializable sessionId = super.generateSessionId(session);
        assignSessionId(session, sessionId);
        // 放入缓存
        String sessionKey = CacheConstant.GROUP_CAS_SESSION_ID + sessionId.toString();
        RBucket<Session> bucket = redissonClient.getBucket(sessionKey);
        boolean trySet = bucket.trySet(session, globalSessionTimeOut, TimeUnit.MICROSECONDS);
        log.debug("---创建全局session结束---sessionId：{}, trySet:{}", sessionId, trySet);
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        log.debug("---读取全局session开始，sessionId：{}---", sessionId);
        String sessionKey = CacheConstant.GROUP_CAS_SESSION_ID + sessionId.toString();
        RBucket<Session> bucket = redissonClient.getBucket(sessionKey);
        Session session = bucket.get();
        log.debug("---读取全局session结束，session：{}---", JSONObject.toJSONString(session));
        return session;
    }

    @Override
    public void update(Session session) throws UnknownSessionException {
        log.debug("---全局session修改---");
        String sessionKey = CacheConstant.GROUP_CAS_SESSION_ID + session.getId().toString();
        RBucket<Session> bucket = redissonClient.getBucket(sessionKey);
        boolean trySet = bucket.trySet(session, globalSessionTimeOut, TimeUnit.MICROSECONDS);
        log.debug("---全局session修改结束---session：{},trySet:{}", session, trySet);
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
