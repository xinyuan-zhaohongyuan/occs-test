package com.knowology.util;

import com.knowology.model.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author : Conan
 * @Description 处理数据库查询出来的内容工具类
 * @date : 2019-05-16 16:44
 **/

public class DBDataUtil {

    @SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(DBDataUtil.class);
    /**
     * 电话号脱敏处理,13788886666脱敏为137****6666
     * @param jobDetails
     * @return
     */
    public static void desensitizationJobdetail(List<JobDetail> jobDetails) {
        if (jobDetails != null && jobDetails.size()>0){
            for (JobDetail j: jobDetails) {
                if (StringUtils.isNotBlank(j.getPassiveNum())) {
                    StringBuilder passiveNum = new StringBuilder(j.getPassiveNum());
                    //137+*****正好7位,标准手机号是11位
                    if (passiveNum != null && passiveNum.length() > 7) {
                        passiveNum.replace(3,7,"****");
                        j.setPassiveNum(passiveNum.toString());
                    }
                }
            }
        }
    }

    /**
     * 号码组电话号码脱敏
     * @param passiveNums
     */
    public static void desensitizationPassivenum(List<PassiveNumDetail> passiveNums) {
        if (passiveNums != null && passiveNums.size()>0){
            for (PassiveNumDetail p: passiveNums) {
                if (StringUtils.isNotBlank(p.getPhoneNum())) {
                    StringBuilder passiveNum = new StringBuilder(p.getPhoneNum());
                    //137+*****正好7位,标准手机号是11位
                    if (passiveNum != null && passiveNum.length() > 7) {
                        passiveNum.replace(3,7,"****");
                        p.setPhoneNum(passiveNum.toString());
                    }
                }
            }
        }
    }

    /**
     * 在这里进行数据的等比缩小,筛选模式很简单，产生随机数随机抽取
     * @param dataStorages
     * @param filterRatio
     * @return
     */
    public static List<DataStorage> dataStorageFilter(List<DataStorage> dataStorages, Integer filterRatio){

        if(dataStorages ==null || dataStorages.size() <= 0){
            return null;
        }
        //生成新的list集合
        List<DataStorage> resultList=new ArrayList<DataStorage>();
        Random random=new Random();
        //临时存放产生的list索引，去除重复的索引
        List<Integer> tempList=new ArrayList<>();
        int temp=0;
        //计算出要抽取的数量
        int count = (dataStorages.size()*filterRatio) / 100;
        for(int i=0;i<Math.ceil(count);i++){
            //初始化一个随机数，将产生的随机数作为被抽list的索引
            temp=random.nextInt(dataStorages.size());
            //判断随机抽取的随机数
            if(!tempList.contains(temp)){
                tempList.add(temp);
                resultList.add(dataStorages.get(temp));
            }
            else{
                i--;
            }
        }
        return resultList;
    }

    /**
     * 短信号码脱敏
     * @param shortMsgDetails
     */
    public static void desensitizationShortMsgDetail(List<ShortMsgDetail> shortMsgDetails) {
        if (shortMsgDetails != null && shortMsgDetails.size()>0){
            for (ShortMsgDetail s: shortMsgDetails) {
                if (StringUtils.isNotBlank(s.getTelenum())) {
                    StringBuilder passiveNum = new StringBuilder(s.getTelenum());
                    //137+*****正好7位,标准手机号是11位
                    if (passiveNum != null && passiveNum.length() > 7) {
                        passiveNum.replace(3,7,"****");
                        s.setTelenum(passiveNum.toString());
                    }
                }
            }
        }
    }

    /**
     * 数组转换字符串
     * @param strings
     * @return
     */
    public static String arrayTransformString(String[] strings){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < strings.length; i++) {
            if (i < strings.length - 1) {
                sb.append(strings[i].replace("\"", "") + ",");
            } else {
                sb.append(strings[i].replace("\"", ""));
            }
        }
        return sb.toString();
    }

    public static ArrayList<String> arrayListTransformArray(String[] strings){
        ArrayList<String> list = new ArrayList<>();

        for (int i = 0; i < strings.length; i++) {
            list.add(strings[i].replace("\"", "").replace("[", "").replace("]", ""));
        }
        return list;
    }
}
