package com.cm.shirotest.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cm.shirotest.enums.DeleteStateEnum;
import com.cm.shirotest.api.request.UserLoginRequest;
import com.cm.shirotest.api.vo.UserVo;
import com.cm.shirotest.config.cache.CacheConstant;
import com.cm.shirotest.config.shiro.SimpleMapCache;
import com.cm.shirotest.dao.UserMapper;
import com.cm.shirotest.entity.User;
import com.cm.shirotest.service.ISimpleCacheService;
import com.cm.shirotest.service.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author 陈萌
 * @since 2021-11-30
 */
@Log4j2
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    private final UserMapper userMapper;
    private final ISimpleCacheService cacheService;

    @Override
    public String userLogin(UserLoginRequest request) {

        // 从SecurityUtils里边创建一个 subject
        Subject subject = SecurityUtils.getSubject();
        boolean authenticated = subject.isAuthenticated();
        if (authenticated) {
            return "登录成功(已登录)";
        }
        // 在认证提交前准备 token（令牌）
        UsernamePasswordToken token = new UsernamePasswordToken(request.getUsername(), request.getPassword());
        // 执行认证登陆
        try {
            subject.login(token);
            //subject.getPreviousPrincipals().getPrimaryPrincipal();
        } catch (UnknownAccountException uae) {
            return "未知账户";
        } catch (IncorrectCredentialsException ice) {
            return "密码不正确";
        } catch (LockedAccountException lae) {
            return "账户已锁定";
        } catch (ExcessiveAttemptsException eae) {
            return eae.getMessage();
        } catch (AuthenticationException ae) {
            return "用户名或密码不正确！";
        }
        if (subject.isAuthenticated()) {
            return "登录成功";
        } else {
            token.clear();
            return "登录失败";
        }
    }

    @Override
    public String userLoginOut() {
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.logout();
        } catch (RuntimeException e) {
            log.error("用户登出失败！异常信息：{}", e.getMessage());
            throw e;
        }
        return "用户登出成功！";
    }

    @Override
    public UserVo getUserInfoById(Integer id) {
        log.info("用户详情，用户ID：{}", id);
        UserVo userVo = new UserVo();
        User user = userMapper.selectById(id);
        BeanUtils.copyProperties(user, userVo);
        log.info("用户详情：{}", userVo);
        return userVo;
    }

    @Override
    public User getUserInfoByLoginName(String loginName) {
        User user;
        String key = CacheConstant.USER_LOGIN_KEY + loginName;
        Cache<Object, Object> cache = cacheService.getCache(key);
        if (Objects.nonNull(cache)) {
            user = (User) cache.get(key);
        } else {
            LambdaQueryWrapper<User> queryWrapper = new QueryWrapper<User>().lambda();
            queryWrapper
                    .eq(User::getUsername, loginName)
                    .eq(User::getDeleteState, DeleteStateEnum.NO);
            user = userMapper.selectOne(queryWrapper);
            if (Objects.nonNull(user)) {
                Map<String, User> map = new HashMap<>();
                map.put(key, user);
                cacheService.createCache(key, new SimpleMapCache(key, map));
            }
        }
        return user;
    }

}
