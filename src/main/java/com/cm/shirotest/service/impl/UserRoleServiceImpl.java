package com.cm.shirotest.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cm.shirotest.api.vo.PermissionRoleVo;
import com.cm.shirotest.api.vo.PermissionVo;
import com.cm.shirotest.api.vo.RoleVo;
import com.cm.shirotest.api.vo.UserRoleVo;
import com.cm.shirotest.dao.RoleMapper;
import com.cm.shirotest.dao.UserMapper;
import com.cm.shirotest.dao.UserRoleMapper;
import com.cm.shirotest.entity.User;
import com.cm.shirotest.entity.UserRole;
import com.cm.shirotest.service.IPermissionRoleService;
import com.cm.shirotest.service.IUserRoleService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 权限角色关系表 服务实现类
 * </p>
 *
 * @author 陈萌
 * @since 2021-11-30
 */
@Log4j2
@Service
@AllArgsConstructor
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {

    private final UserMapper userMapper;
    private final RoleMapper roleMapper;
    private final UserRoleMapper userRoleMapper;
    private final IPermissionRoleService permissionRoleService;

    @Override
    public UserRoleVo getUserRoleByUserId(Integer userId) {
        UserRoleVo userRoleVo = new UserRoleVo();
        Set<RoleVo> roleSet = new HashSet<>();
        Set<PermissionVo> permissionSet = new HashSet<>();
        // 用户
        User user = userMapper.selectById(userId);
        Optional.ofNullable(user)
                .filter(userTemp -> !userTemp.getDeleteState())
                .orElseThrow(() -> new RuntimeException("用户不存在"));
        BeanUtils.copyProperties(user, userRoleVo);
        // 角色
        LambdaQueryWrapper<UserRole> userRoleQueryWrapper = new QueryWrapper<UserRole>().lambda();
        userRoleQueryWrapper
                .eq(UserRole::getUid, userId)
                .eq(UserRole::getDeleteState, 0)
                .orderByDesc(UserRole::getCreateTime);
        List<Long> roleIdList = userRoleMapper.selectList(userRoleQueryWrapper)
                .stream()
                .map(UserRole::getRid)
                .collect(Collectors.toList());
        // 角色、权限
        roleMapper.selectBatchIds(roleIdList)
                .stream()
                .filter(roleTemp -> !roleTemp.getDeleteState())
                .forEach(roleTemp -> {
                    RoleVo roleVo = new RoleVo();
                    BeanUtils.copyProperties(roleTemp, roleVo);
                    PermissionRoleVo permissionRoleVo = permissionRoleService.getPermissionRoleVoByRoleId(roleTemp.getId().longValue());
                    List<PermissionVo> permissionVoList = permissionRoleVo.getPermissionVoList();
                    roleSet.add(roleVo);
                    if (!CollectionUtils.isEmpty(permissionVoList)) {
                        permissionSet.addAll(permissionVoList);
                    }
                });
        // 用户、角色、权限
        userRoleVo.setRoleSet(roleSet);
        userRoleVo.setPermissionSet(permissionSet);
        return userRoleVo;
    }
}
