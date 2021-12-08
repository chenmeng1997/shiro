package com.cm.shirotest.api.vo;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @author 陈萌
 * @since 2021-11-30
 */
@Data
@ApiModel(value = "RoleVo", description = "角色")
public class RoleVo implements Serializable {

    @ApiModelProperty(value = "角色id")
    private Integer id;

    @ApiModelProperty(value = "角色名称")
    private String rname;

    @ApiModelProperty(value = "角色简介")
    private String summary;

}
