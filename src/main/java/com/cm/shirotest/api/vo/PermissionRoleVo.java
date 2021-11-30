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
@ApiModel(value = "PermissionRole对象", description = "权限角色关系表")
public class PermissionRoleVo implements Serializable {

    @ApiModelProperty(value = "角色ID")
    private Integer rid;

    @ApiModelProperty(value = "角色名称")
    private String rname;

    @ApiModelProperty(value = "角色简介")
    private String summary;

    @ApiModelProperty(value = "权限列表")
    private List<PermissionVo> permissionVoList;

}
