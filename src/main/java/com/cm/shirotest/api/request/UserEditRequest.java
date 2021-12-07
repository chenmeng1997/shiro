package com.cm.shirotest.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author 陈萌
 * @Date 2021/12/7 0007 21:11
 * @ProjectName shiro-test
 */
@Data
@ApiModel(value = "用户编辑息 对象", description = "用户编辑息")
public class UserEditRequest {

    @NotNull(message = "用户id,非空")
    @ApiModelProperty(value = "用户id")
    private Integer id;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "0.停用 1.启用")
    private Integer status;

    @ApiModelProperty(value = "0.未删除 1.删除")
    private Integer deleteState;

}
