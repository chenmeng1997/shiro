package com.cm.shirotest.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author 陈萌
 * @Date 2021/12/7 0007 21:11
 * @ProjectName shiro-test
 */
@Data
@ApiModel(value = "用户登录信息 对象", description = "用户登录信息")
public class UserLoginRequest {

    @NotBlank(message = "用户名")
    @ApiModelProperty(value = "用户名")
    private String username;

    @NotBlank(message = "密码")
    @ApiModelProperty(value = "密码")
    private String password;

}
