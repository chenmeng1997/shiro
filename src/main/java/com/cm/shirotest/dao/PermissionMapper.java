package com.cm.shirotest.dao;

import com.cm.shirotest.entity.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 权限表 Mapper 接口
 * </p>
 *
 * @author 陈萌
 * @since 2021-11-30
 */
@Repository
public interface PermissionMapper extends BaseMapper<Permission> {

}
