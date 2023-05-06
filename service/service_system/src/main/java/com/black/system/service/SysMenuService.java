package com.black.system.service;

import com.black.system.entity.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.black.system.vo.request.*;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * <p>
 * 菜单表 服务类
 * </p>
 *
 * @author black
 * @since 2023-04-27
 */
public interface SysMenuService extends IService<SysMenu> {

    PageInfo<SysMenu> getPage(PageInfoReqVO reqVO);

    List<SysMenu> menuTree();

    void addMenu(AddMenuReqVO reqVO);

    void updateMenu(UpdateMenuReqVO reqVO);

    void deleteById(Integer id);

    void updateMenuState(UpdateMenuStateReqVO reqVO);

    List<SysMenu> getMenuByRoleId(Integer roleId);

    void doAssign(UpdateRoleMenuByRoleIdReqVO reqVO);
}
