package com.knowology.service;

import com.github.pagehelper.PageInfo;
import com.knowology.model.ExampleData;

/**
 * 场景测试
 * @author xullent
 */
public interface ExampleDataService {
	/**
	 * 场景测试列表数据
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	PageInfo<ExampleData> list(int pageNum, int pageSize);

	/**
	 * 查询范围内的场景cesium数据
	 * @param passiveNum
	 * @param startTime
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	PageInfo<ExampleData> search(String passiveNum, String startTime, int pageNum, int pageSize);

	/**
	 * 删除数据
	 * @param id
	 */
	void delete(String id);

	/**
	 * 插入数据
	 * @param data
	 */
	void insertExampleData(ExampleData data);

	/**
	 * 更新数据
	 * @param data
	 */
	void update(ExampleData data);
}
