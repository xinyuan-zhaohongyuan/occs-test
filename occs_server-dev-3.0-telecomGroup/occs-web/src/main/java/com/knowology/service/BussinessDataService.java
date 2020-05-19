package com.knowology.service;

import com.knowology.excelVo.*;

import java.util.List;

public interface BussinessDataService {
    /**
     * 营业厅服务满意率数据入库
     * @param list
     * @return
     */
    int insertBussinessHallData(List<BussinessHall> list);

    /**
     * 政企交付满意度/智慧家庭交付满意率数据入库
     * @param list
     * @return
     */
    int insertGovernDeliverylData(List<GovernDelivery> list);

    /**
     * 营业厅服务满意率excel导出的样本接触状态sheet数据
     * @return
     */
    List<BussinessHallSample> selectBussinessHallSampleResult(String jobName);

    /**
     * 营业厅服务满意率excel导出的测评结果sheet数据
     * @return
     */
    List<BussinessHallResult> selectBussinessHallResult(String jobName);

    /**
     * 政企交付满意度/智慧家庭交付满意率excel导出的样本接触状态sheet数据
     * @param jobName
     * @return
     */
    List<GovernDeliverySample> selectGovernDeliverySampleResult(String jobName);

    /**
     * 政企交付满意度/智慧家庭交付满意率excel导出的测评结果sheet数据
     * @param jobName
     * @return
     */
    List<GovernDeliveryResult> selectGovernDeliveryResult(String jobName);
	 /**
	   * 更新intentfind
	   * @param id
	   * @param intentfind
	   */
    void updateIntentfind(String id,String intentfind);
}
