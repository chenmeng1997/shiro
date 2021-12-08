package com.cm.shirotest.service;

import com.cm.shirotest.api.request.UserLoginRequest;
import com.cm.shirotest.api.vo.UserVo;
import com.cm.shirotest.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author 陈萌
 * @since 2021-11-30
 */
public interface IUserService extends IService<User> {

    /**
     * 用户登录
     *
     * @param request 用户账号、密码
     * @return 登录结果
     */
    String userLogin(UserLoginRequest request);

    /**
     * 用户登出
     *
     * @return 登录结果
     */
    String userLoginOut();

    /**
     * 用户信息据ID查
     *@param id 用户ID
     * @return 用户信息
     */
    UserVo getUserInfoById(Integer id);

}
