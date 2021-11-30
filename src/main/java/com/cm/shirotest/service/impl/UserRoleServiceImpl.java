package com.cm.shirotest.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cm.shirotest.api.vo.PermissionRoleVo;
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

import java.util.List;
import java.util.Optional;
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
        // 用户
        User user = userMapper.selectById(userId);
        Optional.ofNullable(user)
                .filter(userTemp -> !userTemp.getDeleteState())
                .orElseThrow(() -> new RuntimeException("用户不存在"));
        BeanUtils.copyProperties(user, userRoleVo);
        // 角色
        QueryWrapper<UserRole> userRoleQueryWrapper = new QueryWrapper<>();
        userRoleQueryWrapper
                .eq("uid", userId)
                .eq("delete_state", 0)
                .orderByDesc("create_time");
        List<Long> roleIdList = userRoleMapper.selectList(userRoleQueryWrapper)
                .stream()
                .map(UserRole::getRid)
                .collect(Collectors.toList());
        // 角色、权限
        List<RoleVo> roleVoList = roleMapper.selectBatchIds(roleIdList)
                .stream()
                .filter(roleTemp -> !roleTemp.getDeleteState())
                .map(roleTemp -> {
                    RoleVo roleVo = new RoleVo();
                    BeanUtils.copyProperties(roleTemp, roleVo);
                    PermissionRoleVo permissionRoleVo = permissionRoleService.getPermissionRoleVoByRoleId(roleTemp.getId().longValue());
                    roleVo.setPermissionRoleVo(permissionRoleVo);
                    return roleVo;
                })
                .collect(Collectors.toList());
        // 用户、角色、权限
        userRoleVo.setRoleVoList(roleVoList);
        return userRoleVo;
    }
}
