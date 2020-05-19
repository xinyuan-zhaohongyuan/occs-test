package com.knowology.dao;

import com.knowology.config.MyMapper;
import com.knowology.dto.SpecialFilterCondition;
import com.knowology.model.DataStorage;
import com.knowology.model.FilterRule;

import java.util.List;

public interface DataStorageMapper extends MyMapper<DataStorage> {

    /**
     * 今日从第三方接口接收的数据
     * @return
     */
    List<DataStorage> todayDataStorage(FilterRule filterRule);

    /**
     * 加载省份
     * @param
     * @return
     */
    List<String> listProvinces();

    /**
     * 加载城市
     * @param
     * @return
     */
    List<String> listCitys();

    /**
     * 加载营业厅
     * @param
     * @return
     */
    List<String> listBusinessHallNames();

    /**
     * 加载业务类型
     * @param
     * @return
     */
    List<String> listBussinessTypes();

    /**
     * 按照条件筛选数据
     * @param specialFilterCondition
     * @return
     */
    List<DataStorage> loadDataFilter(SpecialFilterCondition specialFilterCondition);

    /**
     * 筛选大致数量
     * @param specialFilterCondition
     * @return
     */
    int countDataFilter(SpecialFilterCondition specialFilterCondition);
}