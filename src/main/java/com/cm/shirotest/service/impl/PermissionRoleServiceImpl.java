package com.cm.shirotest.service.impl;

import com.cm.shirotest.entity.PermissionRole;
import com.cm.shirotest.dao.PermissionRoleMapper;
import com.cm.shirotest.service.IPermissionRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 权限角色关系表 服务实现类
 * </p>
 *
 * @author 陈萌
 * @since 2021-11-30
 */
@Service
public class PermissionRoleServiceImpl extends ServiceImpl<PermissionRoleMapper, PermissionRole> implements IPermissionRoleService {

}
