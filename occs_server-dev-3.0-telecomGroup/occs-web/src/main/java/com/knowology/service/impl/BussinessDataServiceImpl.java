package com.knowology.service.impl;

import com.knowology.dao.BussinessDataMapper;
import com.knowology.excelVo.*;
import com.knowology.service.BussinessDataService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
//import java.util.ArrayList;
import java.util.List;

@Transactional(rollbackFor = Exception.class)
@Service
public class BussinessDataServiceImpl implements BussinessDataService {
    @Resource
    private BussinessDataMapper bussinessDataMapper;
    @Override
    public int insertBussinessHallData(List<BussinessHall> list) {
        return bussinessDataMapper.insertBussinessHallData(list);
    }

    @Override
    public int insertGovernDeliverylData(List<GovernDelivery> list) {
        return bussinessDataMapper.insertGovernDeliverylData(list);
    }

    @Override
    public List<BussinessHallSample> selectBussinessHallSampleResult(String jobName) {
        return bussinessDataMapper.selectBussinessHallSampleResult(jobName);
    }

    @Override
    public List<BussinessHallResult> selectBussinessHallResult(String jobName) {
        List<BussinessHallResult> list = bussinessDataMapper.selectBussinessHallResult(jobName);
        return list;//bussinessHallDetails(list);
    }

    @Override
    public List<GovernDeliverySample> selectGovernDeliverySampleResult(String jobName) {
        return bussinessDataMapper.selectGovernDeliverySampleResult(jobName);
    }

    @Override
    public List<GovernDeliveryResult> selectGovernDeliveryResult(String jobName) {
        return bussinessDataMapper.selectGovernDeliveryResult(jobName);
    }

//    private List<BussinessHallResult> bussinessHallDetails(List<BussinessHallResult> list){
//        List<BussinessHallResult> bussinessHallResultList = new ArrayList<>();
//
//        for(BussinessHallResult bussinessHallResult : list){
//            if(bussinessHallResult.getSatisReasion() != null){
//                bussinessHallResult.setSatisfied("满意");
//                if(bussinessHallResult.getSatisReasionTxt() != null){
//                    if("业务办理速度快".equals(bussinessHallResult.getSatisReasionTxt())) {
//                        bussinessHallResult.setSpeed(bussinessHallResult.getSatisReasionTxt());
//                    }else if("业务办理速度快".equals(bussinessHallResult.getSatisReasionTxt())){
//                        bussinessHallResult.setSpeed(bussinessHallResult.getSatisReasionTxt());
//                    }else if("排队时间短".equals(bussinessHallResult.getSatisReasionTxt())){
//                        bussinessHallResult.setShortQueuing(bussinessHallResult.getSatisReasionTxt());
//                    }else if("秩序良好".equals(bussinessHallResult.getSatisReasionTxt())){
//                        bussinessHallResult.setEunomy(bussinessHallResult.getSatisReasionTxt());
//                    }else if("环境干净整洁".equals(bussinessHallResult.getSatisReasionTxt())){
//                        bussinessHallResult.setCleanEnvironment(bussinessHallResult.getSatisReasionTxt());
//                    }else if("态度好".equals(bussinessHallResult.getSatisReasionTxt())){
//                        bussinessHallResult.setGoodAttitude(bussinessHallResult.getSatisReasionTxt());
//                    }else if("业务熟练".equals(bussinessHallResult.getSatisReasionTxt())){
//                        bussinessHallResult.setBusinessSkilled(bussinessHallResult.getSatisReasionTxt());
//                    }else if("办理过程简单".equals(bussinessHallResult.getSatisReasionTxt())){
//                        bussinessHallResult.setSimpleProcess(bussinessHallResult.getSatisReasionTxt());
//                    }else if("顺利解决问题".equals(bussinessHallResult.getSatisReasionTxt())){
//                        bussinessHallResult.setSmoothProblems(bussinessHallResult.getSatisReasionTxt());
//                    }
//                }
//            }
//            if(bussinessHallResult.getNoSatisReasion() != null) {
//                bussinessHallResult.setSatisfied("不满意");
//                if(bussinessHallResult.getNoSatisReasionTxt() != null){
//                    if("业务办理时间长".equals(bussinessHallResult.getNoSatisReasionTxt())) {
//                        bussinessHallResult.setBussinesssLong(bussinessHallResult.getSatisReasionTxt());
//                    }else if("排队时间长".equals(bussinessHallResult.getNoSatisReasionTxt())){
//                        bussinessHallResult.setWaitingLong(bussinessHallResult.getSatisReasionTxt());
//                    }else if("秩序混乱".equals(bussinessHallResult.getNoSatisReasionTxt())){
//                        bussinessHallResult.setOutOfOder(bussinessHallResult.getSatisReasionTxt());
//                    }else if("营业厅环境脏乱差".equals(bussinessHallResult.getNoSatisReasionTxt())){
//                        bussinessHallResult.setEnvironBad(bussinessHallResult.getSatisReasionTxt());
//                    }else if("服务态度差".equals(bussinessHallResult.getNoSatisReasionTxt())){
//                        bussinessHallResult.setServiceAwful(bussinessHallResult.getSatisReasionTxt());
//                    }else if("业务能力差".equals(bussinessHallResult.getNoSatisReasionTxt())){
//                        bussinessHallResult.setBussinessAwful(bussinessHallResult.getSatisReasionTxt());
//                    }else if("业务办理过程繁琐".equals(bussinessHallResult.getNoSatisReasionTxt())){
//                        bussinessHallResult.setBussinessFussy(bussinessHallResult.getSatisReasionTxt());
//                    }else if("设备故障".equals(bussinessHallResult.getNoSatisReasionTxt())){
//                        bussinessHallResult.setEquipmentFailure(bussinessHallResult.getSatisReasionTxt());
//                    }
//                }
//            }
//            bussinessHallResultList.add(bussinessHallResult);
//        }
//
//        return bussinessHallResultList;
//    }

	@Override
	public void updateIntentfind(String id ,String intentfind) {
		bussinessDataMapper.updateIntentfind(id, intentfind);
	}
}
