package com.black.system.controller;

import com.black.system.entity.SysRole;
import com.black.system.service.SysRoleService;
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
import java.util.Map;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author black
 * @since 2023-04-27
 */
@RestController
@RequestMapping("/system/sysRole")
@Api(tags = "角色请求接口")
public class SysRoleController {

    @Resource
    private SysRoleService sysRoleService;

    @PreAuthorize("hasAnyAuthority('btn.role.list')")
    @PostMapping("getListPage")
    @ApiOperation("获取分页权限角色列表")
    public R<PageInfo<SysRole>> getListPage(@RequestBody PageInfoReqVO reqVO){
        PageInfo<SysRole> page = sysRoleService.getPage(reqVO);

        return R.ok(page);
    }

    //根据id查询
    @GetMapping("getRoleById/{id}")
    @ApiOperation("根据id查询")
    public R<SysRole> getRoleById(@PathVariable Integer id){
        SysRole role = sysRoleService.getById(id);
        return R.ok(role);
    }

    //修改权限
    @PreAuthorize("hasAnyAuthority('btn.role.update')")
    @PostMapping("updateRole")
    @ApiOperation("修改权限")
    public R updateRole(@Validated @RequestBody UpdateRoleReqVO reqVO){
        sysRoleService.updateRole(reqVO);
        return R.ok();
    }

    //新增权限
    @PreAuthorize("hasAnyAuthority('btn.role.add')")
    @PostMapping("addRole")
    @ApiOperation("新增权限")
    public R addRole(@RequestBody AddRoleReqVO reqVO){
        sysRoleService.addRole(reqVO);
        return R.ok();
    }

    //删除权限
    @PreAuthorize("hasAnyAuthority('btn.role.delete')")
    @PostMapping("deleteRoleById")
    @ApiOperation("删除权限")
    public R deleteRoleById(@Validated @RequestBody IdReqVO reqVO){
        sysRoleService.removeById(reqVO.getId());
        return R.ok();
    }

    //批量删除
    @PreAuthorize("hasAnyAuthority('btn.role.delete')")
    @PostMapping("batchDelete")
    @ApiOperation("批量删除")
    public R batchDelete(@RequestBody List<Integer> ids){
        sysRoleService.removeByIds(ids);
        return R.ok();
    }

    //根据用户id获取角色
    @GetMapping("getRolesByUserId/{userId}")
    @ApiOperation("根据用户id获取角色")
    public R<Map<String,Object>> getRolesByUserId(@PathVariable Integer userId){

        Map<String, Object> map = sysRoleService.getRolesByUserId(userId);
        return R.ok(map);
    }

    //用户添加角色
    @PreAuthorize("hasAnyAuthority('btn.user.doAccessRole')")
    @PostMapping("doAssign")
    @ApiOperation("用户添加角色")
    public R updateUserRoleByUserId(@Validated @RequestBody UpdateUserRoleByUserIdReqVO reqVO){

        sysRoleService.updateUserRoleByUserId(reqVO);
        return R.ok();
    }

}
