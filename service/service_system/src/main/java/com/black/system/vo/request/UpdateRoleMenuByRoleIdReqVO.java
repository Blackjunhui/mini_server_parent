package com.black.system.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author black
 * @date 2023/3/15 14:11
 */
@ApiModel("修改角色菜单权限请求参数")
@Data
public class UpdateRoleMenuByRoleIdReqVO {

    @NotNull
    @ApiModelProperty("角色id")
    private Integer roleId;

    @ApiModelProperty("菜单ids")
    private List<Integer> menuIds;

}
