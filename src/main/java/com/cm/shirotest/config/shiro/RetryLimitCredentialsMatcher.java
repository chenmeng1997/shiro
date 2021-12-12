package com.cm.shirotest.config.shiro;

import com.cm.shirotest.config.cache.CacheConstant;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * 做凭证匹配,限制密码登录次数
 *
 * @author 陈萌
 * @Date 2021/12/12 0012 17:19
 * @ProjectName shiro-test
 */
@Log4j2
@Component
public class RetryLimitCredentialsMatcher extends HashedCredentialsMatcher {

    @Resource(name = "redissonClientForShiro")
    private RedissonClient redissonClient;

    @Value("${shiro.retryLimit}")
    private long maxRetryLimit;
    @Value("${shiro.userLockTime}")
    private long userLockTime;

    /**
     * 凭证匹配
     *
     * @param token 令牌
     * @param info  认证信息
     * @return 认证结果
     */
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        boolean flag;
        // 获取用户名
        String userLoginName = (String) token.getPrincipal();
        // 登录次数
        RAtomicLong atomicLong = redissonClient.getAtomicLong(CacheConstant.GROUP_CAS_RETRY_LIMIT_NUM + userLoginName);
        long retryLimitNum = atomicLong.get();
        // 超出密码重试次数,retryLimitNum从0计数
        if (retryLimitNum > maxRetryLimit - 1) {
            atomicLong.expire(userLockTime, TimeUnit.MINUTES);
            log.error("用户{}密码重试次数已超过{}次！", userLoginName, maxRetryLimit);
            throw new ExcessiveAttemptsException("密码重试次数已超过:" + maxRetryLimit + "次，" + userLockTime + "分钟后重试");
        }
        // 重试次数加一
        atomicLong.incrementAndGet();
        atomicLong.expire(userLockTime, TimeUnit.MINUTES);
        // 密码验证
        flag = super.doCredentialsMatch(token, info);
        if (flag) {
            // 验证通过，删除重试次数缓存
            atomicLong.delete();
        }
        return flag;
    }

}
