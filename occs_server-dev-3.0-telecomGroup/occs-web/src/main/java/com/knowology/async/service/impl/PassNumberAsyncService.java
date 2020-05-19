package com.knowology.async.service.impl;

import com.knowology.controller.tele.PassiveNumController;
import com.knowology.model.PassiveNum;
import com.knowology.model.PassiveNumDetail;
import com.knowology.service.PassiveNumService;
import com.knowology.util.EasyExcelUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * 异步执行excel上传号码组入库
 */
@Service
public class PassNumberAsyncService {

    @Autowired
    private PassiveNumService passiveNumService;
    private static final Logger logger = LoggerFactory.getLogger(PassiveNumController.class);
    @Async("asyncPoolTaskExecutor")
    public void excute(MultipartFile file, PassiveNum passiveNum){
        List<PassiveNumDetail> passiveNumDetailsArrayList = judgeFile(file);
        logger.info("passiveNumDetailsArrayList : " + passiveNumDetailsArrayList);
        passiveNumService.addPassiveNum(passiveNum,passiveNumDetailsArrayList);
    }

    public static List<PassiveNumDetail> judgeFile(MultipartFile file) {
        List<PassiveNumDetail> passiveNumDetailsArrayList = new ArrayList<>();
        List<Object> list = EasyExcelUtil.readExcel(file,PassiveNumDetail.class);
        for(Object object : list){
            passiveNumDetailsArrayList.add((PassiveNumDetail) object);
        }
        return passiveNumDetailsArrayList;
    }
}
