package com.black.system.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author black
 * @date 2023/3/14 8:58
 */
@ApiModel("新增权限请求参数")
@Data
public class AddRoleReqVO {

    /**
     * 名称
     */
    @ApiModelProperty("名称")
    private String name;

    /**
     * 编码
     */
    @ApiModelProperty("编码")
    private String code;

    /**
     * 描述
     */
    @ApiModelProperty("描述")
    private String description;

}
