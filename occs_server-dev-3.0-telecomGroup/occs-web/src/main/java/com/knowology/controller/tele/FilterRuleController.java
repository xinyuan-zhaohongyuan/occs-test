package com.knowology.controller.tele;

import com.knowology.dto.SpecialFilterCondition;
import com.knowology.model.DataStorage;
import com.knowology.model.FilterRule;
import com.knowology.model.PassiveNumDetail;
import com.knowology.service.FilterRuleService;
import com.knowology.service.PassiveNumService;
import com.knowology.util.DBDataUtil;
import com.knowology.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据筛选
 *
 * @author xullent
 */
@RestController
@RequestMapping("dataFilter")
public class FilterRuleController {

    @Autowired
    private FilterRuleService filterRuleService;
    @Autowired
    private PassiveNumService passiveNumService;

    /**
     * 获取规则
     * @return
     */
    @PostMapping("rule")
    public Object getFilterRule(){
        FilterRule filterRule = filterRuleService.selectFilterRule();

        return ResponseUtil.ok(filterRule);
    }

    /**
     * 更新规则
     * @param filterRule
     * @return
     */
    @PostMapping("updateRule")
    public Object updateRule(FilterRule filterRule){
        if(filterRule == null || filterRule.getId() == null){
            return ResponseUtil.fail("必要参数不能为空");
        }
        int filter = filterRuleService.updateFilterRule(filterRule);
        if(filter <= 0){
            return ResponseUtil.fail("更新失败");
        }
        return ResponseUtil.ok();
    }

    /**
     * 号码组名称列表
     * @return
     */
    @PostMapping("teleNumGroupName")
    public Object getTeleNumGroupNameList(){
        List<String> teleNumGroupNameList = filterRuleService.listTeleNumGroupName();
        return ResponseUtil.ok(teleNumGroupNameList);
    }

    /**
     * 省份列表
     * @return
     */
    @PostMapping("listProvinces")
    public Object listProvinces(){
        List<String> teleNumGroupNameList = filterRuleService.listProvinces();
        return ResponseUtil.ok(teleNumGroupNameList);
    }

    /**
     * 地区列表
     * @return
     */
    @PostMapping("listCitys")
    public Object listCitys(){
        List<String> teleNumGroupNameList = filterRuleService.listCitys();
        return ResponseUtil.ok(teleNumGroupNameList);
    }

    /**
     * 营业厅列表
     * @return
     */
    @PostMapping("listBusinessHallNames")
    public Object listBusinessHallNames(){
        List<String> teleNumGroupNameList = filterRuleService.listBusinessHallNames();
        return ResponseUtil.ok(teleNumGroupNameList);
    }

    /**
     * 业务类型列表
     * @return
     */
    @PostMapping("listBussinessTypes")
    public Object listBussinessTypes(){
        List<String> teleNumGroupNameList = filterRuleService.listBussinessTypes();
        return ResponseUtil.ok(teleNumGroupNameList);
    }

    /**
     * 执行数据筛选并导入号码组
     * @return
     */
    @PostMapping("execute")
    public Object execute(SpecialFilterCondition specialFilterCondition){
        List<DataStorage> dataStorageList = filterRuleService.loadDataFilter(specialFilterCondition);
        List<DataStorage> list = DBDataUtil.dataStorageFilter(dataStorageList,specialFilterCondition.getFilterRatio());
        int i = packDataToPassNumDetail(list,specialFilterCondition.getTelenumGroupName());
        if(i <= 0){
            return ResponseUtil.fail("插入数据失败");
        }
        return ResponseUtil.ok();
    }

    /**
     * 执行数据筛选并导入号码组
     * @return
     */
    @PostMapping("count")
    public Object count(SpecialFilterCondition specialFilterCondition){
        int countNum = filterRuleService.countDataFilter(specialFilterCondition);
        int num = (countNum * specialFilterCondition.getFilterRatio()) / 100;
        return ResponseUtil.ok(num);
    }

    /**
     * 将数据装入相应的号码组中
     * @param dataStorages
     */
    private int packDataToPassNumDetail(List<DataStorage> dataStorages,String telenumGroupName){
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
            passiveNumDetail.setTelenumGroupName(telenumGroupName);
            //专项筛选
            passiveNumDetail.setOrigin(3);
            passiveNumDetails.add(passiveNumDetail);
        }
        return passiveNumService.addPassivenumDetailByPassiveNumId(passiveNumDetails);
    }
}
