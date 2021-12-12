package com.cm.shirotest.config.shiro;

import com.cm.shirotest.config.cache.CacheConstant;
import com.cm.shirotest.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.session.ExpiredSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.redisson.api.RDeque;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author 陈萌
 * @Date 2021/12/12 0012 20:41
 * @ProjectName shiro-test
 */
@Data
@Log4j2
@AllArgsConstructor
public class KickedOutAuthorizationFilter extends AccessControlFilter {

    private DefaultWebSessionManager sessionManager;
    private RedissonClient redissonClient;
    private RedisSessionDao sessionDao;

    /**
     * 是否拒绝访问
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        return false;
    }

    /**
     * 拒绝访问
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        Subject subject = this.getSubject(request, response);
        // 登录状态
        boolean authenticated = subject.isAuthenticated();
        if (!authenticated) {
            return true;
        }
        // redis 创建队列
        // 用户账号
        User user = (User) subject.getPrincipal();
        String loginName = user.getUsername();
        // sessionID
        Serializable sessionId = subject.getSession().getId();
        // 当前用户队列
        RDeque<Serializable> deque = redissonClient.getDeque(CacheConstant.GROUP_CAS_KICKED_OUT_AUTHORIZATION + loginName);
        if (!deque.contains(sessionId)) {
            deque.addLast(sessionId);
        }
        Session session = null;
        // 超出限制删除会话
        if (deque.size() > 1) {
            Serializable firstSessionId = deque.getFirst();
            deque.remove(firstSessionId);
            // 获取会话
            try {
                session = sessionManager.getSession(new DefaultSessionKey(firstSessionId));
            } catch (UnknownSessionException e) {
                log.error("session失效！ e：{}", e);
            } catch (ExpiredSessionException e) {
                log.error("session过期！ e：{}", e);
            }
            if (Objects.nonNull(session)) {
                sessionDao.delete(session);
            }
        }
        return true;
    }
}
