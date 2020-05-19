package com.knowology.util;

import com.alibaba.fastjson.JSONArray;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 将时间策略转换为cron表达式，用于JOB来绑定执行
 * @author xullent
 */
@Component
public class CronExpressionUtil {

    private static String cronProfix;

    @Value("${task.jobdetail.cron}")
    public void setCronProfix(String cron) {
        cronProfix = cron;
    }

    public static String getCronExpression(String executeTime,String week){
        StringBuffer cronExp = new StringBuffer("");
        //默认情况下三十秒执行一次
        cronExp.append( cronProfix + " ");
        cronExp.append(hourChange(executeTime) + " ");
        cronExp.append("? * ");
        cronExp.append(weekChange(week));
        return cronExp.toString();
    }

    /**
     * @see org.quartz.CronExpression
     * @param week
     * 周一,周二,周三,周四,周五,周六,周日
     * @return
     */
    private static String weekChange(String week){
        StringBuilder weekNum = new StringBuilder();
        if(week.contains("周日")){
            weekNum.append("1,");
        }
        if(week.contains("周一")){
            weekNum.append("2,");
        }
        if(week.contains("周二")){
            weekNum.append("3,");
        }
        if(week.contains("周三")){
            weekNum.append("4,");
        }
        if(week.contains("周四")){
            weekNum.append("5,");
        }
        if(week.contains("周五")){
            weekNum.append("6,");
        }
        if(week.contains("周六")){
            weekNum.append("7,");
        }
        if(weekNum.toString().contains(",")){
            return weekNum.substring(0,weekNum.length()-1);
        }
        return weekNum.toString();
    }

    /**
     * 拼接小时的时间段
     * @param executeTime
     * @return
     * [{"beginTime":"00:00:00","endTime":"23:59:59"}]
     */
    private static String hourChange(String executeTime){
        StringBuilder hourNum = new StringBuilder();

        JSONArray jsonArray = JSONArray.parseArray(executeTime);
        for(int i=0;i<jsonArray.size();i++){
           hourNum.append(jsonArray.getJSONObject(i).getString("beginTime").split(":")[0] + "-" + jsonArray.getJSONObject(i).getString("endTime").split(":")[0] + ",");
        }
        if(hourNum.toString().contains(",")){
            return hourNum.substring(0,hourNum.length()-1);
        }
        return hourNum.toString();
    }
}
