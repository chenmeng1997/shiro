package com.cm.shirotest.service.impl;

import com.cm.shirotest.entity.Role;
import com.cm.shirotest.dao.RoleMapper;
import com.cm.shirotest.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author 陈萌
 * @since 2021-11-30
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

}
