package com.cm.shirotest.controller;


import com.cm.shirotest.api.ShiroDemoApi;
import com.cm.shirotest.api.request.UserLoginRequest;
import com.cm.shirotest.api.vo.UserVo;
import com.cm.shirotest.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author 陈萌
 * @since 2021-11-30
 */
@RestController
public class UserController {

    @Autowired
    private IUserService userService;

    @PostMapping(value = ShiroDemoApi.USER_LOGIN)
    public String login(@RequestBody @Valid UserLoginRequest request) {
        return userService.userLogin(request);
    }

    @PostMapping(value = ShiroDemoApi.USER_LOGIN_OUT)
    public String loginOut() {
        return userService.userLoginOut();
    }

    @RequiresPermissions(value = {"user:info", "user:Info"}, logical = Logical.OR)
    @GetMapping(value = ShiroDemoApi.USER_INFO)
    public UserVo getUserInfo(@PathVariable("id") Integer id) {
        return userService.getUserInfoById(id);
    }

}

