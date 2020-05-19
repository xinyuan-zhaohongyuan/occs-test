package com.knowology.dao;

import com.knowology.config.MyMapper;
import com.knowology.model.Menu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MenuMapper extends MyMapper<Menu> {
    /**
     * 根据用户名查询菜单/按钮
     * @param username
     * @return
     */
    List<Menu> selectMenusByUsername(@Param("username") String username);

    /**
     * 根据角色名查询菜单/按钮
     * @param roleName
     * @return
     */
    List<Menu> selectMenusByRoleName(@Param("roleName") String roleName);
}