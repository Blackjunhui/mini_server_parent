package com.black.system.mapper;

import com.black.system.entity.SysMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 菜单表 Mapper 接口
 * </p>
 *
 * @author black
 * @since 2023-04-27
 */
@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    public List<String> getMenuPermsByUserId(@Param("userId")Integer userId);

    public List<SysMenu> getMenusByUserId(@Param("userId")Integer userId);
}
