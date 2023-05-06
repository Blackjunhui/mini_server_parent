package com.black.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.black.system.entity.SysRole;
import com.black.system.entity.SysUserRole;
import com.black.system.mapper.SysRoleMapper;
import com.black.system.mapper.SysUserRoleMapper;
import com.black.system.service.SysRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.black.system.vo.request.AddRoleReqVO;
import com.black.system.vo.request.PageInfoReqVO;
import com.black.system.vo.request.UpdateRoleReqVO;
import com.black.system.vo.request.UpdateUserRoleByUserIdReqVO;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author black
 * @since 2023-04-27
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService{

    @Resource
    private SysRoleMapper sysRoleMapper;

    @Resource
    private SysUserRoleMapper sysUserRoleMapper;

    @Override
    public PageInfo<SysRole> getPage(PageInfoReqVO reqVO) {
        PageInfo<SysRole> roleList = PageMethod.startPage(reqVO.getPageNum(), reqVO.getPageSize()).doSelectPageInfo(() ->
                sysRoleMapper.selectList(null)
        );
        return roleList;
    }

    @Transactional
    @Override
    public void updateRole(UpdateRoleReqVO reqVO) {
        SysRole role = new SysRole();
        BeanUtils.copyProperties(reqVO,role);
        sysRoleMapper.updateById(role);
    }

    @Transactional
    @Override
    public void addRole(AddRoleReqVO reqVO) {
        SysRole role = new SysRole();
        BeanUtils.copyProperties(reqVO,role);
        sysRoleMapper.insert(role);
    }

    @Override
    public Map<String, Object> getRolesByUserId(Integer userId) {
        //查询所有角色
        List<SysRole> roles = sysRoleMapper.selectList(null);

        //根据用户id查询角色
        QueryWrapper<SysUserRole> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        List<SysUserRole> userRoles = sysUserRoleMapper.selectList(wrapper);
        List<Integer> userRoleIds = userRoles.stream().map(userRole -> userRole.getRoleId()).collect(Collectors.toList());

        Map<String,Object> map = new HashMap<>();
        map.put("roles",roles);
        map.put("userRoleIds",userRoleIds);

        return map;
    }

    @Transactional
    @Override
    public void updateUserRoleByUserId(UpdateUserRoleByUserIdReqVO reqVO) {
        //删除用户的旧角色
        QueryWrapper<SysUserRole> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",reqVO.getUserId());
        sysUserRoleMapper.delete(wrapper);

        //添加用户新的角色
        List<Integer> roleIds = reqVO.getRoleIds();
        if(roleIds!=null&&roleIds.size()>0){
            for (Integer roleId : roleIds) {
                SysUserRole userRole = new SysUserRole();
                userRole.setUserId(reqVO.getUserId());
                userRole.setRoleId(roleId);
                sysUserRoleMapper.insert(userRole);
            }
        }
    }
}
