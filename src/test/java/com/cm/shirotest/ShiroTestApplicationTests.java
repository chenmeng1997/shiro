package com.cm.shirotest;


import com.cm.shirotest.api.vo.PermissionRoleVo;
import com.cm.shirotest.api.vo.UserRoleVo;
import com.cm.shirotest.entity.User;
import com.cm.shirotest.service.IPermissionRoleService;
import com.cm.shirotest.service.IUserRoleService;
import com.cm.shirotest.service.IUserService;
import com.cm.shirotest.utils.PWDUtil;
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
    @Autowired
    private IUserService userService;

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
        User user = new User();
        user.setId(2);
        String salt = PWDUtil.getSalt(6);
        user.setSalt(salt);
        String pwd = PWDUtil.getMD5Pwd("chen1997", salt, 2);
        user.setPassword(pwd);
        boolean update = userService.updateById(user);
    }
}
