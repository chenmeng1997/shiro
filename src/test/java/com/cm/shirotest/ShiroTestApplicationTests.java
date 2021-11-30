package com.cm.shirotest;


import com.cm.shirotest.api.vo.PermissionRoleVo;
import com.cm.shirotest.api.vo.UserRoleVo;
import com.cm.shirotest.service.IPermissionRoleService;
import com.cm.shirotest.service.IUserRoleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class ShiroTestApplicationTests {

    @Autowired
    private IPermissionRoleService permissionRoleService;

    @Autowired
    private IUserRoleService userRoleService;

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
}
