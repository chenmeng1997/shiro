package com.cm.shirotest.api.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 权限角色关系表
 * </p>
 *
 * @author 陈萌
 * @since 2021-11-30
 */
@Data
@ApiModel(value = "UserRoleVo对象", description = "权限角色关系")
public class UserRoleVo implements Serializable {

    @ApiModelProperty(value = "用户Id")
    private Integer id;

    @ApiModelProperty(value = "机构ID")
    private Integer organId;

    @ApiModelProperty(value = "用户姓名")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "角色、权限")
    private List<RoleVo> roleVoList;

}
