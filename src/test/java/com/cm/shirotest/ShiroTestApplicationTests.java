package com.cm.shirotest;


import com.cm.shirotest.api.vo.PermissionRoleVo;
import com.cm.shirotest.api.vo.UserRoleVo;
import com.cm.shirotest.service.IPermissionRoleService;
import com.cm.shirotest.service.ISimpleCacheService;
import com.cm.shirotest.service.IUserRoleService;
import com.cm.shirotest.service.IUserService;
import com.cm.shirotest.utils.RedissonSerializable;
import org.apache.shiro.session.Session;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class ShiroTestApplicationTests {

    @Autowired
    private IPermissionRoleService permissionRoleService;

    @Autowired
    private IUserRoleService userRoleService;
    @Autowired
    private IUserService userService;
    @Autowired
    private ISimpleCacheService cacheService;
    @Resource(name = "redissonClientForShiro")
    private RedissonClient redissonClient;
    /**
     * 权限
     */
    @Test
    public void getPermissionRoleVoByRoleId() {

        PermissionRoleVo permissionRoleVo = permissionRoleService.getPermissionRoleVoByRoleId(1L);
    }


    /**
     * 角色
     */

    /**
     * 用户
     */
    @Test
    public void getUserRoleByUserId() {
        UserRoleVo userRoleVo = userRoleService.getUserRoleByUserId(1);
    }

    @Test
    public void test() {
        RBucket<String> bucket = redissonClient.getBucket("group_shiro:global:sessionId:8f0d2613-7e4d-447f-ab18-33e2910d1f9a");
        String sessionStr = bucket.get();
        Session session = (Session) RedissonSerializable.deserialize(sessionStr);
    }
}
