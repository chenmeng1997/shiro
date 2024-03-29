package com.cm.shirotest.config.shiro;

import lombok.extern.log4j.Log4j2;

import javax.annotation.Resource;
import javax.servlet.Filter;

import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.redisson.api.RedissonClient;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author 陈萌
 * @version 1.0
 * @date 2021/12/1 0001 22:48
 * @modelName shiro-test
 */
@Log4j2
@Configuration
public class ShiroConfig {

    @Value("${shiro.globalSessionTimeOut}")
    private String globalSessionTimeOut;

    @Resource(name = "redissonClientForShiro")
    private RedissonClient redissonClient;

    /**
     * 设置过滤器，权限校验方式
     *
     * @param securityManager 安全管理器
     * @return Shiro过滤器工厂
     */
    @Bean(name = "shiroFilterFactoryBean")
    public ShiroFilterFactoryBean shiroFilter(@Qualifier(value = "getSecurityManager") SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        shiroFilterFactoryBean.setLoginUrl("/login");
        shiroFilterFactoryBean.setUnauthorizedUrl("/notRole");

        // 自定义过滤器
        Map<String, Filter> filterMap = this.getFilterMap();
        shiroFilterFactoryBean.setFilters(filterMap);

        // 过滤器链
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        // <!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
        filterChainDefinitionMap.put("/user/login", "anon,kicked");
        filterChainDefinitionMap.put("/test/**", "anon");
        filterChainDefinitionMap.put("/", "anon");
        filterChainDefinitionMap.put("/api/**", "anon");

        filterChainDefinitionMap.put("/admin/**", "authc");
        filterChainDefinitionMap.put("/user/**", "authc");
        //主要这行代码必须放在所有权限设置的最后，不然会导致所有 url 都被拦截 剩余的都需要认证
        filterChainDefinitionMap.put("/**", "authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;

    }

    /**
     * 安全管理器
     *
     * @param userRealm 授权领域
     * @return 默认安全管理器
     */
    @Bean(name = "getSecurityManager")
    public DefaultWebSecurityManager getSecurityManager(@Qualifier(value = "userRealm") UserRealm userRealm) {
        DefaultWebSecurityManager defaultSecurityManager = new DefaultWebSecurityManager();
        defaultSecurityManager.setRealm(userRealm);
        defaultSecurityManager.setSessionManager(getSessionManager());
        //defaultSecurityManager.setRememberMeManager(rememberMeManager());
        return defaultSecurityManager;
    }

    /**
     * 认证、授权
     *
     * @return 授权领域
     */
    @Bean(name = "userRealm")
    public UserRealm getUserRealm() {
        UserRealm userRealm = new UserRealm();
        //配置使用哈希密码匹配
        userRealm.setCredentialsMatcher(getCredentialsMatcher());
        return userRealm;
    }

    /**
     * 密码校验
     *
     * @return 凭证匹配器
     */
    @Bean
    public CredentialsMatcher getCredentialsMatcher() {
        RetryLimitCredentialsMatcher credentialsMatcher = new RetryLimitCredentialsMatcher();
        // 散列算法，这里使用更安全的sha256算法
        credentialsMatcher.setHashAlgorithmName(Md5Hash.ALGORITHM_NAME);
        // 数据库存储的密码字段使用HEX还是BASE64方式加密
        credentialsMatcher.setStoredCredentialsHexEncoded(true);
        // 散列迭代次数
        credentialsMatcher.setHashIterations(2);
        return credentialsMatcher;
    }

    /**
     * session的增删改查
     */
    @Bean(name = "getSessionDao")
    public RedisSessionDao getSessionDao() {
        RedisSessionDao sessionDao = new RedisSessionDao();
        sessionDao.setGlobalSessionTimeOut(globalSessionTimeOut);
        return sessionDao;
    }

    /**
     * Session 管理器
     *
     * @return 管理器
     */
    @Bean
    public DefaultWebSessionManager getSessionManager() {
        ShiroDefaultWebSessionManager sessionManager = new ShiroDefaultWebSessionManager();
        sessionManager.setSessionDAO(getSessionDao());
        sessionManager.setSessionValidationSchedulerEnabled(false);
        sessionManager.setGlobalSessionTimeout(Long.parseLong(globalSessionTimeOut));

        sessionManager.setSessionIdCookieEnabled(true);
        Cookie cookie = new SimpleCookie();
        // 修改默认session名称,这里没有设置session的cookie生命周期，默认就是 浏览器关闭时候或者服务器设置的 session 的时间
        cookie.setName("shiroCookie");
        cookie.setHttpOnly(true);
        sessionManager.setSessionIdCookie(cookie);
        return sessionManager;
    }

    /**
     * rememberMe管理器, cipherKey生成见{@code Base64Test.java}
     */
    @Bean
    public CookieRememberMeManager rememberMeManager() {
        CookieRememberMeManager manager = new CookieRememberMeManager();
        manager.setCipherKey(Base64.decode("Z3VucwAAAAAAAAAAAAAAAA=="));
        manager.setCookie(rememberMeCookie());
        return manager;
    }

    /**
     * 记住密码Cookie
     */
    @Bean
    public SimpleCookie rememberMeCookie() {
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        simpleCookie.setHttpOnly(true);
        simpleCookie.setMaxAge(Integer.parseInt(globalSessionTimeOut));
        return simpleCookie;
    }

    /**
     * 开启对shior注解的支持
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier(value = "getSecurityManager") SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

    /**
     * Shiro注解Aop自动代理创建器
     *
     * @return 默认顾问自动代理创建器
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator proxyCreator = new DefaultAdvisorAutoProxyCreator();
        proxyCreator.setProxyTargetClass(true);
        return proxyCreator;
    }

    private Map<String, Filter> getFilterMap() {
        Map<String, Filter> filterMap = new HashMap<>();
        KickedOutAuthorizationFilter kickedOutFilter = new KickedOutAuthorizationFilter(getSessionManager(), redissonClient, getSessionDao());
        filterMap.put("kicked", kickedOutFilter);
        return filterMap;
    }

}
