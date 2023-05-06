package com.black.system.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author black
 * @date 2023/3/15 11:14
 */
@ApiModel("更改菜单状态请求参数")
@Data
public class UpdateMenuStateReqVO extends IdReqVO {

    @ApiModelProperty("状态")
    private Integer state;

}
