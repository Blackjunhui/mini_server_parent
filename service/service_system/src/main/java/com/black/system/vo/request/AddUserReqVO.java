package com.black.system.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author black
 * @date 2023/3/14 13:22
 */
@ApiModel("新增用户请求参数")
@Data
public class AddUserReqVO {

    /**
     * 用户名称
     */
    @ApiModelProperty("用户名称")
    private String username;

    /**
     * 密码
     */
    @ApiModelProperty("密码")
    private String password;

}
