package com.black.system.utils;

import com.black.system.entity.SysMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * @author black
 * @date 2023/3/15 8:33
 */
public class MenuHelper {
    public static List<SysMenu> treeBuild(List<SysMenu> menus) {

        List<SysMenu> menuTree = new ArrayList<>();
        //查询parentId等与0的对象,作为递归入口
        for (SysMenu menu : menus) {
            if(menu.getParentId()==0){

                menuTree.add(findChildren(menu,menus));
            }
        }

        return menuTree;
    }

    private static SysMenu findChildren(SysMenu menu, List<SysMenu> menus) {
        //数据初始化
        menu.setChildren(new ArrayList<>());

        //遍历递归查找
        for (SysMenu it : menus) {
            //获取当前菜单id
//            Integer id = menu.getId();
            //获取所有菜单parentId
//            Integer parentId = it.getParentId();
            //比对
            if(menu.getId()==it.getParentId()){
                if(menu == null){
                    menu.setChildren(new ArrayList<>());
                }
                menu.getChildren().add(findChildren(it,menus));
            }
        }
        return menu;
    }
}
