package com.black.system.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author black
 * @date 2023/3/14 15:28
 */
@ApiModel("通过用户id修改用户角色请求参数")
@Data
public class UpdateUserRoleByUserIdReqVO {

    @NotNull
    @ApiModelProperty("用户id")
    private Integer userId;

    @ApiModelProperty("角色ids")
    private List<Integer> roleIds;

}
