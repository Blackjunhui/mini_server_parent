package com.black.system.service;

import com.black.system.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.black.system.vo.request.AddUserReqVO;
import com.black.system.vo.request.PageInfoReqVO;
import com.black.system.vo.request.UpdateUserReqVO;
import com.github.pagehelper.PageInfo;

/**
 * <p>
 * 用户信息表 服务类
 * </p>
 *
 * @author black
 * @since 2023-04-21
 */
public interface SysUserService extends IService<SysUser> {

    //分页查询用户列表
    PageInfo<SysUser> getPage(PageInfoReqVO reqVO);

    //新增用户
    Integer addUser(AddUserReqVO reqVO);

    //修改用户
    Integer updateUser(UpdateUserReqVO reqVO);

}
