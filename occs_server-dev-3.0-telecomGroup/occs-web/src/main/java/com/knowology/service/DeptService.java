package com.knowology.service;

import com.knowology.request.DeptQuery;
import com.knowology.vo.TreeDept;

import java.util.List;

public interface DeptService {

    /**
     * 组织列表树形显示
     * @return
     */
    List<TreeDept> deptTree();

    /**
     * 添加同级节点
     * @param deptQuery
     */
    void addBrotherNode(DeptQuery deptQuery);

    /**
     * 添加子节点
     * @param deptQuery
     */
    void addChildNode(DeptQuery deptQuery);

    void update(DeptQuery deptQuery);

    void delete(Integer deptId);

    /**
     * 查询同一级节点是否存在
     * @param deptName
     * @return
     */
    Integer countByDeptName(DeptQuery deptQuery);

    /**
     * 查询子节点是否存在
     * @param deptQuery
     * @return
     */
    Integer countChildNodeByDeptName(DeptQuery deptQuery);
}
