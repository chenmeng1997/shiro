package com.cm.shirotest;


import com.cm.shirotest.api.vo.PermissionRoleVo;
import com.cm.shirotest.service.IPermissionRoleService;
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
    
    @Test
    public void contextLoads() {
        PermissionRoleVo permissionRoleVo = permissionRoleService.getPermissionRoleVoByRoleId(1L);
    }

}
