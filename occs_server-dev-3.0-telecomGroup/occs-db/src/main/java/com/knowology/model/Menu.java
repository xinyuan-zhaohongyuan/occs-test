package com.knowology.model;

import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.*;
import java.util.Date;

@Table(name = "Z_MENU")
public class Menu {
    /**
     * 菜单/按钮ID
     */
    @Id
    @Column(name = "MENU_ID")
    @KeySql(useGeneratedKeys = true)
    private Integer menuId;

    /**
     * 上级菜单ID
     */
    @Column(name = "PARENT_ID")
    private Integer parentId;

    /**
     * 菜单/按钮名称
     */
    @Column(name = "MENU_NAME")
    private String menuName;

    /**
     * 菜单URL
     */
    @Column(name = "URL")
    private String url;

    /**
     * 操作功能
     */
    @Column(name = "OPERATION")
    private String operation;

    /**
     * 图标
     */
    @Column(name = "ICON")
    private String icon;

    /**
     * 类型 0菜单 1按钮
     */
    @Column(name = "TYPE")
    private String type;

    /**
     * 排序
     */
    @Column(name = "ORDER_NUM")
    private Integer orderNum;

    /**
     * 创建时间
     */
    @Column(name = "CREATE_TIME")
    private Date createTime;

    /**
     * 修改时间
     */
    @Column(name = "UPDATE_TIME")
    private Date updateTime;

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

    /**
     * 获取上级菜单ID
     *
     * @return PARENT_ID - 上级菜单ID
     */
    public Integer getParentId() {
        return parentId;
    }

    /**
     * 设置上级菜单ID
     *
     * @param parentId 上级菜单ID
     */
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    /**
     * 获取菜单/按钮名称
     *
     * @return MENU_NAME - 菜单/按钮名称
     */
    public String getMenuName() {
        return menuName;
    }

    /**
     * 设置菜单/按钮名称
     *
     * @param menuName 菜单/按钮名称
     */
    public void setMenuName(String menuName) {
        this.menuName = menuName == null ? null : menuName.trim();
    }

    /**
     * 获取菜单URL
     *
     * @return URL - 菜单URL
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置菜单URL
     *
     * @param url 菜单URL
     */
    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    /**
     * 获取操作功能
     *
     * @return OPERATION - 操作功能
     */
    public String getOperation() {
        return operation;
    }

    /**
     * 设置操作功能
     *
     * @param operation 操作功能
     */
    public void setOperation(String operation) {
        this.operation = operation == null ? null : operation.trim();
    }

    /**
     * 获取图标
     *
     * @return ICON - 图标
     */
    public String getIcon() {
        return icon;
    }

    /**
     * 设置图标
     *
     * @param icon 图标
     */
    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

    /**
     * 获取类型 0菜单 1按钮
     *
     * @return TYPE - 类型 0菜单 1按钮
     */
    public String getType() {
        return type;
    }

    /**
     * 设置类型 0菜单 1按钮
     *
     * @param type 类型 0菜单 1按钮
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * 获取排序
     *
     * @return ORDER_NUM - 排序
     */
    public Integer getOrderNum() {
        return orderNum;
    }

    /**
     * 设置排序
     *
     * @param orderNum 排序
     */
    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    /**
     * 获取创建时间
     *
     * @return CREATE_TIME - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取修改时间
     *
     * @return UPDATE_TIME - 修改时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置修改时间
     *
     * @param updateTime 修改时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}