package com.knowology.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.knowology.dao.JobDetailMapper;
import com.knowology.dao.JobMapper;
import com.knowology.dao.PassiveNumDetailMapper;
import com.knowology.dao.PassiveNumMapper;
import com.knowology.model.PassiveNum;
import com.knowology.model.PassiveNumDetail;
import com.knowology.service.PassiveNumService;
import com.knowology.util.DBDataUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

@Transactional(rollbackFor = Exception.class)
@Service
public class PassiveNumServiceImpl implements PassiveNumService {

    @Resource
    private PassiveNumMapper passiveNumMapper;
    @Resource
    private PassiveNumDetailMapper passivenumDetailsMapper;
    @Resource
    private JobMapper jobMapper;
    @Resource
    private JobDetailMapper jobDetailMapper;

    @Override
    public PageInfo<PassiveNum> select(Integer pageNum, Integer pageSize, String telenumGroupName) {
        PageHelper.startPage(pageNum,pageSize);
        List<PassiveNum> passiveNums = passiveNumMapper.selectPassiveNumByTelenumGroupName(telenumGroupName);
        PageInfo<PassiveNum> info = new PageInfo<>(passiveNums);
        return info;
    }

    @Override
    public PassiveNum selectTeleNumGroupInfo(Integer id) {
        return passiveNumMapper.selectTeleNumGroupInfoById(id);
    }

    @Override
    public PageInfo<PassiveNumDetail> listDetail(Integer id, Integer pageSize, Integer pageNum,String passiveNum) {
        PageHelper.startPage(pageNum,pageSize);
        List<PassiveNumDetail> passivenumDetails = passivenumDetailsMapper.selectTeleNumGroupDetailById(id,passiveNum);
        DBDataUtil.desensitizationPassivenum(passivenumDetails);
        PageInfo<PassiveNumDetail> info = new PageInfo<>(passivenumDetails);
        return info;
    }

    @Override
    public void addPassiveNum(PassiveNum passiveNum, List<PassiveNumDetail> passiveNumDetailsArrayList) {

        final int passiveNumId = passiveNumMapper.insert(passiveNum);
        passiveNumDetailsArrayList.forEach(passiveNumDetail -> {
            passiveNumDetail.setPassivenumId(passiveNumId);
            passiveNumDetail.setTelenumGroupName(passiveNum.getTelenumGroupName());
        });
        passivenumDetailsMapper.insertList(passiveNumDetailsArrayList);
    }

    @Override
    public int deleteDetail(Integer id) {
        return passivenumDetailsMapper.deleteDetailById(id);
    }

    @Override
    public void deletePassiveNum(Integer id) {
        PassiveNum passiveNum = passiveNumMapper.selectTeleNumGroupInfoById(id);
        //删除号码组
        Example example = new Example(PassiveNum.class);
        example.createCriteria().andEqualTo("id",id);
        passiveNumMapper.deletePassiveNumById(id);
        //删除号码组详情
        passivenumDetailsMapper.deleteDetailByPassiveNumName(passiveNum.getTelenumGroupName());
    }

    /**
     * 追加号码，并添加至z_job_detail中
     * @param telenumGroupName
     * @param passiveNumDetailsArrayList
     */
    @Override
    public void addPassiveNumDetail(String telenumGroupName, List<PassiveNumDetail> passiveNumDetailsArrayList) {
        // 追加号码，并添加至z_job_detail中
//        passivenumDetailsMapper.addPassivenumDetailByPassiveNumId(passiveNumDetailsArrayList);
        passivenumDetailsMapper.insertList(passiveNumDetailsArrayList);
//        Example example = new Example(Job.class);
//        example.selectProperties("jobName").createCriteria().andEqualTo("telenumGroupName",telenumGroupName);
//        List<Job> jobs = jobMapper.selectByExample(example);
//        List<String> jobNames = jobs.stream().map(Job::getJobName).collect(Collectors.toList());
//        List<JobDetail> addJobDetail = new LinkedList<>();
//        for (String jobName: jobNames){
//            passiveNumDetailsArrayList.forEach(passiveNumDetail -> {
//                JobDetail jobDetail = new JobDetail();
//                jobDetail.setJobName(jobName);
//                jobDetail.setDealTimes(0);
//                jobDetail.setDealStatus(Job.WAIT_CALL);
//                jobDetail.setPassiveNum(passiveNumDetail.getPhoneNum());
//                jobDetail.setClientName(passiveNumDetail.getClientName());
//                jobDetail.setBusinessHallName(passiveNumDetail.getBusinessHallName());
//                addJobDetail.add(jobDetail);
//            });
//        }
//        jobDetailMapper.insertList(addJobDetail);

    }

    @Override
    public int updatePassiveNum(PassiveNum passiveNum) {
        return passiveNumMapper.updatePassiveNumById(passiveNum);
    }

    @Override
    public List<PassiveNum> selectTeleNumGroupName() {
        List<PassiveNum> passiveNumList = passiveNumMapper.selectTeleNumGroupName();
        return passiveNumList;
    }

    @Override
    public int add(PassiveNum passiveNum) {
        return passiveNumMapper.addPassiveNum(passiveNum);
    }

    @Override
    public List<PassiveNumDetail> listPassiveNumDetail(Integer id) {
        List<PassiveNumDetail> passiveNumDetails = passivenumDetailsMapper.selectTeleNumGroupDetailById(id,"");
        return passiveNumDetails;
    }
    /**
     * 根据号码名称判断是否号码组名称已经存在
     * @param telenumGroupName
     * @return
     */
    @Override
    public int selectPassiveNumByName(String telenumGroupName) {
        return passiveNumMapper.selectPassiveNumByName(telenumGroupName);
    }

    @Override
    public List<PassiveNum> selectAll() {
        return passiveNumMapper.selectAll();
    }

    @Override
    public List<PassiveNumDetail> selectNoSynByTelenumGroupName(String telenumGroupName, Integer dealNum) {
        return passivenumDetailsMapper.selectNoSynByTelenumGroupName(telenumGroupName,dealNum);
    }

    @Override
    public int addPassivenumDetailByPassiveNumId(List<PassiveNumDetail> passiveNumDetailList) {
        return passivenumDetailsMapper.addPassivenumDetailByPassiveNumId(passiveNumDetailList);
    }

    @Override
    public List<PassiveNumDetail> selectByTelenumName(String telenumName) {
        Example example = new Example(PassiveNumDetail.class);
        //查出接受回访的电话号列表
        example.selectProperties("id","phoneNum","clientName","businessHallName")
                .createCriteria()
                .andEqualTo("telenumGroupName",telenumName);
        return passivenumDetailsMapper.selectByExample(example);
    }

}








