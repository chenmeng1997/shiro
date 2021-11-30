package com.cm.shirotest.service;

import com.cm.shirotest.api.vo.PermissionRoleVo;
import com.cm.shirotest.entity.PermissionRole;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 权限角色关系表 服务类
 * </p>
 *
 * @author 陈萌
 * @since 2021-11-30
 */
public interface IPermissionRoleService extends IService<PermissionRole> {

    /**
     * 根据角色ID获取权限
     *
     * @param roleId 角色ID
     * @return 权限
     */
    PermissionRoleVo getPermissionRoleVoByRoleId(Long roleId);

}
