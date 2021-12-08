package com.cm.shirotest.api;

/**
 * @author 陈萌
 * @date 2021/11/28 21:44
 */
public interface ShiroDemoApi {

    String USER_INFO = "/user/byId/{id}";

    /**
     * 用户登录
     */
    String USER_LOGIN = "/user/login";
    /**
     * 用户登出
     */
    String USER_LOGIN_OUT = "/user/loginOut";

}
