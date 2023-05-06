package com.black.system.vo.response;

import lombok.Data;

import java.util.List;

/**
 * @author black
 * @date 2023/3/15 17:18
 */
@Data
public class RouterVO {

    //路由名称
//    private String name;

    //路由地址
    private String path;

    //是否隐藏路由，当设置为true的时候该路由不会在侧边栏显示
    private Boolean hidden;

    //组件地址
    private String component;

    //当你一个路由下面的children声明的路由大于1个时，自动会变成嵌套的模式--如组件页面
    private Boolean alwaysShow;

    //其他元素
    private MetaVO meta;

    //子路由
    private List<RouterVO> children;
}
