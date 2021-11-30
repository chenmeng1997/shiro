package com.cm.shirotest.service.impl;

import com.cm.shirotest.entity.Permission;
import com.cm.shirotest.dao.PermissionMapper;
import com.cm.shirotest.service.IPermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 权限表 服务实现类
 * </p>
 *
 * @author 陈萌
 * @since 2021-11-30
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {

}
