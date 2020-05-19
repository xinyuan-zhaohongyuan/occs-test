package com.knowology.controller.system;

import com.knowology.request.DeptQuery;
import com.knowology.service.DeptService;
import com.knowology.util.ResponseUtil;
import com.knowology.valid.AddCheck;
import com.knowology.valid.UpdateCheck;
import com.knowology.vo.TreeDept;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>组织管理</p>
 *
 * @author : Conan
 * @date : 2019-07-22 10:46
 **/
@RestController
@RequestMapping("dept")
public class    DeptController {
    private DeptService deptService;

    private DeptController(DeptService deptService) {
        this.deptService = deptService;
    }

    @PostMapping("deptTree")
    public Object deptTree() {
        List<TreeDept> treeDept = deptService.deptTree();
        return ResponseUtil.ok(treeDept);
    }

    /**
     * 添加同级节点
     * @param deptQuery
     * @return
     */
//    @PreAuthorize("hasAuthority('organizationalManagement_add')")
    @PostMapping("addBrotherNode")
    public Object addBrotherNode(@Validated(AddCheck.class) DeptQuery deptQuery) {
        final Integer num = deptService.countByDeptName(deptQuery);
        if (num > 0) {
            return ResponseUtil.fail("同级节点名称已存在");
        }
        deptService.addBrotherNode(deptQuery);
        return ResponseUtil.ok();
    }

    /**
     * 添加子节点
     * @param deptQuery
     * @return
     */
//    @PreAuthorize("hasAuthority('organizationalManagement_add')")
    @PostMapping("addChildNode")
    public Object addChildNode(@Validated(AddCheck.class) DeptQuery deptQuery) {
        final Integer num = deptService.countChildNodeByDeptName(deptQuery);
        if (num > 0) {
            return ResponseUtil.fail("子节点名称已存在");
        }
        deptService.addChildNode(deptQuery);
        return ResponseUtil.ok();
    }

//    @PreAuthorize("hasAuthority('organizationalManagement_update')")
    @PostMapping("update")
    public Object update(@Validated(UpdateCheck.class) DeptQuery deptQuery) {
        deptService.update(deptQuery);
        return ResponseUtil.ok();
    }

//    @PreAuthorize("hasAuthority('organizationalManagement_delete')")
    @PostMapping("delete")
    public Object delete(Integer deptId) {
        deptService.delete(deptId);
        return ResponseUtil.ok();
    }
}
