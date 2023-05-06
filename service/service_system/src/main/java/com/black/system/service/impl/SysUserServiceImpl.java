package com.black.system.service.impl;

import com.black.system.entity.SysUser;
import com.black.system.mapper.SysUserMapper;
import com.black.system.service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.black.system.vo.request.AddUserReqVO;
import com.black.system.vo.request.PageInfoReqVO;
import com.black.system.vo.request.UpdateUserReqVO;
import com.black.utils.utils.MD5;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author black
 * @since 2023-04-21
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService{

    @Resource
    private SysUserMapper sysUserMapper;

    @Override
    public PageInfo<SysUser> getPage(PageInfoReqVO reqVO) {
        PageInfo<SysUser> userPage = PageMethod.startPage(reqVO.getPageNum(), reqVO.getPageSize()).doSelectPageInfo(() -> {
            sysUserMapper.selectList(null);
        });
        List<SysUser> list = userPage.getList();
        userPage.setList(list);
        return userPage;
    }

    @Transactional
    @Override
    public Integer addUser(AddUserReqVO reqVO) {
        SysUser user = new SysUser();
        BeanUtils.copyProperties(reqVO,user);
        //将输入的密码进行加密
        String passwordCoder = MD5.encrypt(user.getPassword());
        user.setPassword(passwordCoder);
        int insert = sysUserMapper.insert(user);
        return insert;
    }

    @Transactional
    @Override
    public Integer updateUser(UpdateUserReqVO reqVO) {
        SysUser user = new SysUser();
        BeanUtils.copyProperties(reqVO,user);
        int update = sysUserMapper.updateById(user);
        return update;
    }
}
