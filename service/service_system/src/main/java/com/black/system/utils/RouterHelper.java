package com.black.system.utils;


import com.black.system.entity.SysMenu;
import com.black.system.vo.response.MetaVO;
import com.black.system.vo.response.RouterVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author black
 * @date 2023/3/15 17:45
 */
public class RouterHelper {

    /**
     *
     * @param menus
     * @return
     */
    public static List<RouterVO> buildRouters(List<SysMenu> menus){
        List<RouterVO> routers = new LinkedList<RouterVO>();
        for (SysMenu menu : menus) {
            RouterVO router = new RouterVO();
            router.setHidden(false);
            router.setAlwaysShow(false);
            router.setPath(getRouterPath(menu));
            router.setComponent(menu.getComponent());
            router.setMeta(new MetaVO(menu.getName(),menu.getIcon()));
            List<SysMenu> children = menu.getChildren();
            //如果当前是菜单，需将按钮对应的路由加载出来，如：“角色授权”按钮对应的路由在“系统管理”下面
            if(menu.getType() == 1){
                List<SysMenu> hiddenMenuList = children.stream().filter(item ->
                        !StringUtils.isEmpty(item.getComponent())).collect(Collectors.toList());
                for (SysMenu hiddenMenu : hiddenMenuList) {
                    RouterVO hiddenRouter = new RouterVO();
                    hiddenRouter.setHidden(true);
                    hiddenRouter.setAlwaysShow(false);
                    hiddenRouter.setPath(getRouterPath(hiddenMenu));
                    hiddenRouter.setComponent(hiddenMenu.getComponent());
                    hiddenRouter.setMeta(new MetaVO(hiddenMenu.getName(),hiddenMenu.getIcon()));
                    routers.add(hiddenRouter);
                }
            }else {
                if(!CollectionUtils.isEmpty(children)){
                    if(children.size()>0){
                        router.setAlwaysShow(true);
                    }
                    router.setChildren(buildRouters(children));
                }
            }
            routers.add(router);
        }
        return routers;
    }

    /**
     * 获取路由地址
     * @param menu
     * @return
     */
    private static String getRouterPath(SysMenu menu) {
        String routerPath = "/"+menu.getPath();
        if(menu.getParentId() != 0){
            routerPath = menu.getPath();
        }
        return routerPath;
    }

}
