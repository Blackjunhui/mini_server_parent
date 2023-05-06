package com.black.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.black.system.entity.SysMenu;
import com.black.system.entity.SysRoleMenu;
import com.black.system.mapper.SysMenuMapper;
import com.black.system.mapper.SysRoleMenuMapper;
import com.black.system.service.SysMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.black.system.utils.MenuHelper;
import com.black.system.vo.request.*;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author black
 * @since 2023-04-27
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService{

    @Resource
    private SysMenuMapper sysMenuMapper;

    @Resource
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Override
    public PageInfo<SysMenu> getPage(PageInfoReqVO reqVO) {
        PageInfo<SysMenu> menuPage = PageMethod.startPage(reqVO.getPageNum(), reqVO.getPageSize()).doSelectPageInfo(() -> {
            sysMenuMapper.selectList(null);
        });
        return menuPage;
    }

    @Override
    public List<SysMenu> menuTree() {
        //查询所有数据
        List<SysMenu> menus = sysMenuMapper.selectList(null);
        Collections.sort(menus, new Comparator<SysMenu>() {
            @Override
            public int compare(SysMenu o1, SysMenu o2) {

                return o1.getSortValue() - o2.getSortValue();
            }
        });

        List<SysMenu> menuTree = new ArrayList<>();
        if(menus !=null&&menus.size()>0) {
            //构建树形结构
            menuTree = MenuHelper.treeBuild(menus);
        }
        return menuTree;
    }

    @Transactional
    @Override
    public void addMenu(AddMenuReqVO reqVO) {
        SysMenu menu = new SysMenu();
        BeanUtils.copyProperties(reqVO,menu);
        sysMenuMapper.insert(menu);
    }

    @Transactional
    @Override
    public void updateMenu(UpdateMenuReqVO reqVO) {
        SysMenu menu = new SysMenu();
        BeanUtils.copyProperties(reqVO,menu);
        sysMenuMapper.updateById(menu);
    }

    @Override
    public void deleteById(Integer id) {
        //查询是否存在子菜单
        QueryWrapper<SysMenu> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id",id);
        Long count = sysMenuMapper.selectCount(wrapper);
        if(count==0){
            //删除
            sysMenuMapper.deleteById(id);
        }
    }

    @Transactional
    @Override
    public void updateMenuState(UpdateMenuStateReqVO reqVO) {
        SysMenu menu = sysMenuMapper.selectById(reqVO.getId());
        menu.setStatus(reqVO.getState());
        sysMenuMapper.updateById(menu);
    }

    @Override
    public List<SysMenu> getMenuByRoleId(Integer roleId) {
        //获取所有菜单
        QueryWrapper<SysMenu> wrapper = new QueryWrapper<>();
        wrapper.eq("status",1);
        List<SysMenu> menus = sysMenuMapper.selectList(wrapper);

        //根据角色id查询角色分配过的菜单
        QueryWrapper<SysRoleMenu> roleMenuWrapper = new QueryWrapper<>();
        roleMenuWrapper.eq("role_id",roleId);
        List<SysRoleMenu> roleMenus = sysRoleMenuMapper.selectList(roleMenuWrapper);
        //获取角色分配的菜单id
        List<Integer> menuIds = roleMenus.stream().map(SysRoleMenu::getMenuId).collect(Collectors.toList());

        //数据处理：如果选中isSelect=true,否则false
        for (SysMenu menu: menus) {
            if(menuIds.contains(menu.getId())){
                menu.setIsSelect(true);
            }else{
                menu.setIsSelect(false);
            }
        }


        //通过MenuHelper转换为树形结构
        List<SysMenu> menuTree = MenuHelper.treeBuild(menus);

        return menuTree;
    }

    @Transactional
    @Override
    public void doAssign(UpdateRoleMenuByRoleIdReqVO reqVO) {
//删除角色旧数据
        QueryWrapper<SysRoleMenu> wrapper = new QueryWrapper();
        wrapper.eq("role_id",reqVO.getRoleId());
        sysRoleMenuMapper.delete(wrapper);

        //添加角色菜单权限
        for (Integer menuId : reqVO.getMenuIds()) {
            SysRoleMenu roleMenu = new SysRoleMenu();
            roleMenu.setRoleId(reqVO.getRoleId());
            roleMenu.setMenuId(menuId);
            sysRoleMenuMapper.insert(roleMenu);
        }
    }
}
