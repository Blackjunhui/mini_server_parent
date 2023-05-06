package com.black.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.black.security.security.DefaultPasswordEncoder;
import com.black.security.security.TokenManager;
import com.black.system.entity.SysMenu;
import com.black.system.entity.SysUser;
import com.black.system.mapper.SysMenuMapper;
import com.black.system.mapper.SysUserMapper;
import com.black.system.service.IndexService;
import com.black.system.utils.MenuHelper;
import com.black.system.utils.RouterHelper;
import com.black.system.vo.request.LoginReqVO;
import com.black.system.vo.response.RouterVO;
import com.black.utils.utils.MD5;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author black
 * @date 2023/4/27 10:08
 */
@Service
public class IndexServiceImpl implements IndexService {

    @Value("${admin.id}")
    private Integer adminId;

    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private SysMenuMapper sysMenuMapper;

    @Override
    public Map<String,Object> getUserInfo(HttpServletRequest request) {
        //获取请求头中的token
        String token = request.getHeader("token");

        //从token中获取用户id
        Integer userId = TokenManager.getUserId(token);

        //通过用户id获取用户信息
        SysUser user = sysUserMapper.selectById(userId);
        //通过用户id获取菜单权限
        List<SysMenu> menus = new ArrayList<>();
        if(!isAdmin(userId)){
            menus = sysMenuMapper.getMenusByUserId(userId);
        }else {
            menus = sysMenuMapper.selectList(null);
        }

        List<SysMenu> menuTree = MenuHelper.treeBuild(menus);
        List<RouterVO> routerVOS = RouterHelper.buildRouters(menuTree);
        //根据用户id获取按钮权限
        List<String> permsList = menus.stream().filter(menu -> menu.getType() == 2).map(SysMenu::getPerms).collect(Collectors.toList());

        Map<String,Object> map = new HashMap<>();
        map.put("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        map.put("name",user.getUsername());
        map.put("roles",new String[]{"admin"});
        //用户id
        map.put("userId",userId);
        //菜单权限数据
        map.put("routers",routerVOS);
        //按钮权限数据
        map.put("buttons",permsList);
        return map;
    }

    @Override
    public Map<String, String> login(LoginReqVO reqVO) {
        Map<String,String> map = new HashMap<>();
        //根据用户名查询数据
        QueryWrapper<SysUser> wrapper = new QueryWrapper();
        wrapper.eq("username",reqVO.getUsername());
        SysUser user = sysUserMapper.selectOne(wrapper);

        //判断是否为空
        if(user != null){
            //判断密码是否正确
            String encoderPassword = MD5.encrypt(reqVO.getPassword());
            if(encoderPassword.equals(user.getPassword())){
                //根据userId和userName生成token
                String token = TokenManager.createToken(user.getId(), user.getUsername());
                map.put("token",token);
            }
        }
        return map;
    }

    private boolean isAdmin(Integer userId){
        if(userId == adminId){
            return true;
        }
        return false;
    }
}
