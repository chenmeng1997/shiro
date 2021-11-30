package com.cm.shirotest.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 权限角色关系表
 * </p>
 *
 * @author 陈萌
 * @since 2021-11-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_permission_role")
@ApiModel(value="PermissionRole对象", description="权限角色关系表")
public class PermissionRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "权限角色关系id")
      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Long rid;

    private Long pid;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
      @TableField(fill = FieldFill.UPDATE)
    private Date modifyTime;

    @ApiModelProperty(value = "删除标志：0未删除，1已删除")
    @TableLogic
    private Boolean deleteState;


}
