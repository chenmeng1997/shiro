package com.cm.shirotest.controller;


import com.cm.shirotest.api.ShiroDemoApi;
import com.cm.shirotest.api.request.UserLoginRequest;
import com.cm.shirotest.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;

    @PostMapping(value = ShiroDemoApi.USER_LOGIN)
    public String login(@RequestBody @Valid UserLoginRequest request) {
        return userService.userLogin(request);
    }


}

