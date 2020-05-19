package com.knowology.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.knowology.dao.SceneMapper;
import com.knowology.model.Scene;
import com.knowology.request.BaseQuery;
import com.knowology.request.SceneQuery;
import com.knowology.service.SceneService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p></p>
 *
 * @author : Conan
 * @date : 2019-08-08 15:54
 **/
@Service
public class SceneServiceImpl implements SceneService {
    @Resource
    private SceneMapper sceneMapper;

    @Override
    public PageInfo<Scene> list(BaseQuery baseQuery) {
        PageHelper.startPage(baseQuery.getPageNum(),baseQuery.getPageSize());
        Example example = new Example(Scene.class);
        example.selectProperties("id","scene");
        return new PageInfo<>(sceneMapper.selectByExample(example));
    }

    @Override
    public void add(SceneQuery sceneQuery) {
        Scene scene = new Scene();
        scene.setScene(sceneQuery.getScene());
        scene.setShop(sceneQuery.getShop());
        scene.setChannel(sceneQuery.getChannel());
        scene.setProvince(sceneQuery.getProvince());
        scene.setProvinceCode(sceneQuery.getProvinceCode());
        scene.setCity(sceneQuery.getCity());
        scene.setCityCode(sceneQuery.getCityCode());
        sceneMapper.insert(scene);
        // TODO: 2019/8/8 调接口传递数据
    }

    @Override
    public Scene view(Integer id) {
        Example example = new Example(Scene.class);
        example.createCriteria().andEqualTo("id",id);
        return sceneMapper.selectOneByExample(example);
    }

    @Override
    public void delete(Integer id) {
        Example example = new Example(Scene.class);
        example.createCriteria().andEqualTo("id",id);
        sceneMapper.deleteByExample(example);
    }

    @Override
    public List<String> listScene() {
        Example example = new Example(Scene.class);
        example.selectProperties("scene");
        return sceneMapper.selectByExample(example).stream().map(Scene::getScene).collect(Collectors.toList());
    }
}
