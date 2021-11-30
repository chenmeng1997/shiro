package com.cm.shirotest.service.impl;

import com.cm.shirotest.entity.UserRole;
import com.cm.shirotest.dao.UserRoleMapper;
import com.cm.shirotest.service.IUserRoleService;
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
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {

}
