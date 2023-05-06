package com.black.system.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("分页请求参数")
public class PageInfoReqVO {

    @ApiModelProperty("分页页码")
    private Integer pageNum;

    @ApiModelProperty("分页数量")
    private Integer pageSize;

}
