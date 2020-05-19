package com.knowology.service;

import com.github.pagehelper.PageInfo;
import com.knowology.model.Role;
import com.knowology.request.AuthorityQuery;
import com.knowology.vo.TreeMenu;

import java.util.List;

public interface AuthorityService {
    /**
     * 角色列表
     * @return List<Role>
     */
    PageInfo<Role> list();

    /**
     * 菜单按钮Tree, 新建角色页面使用
     * @param username
     * @return
     */
    List<TreeMenu> menuButtonTreeOfUser(String username);

    /**
     * 角色的菜单按钮，查看角色和编辑时使用
     * @param roleName
     * @return
     */
    List<TreeMenu> menuButtonTreeOfRole(String roleName);

    /**
     * 新增角色
     */
    void add(AuthorityQuery authorityQuery);

    void update(AuthorityQuery authorityQuery);

    void delete(Integer roleId);

    Integer selectCountByRoleName(String roleName);

}
