package com.cm.shirotest.config.shiro;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cm.shirotest.api.vo.RoleVo;
import com.cm.shirotest.api.vo.UserRoleVo;
import com.cm.shirotest.entity.User;
import com.cm.shirotest.enums.DeleteStateEnum;
import com.cm.shirotest.service.IUserRoleService;
import com.cm.shirotest.service.IUserService;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author 陈萌
 * @version 1.0
 * @date 2021/12/1 0001 22:50
 * @modelName shiro-test
 */
@Log4j2
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private IUserService userService;
    @Autowired
    private IUserRoleService userRoleService;

    /**
     * 授权
     *
     * @param principalCollection 认证主体信息
     * @return 授权信息
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        User user = (User) principalCollection.getPrimaryPrincipal();
        UserRoleVo userRolePermission = userRoleService.getUserRoleByUserId(user.getId());
        // 角色
        Set<String> roles = userRolePermission.getRoleSet()
                .stream()
                .map(RoleVo::getRname)
                .collect(Collectors.toSet());
        authorizationInfo.setRoles(roles);
        // 权限
        Set<String> permissions = new HashSet<>();
        userRolePermission.getPermissionSet()
                .forEach(permissionVo -> {
                    String perms = permissionVo.getPerms();
                    if (StringUtils.isNotBlank(perms)) {
                        permissions.addAll(Arrays.asList(perms.split(",")));
                    }
                });
        authorizationInfo.setStringPermissions(permissions);
        return authorizationInfo;
    }

    /**
     * 认证
     *
     * @param authenticationToken 认证令牌
     * @return 认证信息
     * @throws AuthenticationException 认证异常信息
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String userName = (String) authenticationToken.getPrincipal();
        log.info("用户认证，登录账户：{}", userName);
        //根据用户名从数据库获取密码
        LambdaQueryWrapper<User> queryWrapper = new QueryWrapper<User>().lambda();
        queryWrapper
                .eq(User::getUsername, userName)
                .eq(User::getDeleteState, DeleteStateEnum.NO.getValue());
        User user = userService.getOne(queryWrapper);
        // 认证信息
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user,
                user.getPassword(),
                ByteSource.Util.bytes(user.getSalt()),
                this.getName());
        log.info("用户认证，认证信息：{}", authenticationInfo);
        return authenticationInfo;
    }

}
