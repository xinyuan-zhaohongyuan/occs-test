package com.knowology.thread;

import com.knowology.dao.DataStorageMapper;
import com.knowology.dao.FilterRuleMapper;
import com.knowology.dao.PassiveNumDetailMapper;
import com.knowology.model.DataStorage;
import com.knowology.model.FilterRule;
import com.knowology.model.PassiveNumDetail;
import com.knowology.util.DBDataUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 异步任务,异步任务执行数据筛选及添加号码组
 *
 * @author xullent
 */
@Service
public class TaskDataFilterAsyncService {
    private static final Logger logger = LoggerFactory.getLogger(TaskDataFilterAsyncService.class);

    @Resource
    private PassiveNumDetailMapper passiveNumDetailMapper;
    @Resource
    private DataStorageMapper dataStorageMapper;
    @Resource
    private FilterRuleMapper filterRuleMapper;

    @Async("asyncPoolTaskExecutor")
    public void dataFilter(){
        logger.info("异步执行数据筛选模式");
        FilterRule filterRule = filterRuleMapper.selectRule();
        if("全部".equals(filterRule.getProvince())){
            filterRule.setProvince("");
        }
        if("全部".equals(filterRule.getBussinessType())){
            filterRule.setBussinessType("");
        }
        List<DataStorage> dataStorageList = dataStorageMapper.todayDataStorage(filterRule);
        if(dataStorageList == null || dataStorageList.size() <= 0){
            logger.info("没有数据");
            return;
        }
        packDataToPassNumDetail(DBDataUtil.dataStorageFilter(dataStorageList,filterRule.getFilterRatio()));
    }
    /**
     * 将数据装入相应的号码组中
     * @param dataStorages
     */
    private int packDataToPassNumDetail(List<DataStorage> dataStorages){
        List<PassiveNumDetail> passiveNumDetails = new ArrayList<>();
        if(dataStorages == null || dataStorages.size() <= 0){
            return 0;
        }
        for (DataStorage dataStorage : dataStorages){
            PassiveNumDetail passiveNumDetail = new PassiveNumDetail();
            passiveNumDetail.setBussinessType(dataStorage.getBussinessType());
            passiveNumDetail.setAge(dataStorage.getAge());
            passiveNumDetail.setBusinessHallName(dataStorage.getBusinessHallName());
            passiveNumDetail.setCity(dataStorage.getCity());
            passiveNumDetail.setClientName(dataStorage.getClientName());
            passiveNumDetail.setCreateTime(dataStorage.getCreateTime());
            passiveNumDetail.setGender(dataStorage.getGender());
            passiveNumDetail.setPhoneNum(dataStorage.getPhoneNum());
            passiveNumDetail.setProvince(dataStorage.getProvince());
            passiveNumDetail.setTelenumGroupName(filterRuleMapper.selectRule().getTelenumGroupName());
            //自动筛选
            passiveNumDetail.setOrigin(2);
            passiveNumDetails.add(passiveNumDetail);
        }
        return passiveNumDetailMapper.addPassivenumDetailByPassiveNumId(passiveNumDetails);
    }
}
