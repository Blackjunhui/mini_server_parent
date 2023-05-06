package com.black.system.controller;

import com.black.system.entity.SysUser;
import com.black.system.service.SysUserService;
import com.black.system.vo.request.AddUserReqVO;
import com.black.system.vo.request.IdReqVO;
import com.black.system.vo.request.PageInfoReqVO;
import com.black.system.vo.request.UpdateUserReqVO;
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
 * 用户信息表 前端控制器
 * </p>
 *
 * @author black
 * @since 2023-04-21
 */
@RestController
@RequestMapping("/system/sysUser")
@Api(tags = "用户请求接口")
public class SysUserController {


    @Resource
    private SysUserService sysUserService;

    //分页查询列表
    @PreAuthorize("hasAnyAuthority('btn.user.list')")
    @PostMapping("getListPage")
    @ApiOperation("分页查询列表")
    public R<PageInfo<SysUser>> getListPage(@RequestBody PageInfoReqVO reqVO){
        PageInfo<SysUser> page = sysUserService.getPage(reqVO);

        return R.ok(page);
    }

    //根据id查询用户
    @GetMapping("getUserById/{id}")
    @ApiOperation("根据id查询")
    public R<SysUser> getUserById(@PathVariable Integer id){
        SysUser user = sysUserService.getById(id);
        return R.ok(user);
    }

    //新增用户
    @PreAuthorize("hasAnyAuthority('btn.user.add')")
    @PostMapping("addUser")
    @ApiOperation("新增用户")
    public R addUser(@RequestBody AddUserReqVO reqVO){

        sysUserService.addUser(reqVO);
        return R.ok();
    }

    //修改用户
    @PreAuthorize("hasAnyAuthority('btn.user.update')")
    @PostMapping("updateUser")
    @ApiOperation("修改用户")
    public R updateRole(@Validated @RequestBody UpdateUserReqVO reqVO){
        sysUserService.updateUser(reqVO);
        return R.ok();
    }

    //删除用户
    @PreAuthorize("hasAnyAuthority('btn.user.delete')")
    @PostMapping("deleteUserById")
    @ApiOperation("删除用户")
    public R deleteUserById(@Validated @RequestBody IdReqVO reqVO){
        sysUserService.removeById(reqVO.getId());
        return R.ok();
    }

    //批量删除用户
    @PreAuthorize("hasAnyAuthority('btn.user.delete')")
    @PostMapping("batchDelete")
    @ApiOperation("批量删除")
    public R batchDelete(@RequestBody List<Integer> ids){
        sysUserService.removeByIds(ids);
        return R.ok();
    }

}
