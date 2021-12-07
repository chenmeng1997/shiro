package com.cm.shirotest.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 陈萌
 * @version 1.0
 * @date 2021/12/1 0001 23:29
 * @modelName shiro-test
 */
@Data
@ApiModel(value = "UserVo对象", description = "用户表")
public class UserVo implements Serializable {

    @ApiModelProperty(value = "权限id")
    private Integer id;

    @ApiModelProperty(value = "机构ID")
    private Integer organId;

    @ApiModelProperty(value = "用户姓名")
    private String username;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "1:启用 0：停用")
    private Integer status;

}