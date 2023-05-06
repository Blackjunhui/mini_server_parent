package com.black.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.black.security.entity.SecurityUser;
import com.black.system.entity.SysUser;
import com.black.system.mapper.SysMenuMapper;
import com.black.system.mapper.SysUserMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author black
 * @date 2023/4/27 8:58
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private SysMenuMapper sysMenuMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        QueryWrapper<SysUser> wrapper = new QueryWrapper();
        wrapper.eq("username",username);
        SysUser sysUser = sysUserMapper.selectOne(wrapper);
        if(sysUser == null){
            throw new UsernameNotFoundException("用户不存在");
        }

        com.black.security.entity.SysUser curUser = new com.black.security.entity.SysUser();
        BeanUtils.copyProperties(sysUser,curUser);

        //获取菜单按钮权限
        List<String> menuPerms = sysMenuMapper.getMenuPermsByUserId(sysUser.getId());

        SecurityUser user = new SecurityUser();
        user.setCurrentUserInfo(curUser);
        user.setPermissionValueList(menuPerms);
        return user;
    }
}
