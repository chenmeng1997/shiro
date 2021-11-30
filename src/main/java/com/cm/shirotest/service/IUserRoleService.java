package com.cm.shirotest.service;

import com.cm.shirotest.api.vo.UserRoleVo;
import com.cm.shirotest.entity.UserRole;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 权限角色关系表 服务类
 * </p>
 *
 * @author 陈萌
 * @since 2021-11-30
 */
public interface IUserRoleService extends IService<UserRole> {

    /**
     * 根据用户ID获取角色、权限
     * @param userId 用户ID
     * @return 用户、角色、权限
     */
    UserRoleVo getUserRoleByUserId(Integer userId);

}
