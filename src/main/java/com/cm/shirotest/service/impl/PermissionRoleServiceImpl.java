package com.cm.shirotest.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cm.shirotest.api.vo.PermissionRoleVo;
import com.cm.shirotest.api.vo.PermissionVo;
import com.cm.shirotest.dao.PermissionMapper;
import com.cm.shirotest.dao.RoleMapper;
import com.cm.shirotest.entity.Permission;
import com.cm.shirotest.entity.PermissionRole;
import com.cm.shirotest.dao.PermissionRoleMapper;
import com.cm.shirotest.entity.Role;
import com.cm.shirotest.service.IPermissionRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.nio.file.OpenOption;
import java.util.ArrayList;
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
@RequiredArgsConstructor
public class PermissionRoleServiceImpl extends ServiceImpl<PermissionRoleMapper, PermissionRole> implements IPermissionRoleService {

    private final RoleMapper roleMapper;
    private final PermissionMapper permissionMapper;
    private final PermissionRoleMapper permissionRoleMapper;

    @Override
    public PermissionRoleVo getPermissionRoleVoByRoleId(Long roleId) {
        // 角色
        Role role = roleMapper.selectById(roleId);
        Optional.ofNullable(role)
                .filter(roleTemp -> !roleTemp.getDeleteState())
                .orElseThrow(() -> new RuntimeException("角色不存在"));
        // 权限ID集合
        QueryWrapper<PermissionRole> permissionRoleQueryWrapper = new QueryWrapper<>();
        permissionRoleQueryWrapper
                .eq("rid", roleId)
                .eq("delete_state", 0)
                .orderByDesc("create_time");
        List<Long> permissionIdList = permissionRoleMapper.selectList(permissionRoleQueryWrapper)
                .stream()
                .map(PermissionRole::getPid)
                .collect(Collectors.toList());
        // 权限
        List<Permission> permissionList = permissionMapper.selectBatchIds(permissionIdList)
                .stream()
                .filter(permissionTemp -> !permissionTemp.getDeleteState())
                .collect(Collectors.toList());
        return this.permissionRoleVoAssembly(role, permissionList);
    }

    // 数据组装
    private PermissionRoleVo permissionRoleVoAssembly(Role role, List<Permission> permissionList) {
        PermissionRoleVo permissionRoleVo = new PermissionRoleVo();
        List<PermissionVo> permissionVoList = new ArrayList<>(permissionList.size());
        if (role != null && !CollectionUtils.isEmpty(permissionList)) {
            for (Permission permission : permissionList) {
                PermissionVo permissionVo = new PermissionVo();
                BeanUtils.copyProperties(permission, permissionVo);
                permissionVoList.add(permissionVo);
            }
            permissionRoleVo.setPermissionVoList(permissionVoList);
            permissionRoleVo.setRid(role.getId());
            permissionRoleVo.setRname(role.getRname());
            permissionRoleVo.setSummary(role.getSummary());
        }
        return permissionRoleVo;
    }

}
