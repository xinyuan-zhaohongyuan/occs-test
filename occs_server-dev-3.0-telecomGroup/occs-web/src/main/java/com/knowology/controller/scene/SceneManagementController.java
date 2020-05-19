package com.knowology.controller.scene;

import com.github.pagehelper.PageInfo;
import com.knowology.model.Scene;
import com.knowology.request.BaseQuery;
import com.knowology.request.SceneQuery;
import com.knowology.service.SceneService;
import com.knowology.util.ResponseUtil;
import com.knowology.valid.AddCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>场景管理</p>
 *
 * @author : Conan
 * @date : 2019-08-08 15:21
 **/
@RestController
@RequestMapping("scene")
public class SceneManagementController {
    @Autowired
    private SceneService sceneService;

    @PostMapping("list")
    public Object list(@Validated BaseQuery baseQuery) {
        PageInfo<Scene> list = sceneService.list(baseQuery);
        Map<String,Object> map = new HashMap<>(4);
        map.put("total", list.getTotal());
        map.put("items", list.getList());
        return ResponseUtil.ok(map);
    }

    @PreAuthorize("hasAuthority('sceneManagement_add')")
    @PostMapping("add")
    public Object add(@Validated(AddCheck.class) SceneQuery sceneQuery) {
        sceneService.add(sceneQuery);
        return ResponseUtil.ok();
    }

    @PostMapping("view")
    public Object view(Integer id) {
        if (id == null) {
            return ResponseUtil.fail("id不能为空");
        }
        Scene scene = sceneService.view(id);
        return ResponseUtil.ok(scene);
    }

    @PreAuthorize("hasAuthority('sceneManagement_delete')")
    @PostMapping("delete")
    public Object delete(Integer id) {
        if (id == null) {
            return ResponseUtil.fail("id不能为空");
        }
        sceneService.delete(id);
        return ResponseUtil.ok();
    }
}
