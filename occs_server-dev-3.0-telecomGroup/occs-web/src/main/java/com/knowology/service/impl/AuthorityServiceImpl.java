package com.knowology.service.impl;

import com.github.pagehelper.PageInfo;
import com.knowology.dao.MenuMapper;
import com.knowology.dao.RoleMapper;
import com.knowology.dao.RoleMenuMapper;
import com.knowology.model.Menu;
import com.knowology.model.Role;
import com.knowology.model.RoleMenu;
import com.knowology.request.AuthorityQuery;
import com.knowology.service.AuthorityService;
import com.knowology.vo.TreeMenu;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.*;

/**
 * <p></p>
 *
 * @author : Conan
 * @date : 2019-07-22 11:24
 * @see com.knowology.service.AuthorityService
 **/
@Transactional(rollbackFor = Exception.class)
@Service
public class AuthorityServiceImpl implements AuthorityService {
    //数据库中菜单type = 0
    private static final String MENU = "0";
    //数据库中按钮type = 1
    private static final String BUTTON = "1";

    @Resource
    private RoleMapper roleMapper;
    @Resource
    private RoleMenuMapper roleMenuMapper;
    @Resource
    private MenuMapper menuMapper;

    @Override
    public PageInfo<Role> list() {
        Example example = new Example(Role.class);
        example.selectProperties("roleId", "roleName");
        return new PageInfo<>(roleMapper.selectByExample(example));
    }

    @Override
    public List<TreeMenu> menuButtonTreeOfUser(String username) {
        List<TreeMenu> nodes = new ArrayList<>();
        Optional.ofNullable(selectUserMenus(username)).ifPresent(l -> l.forEach(m -> {
            nodes.add(new TreeMenu(m));
        }));
        return buildTree(nodes);
    }

    @Override
    public List<TreeMenu> menuButtonTreeOfRole(String roleName) {
        List<TreeMenu> nodes = new ArrayList<>();
        Optional.ofNullable(selectRoleMenus(roleName)).ifPresent(l -> l.forEach(m -> {
            nodes.add(new TreeMenu(m));
        }));
        return buildTree(nodes);
    }

    @Override
    public void add(AuthorityQuery authorityQuery) {
        Role role = new Role();
        role.setRoleName(authorityQuery.getRoleName());
        role.setCreateTime(new Date());
        roleMapper.insert(role);
        List<RoleMenu> list = new LinkedList<>();
        authorityQuery.getMenuIds().forEach(id -> {
            RoleMenu rm = new RoleMenu();
            rm.setRoleId(role.getRoleId());
            rm.setMenuId(id);
            list.add(rm);
        });
        roleMenuMapper.insertList(list);
    }

    @Override
    public void update(AuthorityQuery authorityQuery) {
        updateRole(authorityQuery);
        //删除并重新插入rolemenu映射表数据
        deleteRoleMenu(authorityQuery.getRoleId());
        insertListRoleMenu(authorityQuery);
    }

    @Override
    public void delete(Integer roleId) {
        Example roleExample = new Example(Role.class);
        roleExample.createCriteria().andEqualTo("roleId", roleId);
        roleMapper.deleteByExample(roleExample);
        deleteRoleMenu(roleId);
    }

    @Override
    public Integer selectCountByRoleName(String roleName) {
        Example example = new Example(Role.class);
        example.createCriteria().andEqualTo("roleName",roleName);
        return roleMapper.selectCountByExample(example);
    }

    /**
     * 批量插入rolemenu表数据
     *
     * @param authorityQuery
     */
    private void insertListRoleMenu(AuthorityQuery authorityQuery) {
        List<RoleMenu> list = new LinkedList<>();
        authorityQuery.getMenuIds().forEach(id -> {
            RoleMenu rm = new RoleMenu();
            rm.setMenuId(id);
            rm.setRoleId(authorityQuery.getRoleId());
            list.add(rm);
        });
        roleMenuMapper.insertList(list);
    }

    /**
     * 删除rolemenu映射表数据
     *
     * @param roleId
     */
    private void deleteRoleMenu(Integer roleId) {
        Example example = new Example(RoleMenu.class);
        example.createCriteria().andEqualTo("roleId", roleId);
        roleMenuMapper.deleteByExample(example);
    }

    /**
     * 更细role角色名
     *
     * @param authorityQuery
     */
    private void updateRole(AuthorityQuery authorityQuery) {
        Example roleExample = new Example(Role.class);
        roleExample.createCriteria().andEqualTo("roleId", authorityQuery.getRoleId());
        Role role = new Role();
        role.setRoleName(authorityQuery.getRoleName());
        role.setUpdateTime(new Date());
        roleMapper.updateByExampleSelective(role, roleExample);
    }

    /**
     * 查询username的所有菜单按钮
     *
     * @param username
     * @return
     */
    private List<Menu> selectUserMenus(String username) {
        return menuMapper.selectMenusByUsername(username);
    }

    /**
     * 查询roleName的所有菜单按钮
     *
     * @param roleName
     * @return
     */
    private List<Menu> selectRoleMenus(String roleName) {
        return menuMapper.selectMenusByRoleName(roleName);
    }

    private List<TreeMenu> buildTree(List<TreeMenu> nodes) {
        List<TreeMenu> result = new ArrayList<>();
        if (null == nodes) {
            return result;
        }
        nodes.forEach(treeMenu -> {
            Integer parentId = treeMenu.getParentId();
            if (0 == parentId) {
                result.add(treeMenu);
                return;
            }
            for (TreeMenu parent : nodes) {
                if (parentId.equals(parent.getId())) {
                    if (MENU.equals(treeMenu.getMenuType())) {
                        parent.getSubMenu().add(treeMenu);
                        return;
                    }
                    if (BUTTON.equals(treeMenu.getMenuType())) {
                        parent.getButtons().add(treeMenu);
                        return;
                    }
                }
            }
        });
        //排序 根据ORDER_NUM ASC
        result.sort(Comparator.comparing(TreeMenu::getOrderNum));
        result.forEach(
                treeMenu ->
                        Optional.ofNullable(treeMenu.getSubMenu())
                                .ifPresent(children ->
                                        children.sort(Comparator.comparingInt(TreeMenu::getOrderNum))));
        return result;
    }
}
