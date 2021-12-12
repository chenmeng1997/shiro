package com.cm.shirotest.config.cache;

/**
 * 缓存键值常量类
 *
 * @author 陈萌
 * @Date 2021/12/9 0009 22:35
 * @ProjectName shiro-test
 */
public interface CacheConstant {

    String GROUP_CAS = "group_shiro:";

    /**
     * 用户登录
     */
    String USER_LOGIN_KEY = GROUP_CAS + "user_login:";

    /**
     * 全局sessionID
     */
    String GROUP_CAS_SESSION_ID = GROUP_CAS + "global:sessionId:";

    /**
     * 用户密码重试次数
     */
    String GROUP_CAS_RETRY_LIMIT_NUM = GROUP_CAS + "retryLimitNum:";

}
