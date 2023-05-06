package com.black.system.controller;

import com.black.system.entity.SysMenu;
import com.black.system.service.SysMenuService;
import com.black.system.vo.request.*;
import com.black.utils.utils.R;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 菜单表 前端控制器
 * </p>
 *
 * @author black
 * @since 2023-04-27
 */
@RestController
@RequestMapping("/system/sysMenu")
@Api(tags = "菜单请求接口")
public class SysMenuController {

    @Resource
    private SysMenuService sysMenuService;

    //分页查询列表
    @PostMapping("getListPage")
    @ApiOperation("分页查询列表")
    public R<PageInfo<SysMenu>> getListPage(@RequestBody PageInfoReqVO reqVO){
        PageInfo<SysMenu> page = sysMenuService.getPage(reqVO);

        return R.ok(page);
    }

    //菜单列表(树形)
    @PreAuthorize("hasAnyAuthority('btn.menu.list')")
    @GetMapping("menuTree")
    @ApiOperation("菜单列表(树形)")
    public R<List<SysMenu>> menuTree(){
        List<SysMenu> menus = sysMenuService.menuTree();
        return R.ok(menus);
    }

    //根据id查询用户
    @GetMapping("getMenuById/{id}")
    @ApiOperation("根据id查询")
    public R<SysMenu> getUserById(@PathVariable Integer id){
        SysMenu menu = sysMenuService.getById(id);
        return R.ok(menu);
    }

    //新增用户
    @PreAuthorize("hasAnyAuthority('btn.menu.add')")
    @PostMapping("addMenu")
    @ApiOperation("新增菜单")
    public R addUser(@RequestBody AddMenuReqVO reqVO){
        sysMenuService.addMenu(reqVO);
        return R.ok();
    }

    //修改用户
    @PreAuthorize("hasAnyAuthority('btn.menu.update')")
    @PostMapping("updateMenu")
    @ApiOperation("修改菜单")
    public R updateRole(@Validated @RequestBody UpdateMenuReqVO reqVO){
        sysMenuService.updateMenu(reqVO);
        return R.ok();
    }

    //删除用户
    @PreAuthorize("hasAnyAuthority('btn.menu.delete')")
    @PostMapping("deleteMenuById")
    @ApiOperation("删除菜单")
    public R deleteUserById(@Validated @RequestBody IdReqVO reqVO){
        sysMenuService.deleteById(reqVO.getId());
        return R.ok();
    }

    //更改菜单状态
    @PostMapping("updateMenuState")
    @ApiOperation("更改菜单状态")
    public R updateMenuState(@Validated @RequestBody UpdateMenuStateReqVO reqVO){
        sysMenuService.updateMenuState(reqVO);
        return R.ok();
    }

    //根绝角色id获取菜单
    @GetMapping("getMenuByRoleId/{roleId}")
    @ApiOperation("根绝角色id获取菜单")
    public R<List<SysMenu>> getMenuByRoleId(@PathVariable Integer roleId){
        List<SysMenu> menus = sysMenuService.getMenuByRoleId(roleId);
        return R.ok(menus);
    }

    //给角色分配权限
    @PreAuthorize("hasAnyAuthority('btn.role.assignAuth')")
    @PostMapping("doAssign")
    @ApiOperation("给角色分配权限")
    public R doAssign(@Validated @RequestBody UpdateRoleMenuByRoleIdReqVO reqVO){
        sysMenuService.doAssign(reqVO);
        return R.ok();
    }

}
