package com.black.system.service;

import com.black.system.entity.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;
import com.black.system.vo.request.AddRoleReqVO;
import com.black.system.vo.request.PageInfoReqVO;
import com.black.system.vo.request.UpdateRoleReqVO;
import com.black.system.vo.request.UpdateUserRoleByUserIdReqVO;
import com.github.pagehelper.PageInfo;

import java.util.Map;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author black
 * @since 2023-04-27
 */
public interface SysRoleService extends IService<SysRole> {

    PageInfo<SysRole> getPage(PageInfoReqVO reqVO);

    void updateRole(UpdateRoleReqVO reqVO);

    void addRole(AddRoleReqVO reqVO);


    Map<String, Object> getRolesByUserId(Integer userId);

    void updateUserRoleByUserId(UpdateUserRoleByUserIdReqVO reqVO);
}
