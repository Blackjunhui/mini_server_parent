package com.black.system.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author black
 * @date 2023/3/14 13:36
 */
@ApiModel("修改菜单请求参数")
@Data
public class UpdateMenuReqVO extends IdReqVO{

    /**
     * 父id
     */
    @ApiModelProperty("父id")
    private Integer parentId;

    /**
     * 名称
     */
    private String name;

    /**
     * 类型0目录|1菜单|2按钮
     */
    @ApiModelProperty("类型0目录|1菜单|2按钮")
    private Integer type;

    /**
     * 路由地址
     */
    @ApiModelProperty("路由地址")
    private String path;

    /**
     * 组件路径
     */
    @ApiModelProperty("组件路径")
    private String component;

    /**
     * 权限标识
     */
    @ApiModelProperty("权限标识")
    private String perms;

    /**
     * 图标
     */
    @ApiModelProperty("图标")
    private String icon;

    /**
     * 排序
     */
    @ApiModelProperty("排序")
    private Integer sortValue;

    /**
     * 状态0禁止|1正常
     */
    @ApiModelProperty("状态0禁止|1正常")
    private Integer status;

}
