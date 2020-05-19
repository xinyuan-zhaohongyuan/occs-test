package com.knowology.vo;

import com.knowology.model.Dept;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * <p></p>
 *
 * @author : Conan
 * @date : 2019-07-25 14:43
 **/
@Data
public class TreeDept {
    /**
     * 本id
     */
    private Integer deptId;

    /**
     * 父Id
     */
    private Integer parentId;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 子部门集合
     */
    private List<TreeDept> children = new ArrayList<>();

    public TreeDept(Dept dept) {
        this.deptId = dept.getDeptId();
        this.parentId = dept.getParentId();
        this.deptName = dept.getDeptName();
    }
}
