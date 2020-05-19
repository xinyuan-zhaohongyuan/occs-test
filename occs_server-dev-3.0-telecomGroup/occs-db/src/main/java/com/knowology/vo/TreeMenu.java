package com.knowology.vo;

import com.knowology.model.Menu;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * <p></p>
 *
 * @author : Conan
 * @date : 2019-07-24 14:57
 **/
@Data
public class TreeMenu {

    private Integer id;

    private Integer parentId;

    private Integer orderNum;

    private String icon;

    /**
     * menuName
     */
    private String name;

    /**
     * 父菜单没用,子菜单同menuName
     */
    private String title;

    /**
     * url
     */
    private String key;

    /**
     * 父菜单没用,子菜单同key
     */
    private String route;

    /**
     * 目前是前端需要的一个固定值
     */
    private String type = "mail";

    /**
     * 菜单0按钮1
     */
    private String menuType;

    /**
     * 子菜单
     */
    private List<TreeMenu> subMenu = new ArrayList<>();

    /**
     * 按钮
     */
    private List<TreeMenu> buttons = new ArrayList<>();

    public TreeMenu(Menu menu) {
        this.id = menu.getMenuId();
        this.parentId = menu.getParentId();
        this.orderNum = menu.getOrderNum();
        this.icon = menu.getIcon();
        this.name = menu.getMenuName();
        this.title = menu.getMenuName();
        this.key = menu.getUrl();
        this.route = menu.getUrl();
        this.menuType = menu.getType();
    }
}
