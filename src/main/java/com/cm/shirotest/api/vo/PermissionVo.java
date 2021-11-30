package com.cm.shirotest.api.vo;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 权限表
 * </p>
 *
 * @author 陈萌
 * @since 2021-11-30
 */
@Data
@ApiModel(value = "PermissionVo对象", description = "权限表")
public class PermissionVo implements Serializable {

    @ApiModelProperty(value = "权限ID")
    private Long pid;

    @ApiModelProperty(value = "父节点id")
    private Integer parentId;

    @ApiModelProperty(value = "菜单名称")
    private String name;

    @ApiModelProperty(value = "菜单路径")
    private String url;

    @ApiModelProperty(value = "菜单类型 1：菜单 2：目录 3：按钮")
    private String type;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "授权 以’,’分隔")
    private String perms;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date modifyTime;

}
