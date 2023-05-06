package com.black.system.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@ApiModel("id主键请求参数")
@Data
public class IdReqVO {

    @NotNull
    @ApiModelProperty("主键id")
    private Integer id;

}
