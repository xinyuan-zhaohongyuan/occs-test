package com.knowology.model;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "Z_ROLE_MENU")
public class RoleMenu {
    /**
     * 角色ID
     */
    @Column(name = "ROLE_ID")
    private Integer roleId;

    /**
     * 菜单/按钮ID
     */
    @Column(name = "MENU_ID")
    private Integer menuId;

    /**
     * 获取角色ID
     *
     * @return ROLE_ID - 角色ID
     */
    public Integer getRoleId() {
        return roleId;
    }

    /**
     * 设置角色ID
     *
     * @param roleId 角色ID
     */
    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    /**
     * 获取菜单/按钮ID
     *
     * @return MENU_ID - 菜单/按钮ID
     */
    public Integer getMenuId() {
        return menuId;
    }

    /**
     * 设置菜单/按钮ID
     *
     * @param menuId 菜单/按钮ID
     */
    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }
}