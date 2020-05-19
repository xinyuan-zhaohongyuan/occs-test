package com.knowology.controller.system;

import com.github.pagehelper.PageInfo;
import com.knowology.config.annotation.LoginUser;
import com.knowology.model.Role;
import com.knowology.request.AuthorityQuery;
import com.knowology.service.AuthorityService;
import com.knowology.util.ResponseUtil;
import com.knowology.valid.AddCheck;
import com.knowology.valid.UpdateCheck;
import com.knowology.vo.TreeMenu;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>权限管理</p>
 *
 * @author : Conan
 * @date : 2019-07-22 10:45
 **/
@RestController
@RequestMapping("authority")
public class AuthorityController {

    private AuthorityService authorityService;

    private AuthorityController(AuthorityService authorityService) {
        this.authorityService = authorityService;
    }

    @PostMapping("list")
    public Object list() {
        PageInfo<Role> list = authorityService.list();
        Map<String, Object> map = new HashMap<>(4);
        map.put("total", list.getTotal());
        map.put("items", list.getList());
        return ResponseUtil.ok(map);
    }

    @ApiOperation(value = "加载菜单树接口")
    @PostMapping("menuButtonTree")
    // TODO: 2019/8/8 defaultValue去掉 
    public Object menuButtonTree(@LoginUser(defaultValue = "admin2") String username) {
        List<TreeMenu> treeMenus = authorityService.menuButtonTreeOfUser(username);
        return ResponseUtil.ok(treeMenus);
    }

//    @PreAuthorize("hasAuthority('authorityManagement_add')")
    @ApiOperation(value = "新建角色")
    @PostMapping("add")
    public Object add(@Validated(AddCheck.class) @RequestBody AuthorityQuery authorityQuery) {
        if (StringUtils.isBlank(authorityQuery.getRoleName())) {
            return ResponseUtil.fail("角色名不能为空");
        }
        final Integer integer = authorityService.selectCountByRoleName(authorityQuery.getRoleName());
        if (integer > 1) {
            return ResponseUtil.fail("角色名已存在");
        }
        authorityService.add(authorityQuery);
        return ResponseUtil.ok();
    }

//    @PreAuthorize("hasAuthority('authorityManagement_update')")
    @PostMapping("update")
    public Object update(@Validated(UpdateCheck.class) @RequestBody AuthorityQuery authorityQuery) {
        authorityService.update(authorityQuery);
        return ResponseUtil.ok();
    }

    @PostMapping("view")
    public Object view(String roleName) {
        List<TreeMenu> treeMenus = authorityService.menuButtonTreeOfRole(roleName);
        return ResponseUtil.ok(treeMenus);
    }

//    @PreAuthorize("hasAuthority('authorityManagement_delete')")
    @PostMapping("delete")
    public Object delete(Integer roleId) {
        if (roleId == null) {
            return ResponseUtil.fail("请选择想要删除的角色");
        }
        authorityService.delete(roleId);
        return ResponseUtil.ok();
    }

}
