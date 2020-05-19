package com.knowology.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.knowology.dao.ExampleDataMapper;
import com.knowology.model.ExampleData;
import com.knowology.service.ExampleDataService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description:
 * @author: Conan
 * @create: 2019-03-07 15:11
 **/
@Transactional(rollbackFor = Exception.class)
@Service
public class ExampleDataServiceImpl implements ExampleDataService {

	@Resource
    ExampleDataMapper mapper;

	@Override
	public PageInfo<ExampleData> list(int pageNum, int pageSize) {
		Example example = new Example(ExampleData.class);
		PageHelper.startPage(pageNum,pageSize);
		example.setOrderByClause("START_TIME DESC");
		List<ExampleData> data = mapper.selectByExample(example);
		PageInfo<ExampleData> pageInfo = new PageInfo<>(data);
		return pageInfo;
	}

	@Override
	public PageInfo<ExampleData> search(String passiveNum, String startTime, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<ExampleData> list = mapper.search(passiveNum, startTime);
		PageInfo<ExampleData> pageInfo = new PageInfo<ExampleData>(list);
		return pageInfo;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void delete(String id) {
		ExampleData exampleData = new ExampleData();
		exampleData.setId(Integer.parseInt(id));
		mapper.deleteByPrimaryKey(exampleData);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void insertExampleData(ExampleData data) {
		mapper.insert(data);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void update(ExampleData data) {
		Example example = new Example(ExampleData.class);
		example.createCriteria().andEqualTo("id",data.getId()).andEqualTo("version","0");
		mapper.updateByExampleSelective(data,example);
	}
}
