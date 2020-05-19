package com.knowology.dao;

import com.knowology.config.MyMapper;
import com.knowology.excelVo.*;
import com.knowology.model.BussinessData;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BussinessDataMapper extends MyMapper<BussinessData> {

    /**
     * 检索今日数据
     * @param flag 任务的类型标志
     * @return
     */
    List<BussinessData> selectInnerData(@Param("flag") Integer flag);

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
    List<BussinessHallSample> selectBussinessHallSampleResult(@Param("jobName") String jobName);

    /**
     * 营业厅服务满意率excel导出的测评结果sheet数据
     * @return
     */
    List<BussinessHallResult> selectBussinessHallResult(@Param("jobName") String jobName);

    /**
     * 政企交付满意度/智慧家庭交付满意率excel导出的样本接触状态sheet数据
     * @param jobName
     * @return
     */
    List<GovernDeliverySample> selectGovernDeliverySampleResult(@Param("jobName") String jobName);

    /**
     * 政企交付满意度/智慧家庭交付满意率excel导出的测评结果sheet数据
     * @param jobName
     * @return
     */
    List<GovernDeliveryResult> selectGovernDeliveryResult(@Param("jobName") String jobName);
    /**
     * 更新intentfind
     * @param intentfind
     */
    void updateIntentfind(@Param("id") String id,@Param("intentfind")String intentfind);
}