package com.knowology.service;

import com.github.pagehelper.PageInfo;
import com.knowology.model.Scene;
import com.knowology.request.BaseQuery;
import com.knowology.request.SceneQuery;

import java.util.List;

public interface SceneService {
    /**
     * 场景列表
     * @param baseQuery
     * @return
     */
    PageInfo<Scene> list(BaseQuery baseQuery);

    void add(SceneQuery sceneQuery);

    Scene view(Integer id);

    void delete(Integer id);

    /**
     * 加载场景列表
     * @return
     */
    List<String> listScene();
}
