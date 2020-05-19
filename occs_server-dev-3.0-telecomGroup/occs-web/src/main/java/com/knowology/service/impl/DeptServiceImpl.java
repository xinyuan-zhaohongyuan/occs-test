package com.knowology.service.impl;

import com.knowology.dao.DeptMapper;
import com.knowology.model.Dept;
import com.knowology.request.DeptQuery;
import com.knowology.service.DeptService;
import com.knowology.vo.TreeDept;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.*;

/**
 * <p></p>
 *
 * @author : Conan
 * @date : 2019-07-23 18:10
 **/
@Transactional(rollbackFor = Exception.class)
@Service
public class DeptServiceImpl implements DeptService {
    @Resource
    private DeptMapper deptMapper;

    @Override
    public List<TreeDept> deptTree() {
        List<TreeDept> nodes = new ArrayList<>();
        Optional.ofNullable(deptMapper.selectAll()).ifPresent(l -> l.forEach(dept -> {
            nodes.add(new TreeDept(dept));
        }));
        return buildTree(nodes);
    }

    @Override
    public void addBrotherNode(DeptQuery deptQuery) {
        Dept dept = new Dept();
        //添加同级节点，传的是parent的id
        dept.setParentId(deptQuery.getParentId());
        dept.setDeptName(deptQuery.getDeptName());
        dept.setCreateTime(new Date());
        deptMapper.insert(dept);
    }

    @Override
    public void addChildNode(DeptQuery deptQuery) {
        Dept dept = new Dept();
        //添加子节点，传的是本节点的id
        dept.setParentId(deptQuery.getDeptId());
        dept.setDeptName(deptQuery.getDeptName());
        dept.setCreateTime(new Date());
        deptMapper.insert(dept);
    }

    @Override
    public void update(DeptQuery deptQuery) {
        Example example = new Example(Dept.class);
        example.createCriteria().andEqualTo("deptId",deptQuery.getDeptId());
        Dept dept = new Dept();
        dept.setDeptName(deptQuery.getDeptName());
        deptMapper.updateByExampleSelective(dept,example);
    }

    @Override
    public void delete(Integer deptId) {
        List<Dept> depts = deptMapper.selectAll();
        List<Integer> ids = new LinkedList<>();
        ids.add(deptId);
        collectTreeDeptIds(depts,deptId,ids);
        Example example = new Example(Dept.class);
        example.createCriteria().andIn("deptId",ids);
        deptMapper.deleteByExample(example);
    }

    @Override
    public Integer countByDeptName(DeptQuery deptQuery) {
        Example example1 = new Example(Dept.class);
        example1.createCriteria().andEqualTo("deptId",deptQuery.getDeptId());
        Dept dept = deptMapper.selectOneByExample(example1);

        Example example = new Example(Dept.class);
        example.createCriteria().andEqualTo("deptName",deptQuery.getDeptName());
        example.createCriteria().andEqualTo("parentId",dept.getParentId());
        return deptMapper.selectCountByExample(example);
    }

    @Override
    public Integer countChildNodeByDeptName(DeptQuery deptQuery) {
        Example example = new Example(Dept.class);
        example.createCriteria().andEqualTo("deptName",deptQuery.getDeptName());
        //本节点ID作为父节点ID去查询
        example.createCriteria().andEqualTo("parentId",deptQuery.getDeptId());
        return deptMapper.selectCountByExample(example);
    }

    /**
     * 递归查找部门及其下的所有递归子部门的id
     *
     * @param allDept   所有dept
     * @param deptId 待递归部门id
     * @return
     */
    private void collectTreeDeptIds(List<Dept> allDept, Integer deptId, List<Integer> result) {
        for (Dept dept : allDept) {
            if (deptId.equals(dept.getParentId())) {
                result.add(dept.getDeptId());
                collectTreeDeptIds(allDept,dept.getDeptId(),result);
            }
        }
    }

    private List<TreeDept> buildTree(List<TreeDept> nodes) {
        List<TreeDept> result = new ArrayList<>();
        nodes.forEach(treeDept -> {
            Integer parentId = treeDept.getParentId();
            if (null == parentId) {
                result.add(treeDept);
                return;
            }
            for (TreeDept parent : nodes) {
                if (parentId.equals(parent.getDeptId())) {
                    parent.getChildren().add(treeDept);
                    return;
                }
            }
        });
        //排序 根据DEPT_ID ASC
        result.sort(Comparator.comparing(TreeDept::getDeptId));
        result.forEach(
                treeDept ->
                        Optional.ofNullable(treeDept.getChildren())
                                .ifPresent(children ->
                                        children.sort(Comparator.comparingInt(TreeDept::getDeptId))));
        return result;
    }
}
