package com.knowology.controller.analysis;


import com.knowology.model.Job;
import com.knowology.po.RealtimeExcute;
import com.knowology.request.CallOutMonitorQuery;
import com.knowology.service.CallOutMonitorService;
import com.knowology.service.JobService;
import com.knowology.util.ResponseUtil;
import com.sun.management.OperatingSystemMXBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.management.ManagementFactory;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 外呼情况监控
 * @author  xullent
 */
@SuppressWarnings("restriction")
@RestController
@RequestMapping("monitor")
public class CallOutMonitorController {

    private static final OperatingSystemMXBean operatingSystemMXBean = (OperatingSystemMXBean)ManagementFactory.getOperatingSystemMXBean();

    @Autowired
    private CallOutMonitorService callOutMonitorService;
    @Autowired
    private JobService jobService;

    /**
     * 加载监控数据
     * @return
     */
    @PostMapping("load")
    public Object loadCallOutMonitorResult(Job job){
        //任务名为空时按照全局检索
        String jobName = job.getJobName();
        Map<String, Object> map = new HashMap<>(8);
        RealtimeExcute realtimeExcuteResult = callOutMonitorService.listRealtimeExcute(jobName);
        List<Map<String,String>> callStatusList = callOutMonitorService.listCallStatus(jobName);
        List<Map<String,String>> talkTimeList = callOutMonitorService.listTalkTime(jobName);
        List<Map<String,String>> roundsList = callOutMonitorService.listRounds(jobName);
        List<Map<String,String>> hangupList = callOutMonitorService.listHangup(jobName);

        //总呼出
        map.put("total",realtimeExcuteResult.getTotal());
        //任务完成率
        map.put("completion",realtimeExcuteResult.getCompletion());
        //呼出成功率
        map.put("success",realtimeExcuteResult.getSuccess());
        //今日呼出
        map.put("todayAmount",realtimeExcuteResult.getTodayAmount());
        //呼叫状态统计
        map.put("callStatus",callStatusList);
        //通话时长统计
        map.put("talkTime",talkTimeList);
        //交互轮次统计
        map.put("rounds",roundsList);
        //挂断方统计
        map.put("hangup",hangupList);

        return ResponseUtil.ok(map);
    }

    /**
     * 总监控数据
     * @param callOutMonitorQuery
     * @return
     */
    @PostMapping("loadDetails")
    public Object loadDetailsCallOutMonitorResult(CallOutMonitorQuery callOutMonitorQuery){
        //任务名为空时按照全局检索
        String jobName = callOutMonitorQuery.getJobName();
        Map<String, Object> map = new HashMap<>(8);
        RealtimeExcute realtimeExcuteResult = callOutMonitorService.listRealtimeExcute(jobName);
        List<Map<String,String>> callStatusList = callOutMonitorService.listCallStatus(jobName);
        List<Map<String,String>> talkTimeList = callOutMonitorService.listTalkTime(jobName);
        List<Map<String,String>> roundsList = callOutMonitorService.listRounds(jobName);
        List<Map<String,String>> hangupList = callOutMonitorService.listHangup(jobName);
        //测评服务总量
        map.put("serviceTotal",realtimeExcuteResult.getTotal());
        //测评呼叫量
        map.put("serviceCallNum",realtimeExcuteResult.getSuccess());

        //测评成功量
        map.put("successNum","");
        //测评成功率
        map.put("successRate","");
        //测评接通数量
        map.put("connectNum","");
        //测评接通率
        map.put("connectRate","");
        //总交互量
        map.put("allRounds","");
        //语音识别成功数量
        map.put("voiceRecogSuccessNum","");
        //语音识别成功率
        map.put("voiceRecogSuccessRate","");
        //语义理解成功数量
        map.put("semanticRecogSuccessNum","");
        //语义理解成功率
        map.put("semanticRecogSuccessRate","");
        //标注数量
        map.put("labelNum","");
        //语音识别准确率
        map.put("voiceRecogExactNum","");
        //语义理解准确率
        map.put("semanticRecogExactRate","");
        //呼叫状态统计
        map.put("callStatus",callStatusList);
        //通话时长统计
        map.put("talkTime",talkTimeList);
        //交互轮次统计
        map.put("rounds",roundsList);
        //挂断方统计
        map.put("hangup",hangupList);

        return ResponseUtil.ok(map);
    }
    /**
     * 系统监控，内存,cpu
     * @return
     */
    @PostMapping("sysMonitor")
    public Object sysMonitor() {
        BigDecimal bigDecimal = new BigDecimal(operatingSystemMXBean.getSystemCpuLoad()*100);
        //cpu占比
        double systemCpuLoad = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        //总内存
        long totalPhysicalMemorySize = operatingSystemMXBean.getTotalPhysicalMemorySize()/(1024*1024*1024);
        //空闲内存
        long freePhysicalMemorySize = operatingSystemMXBean.getFreePhysicalMemorySize() / (1024 * 1024 * 1024);
        Map<String,Object> map = new HashMap<>(4);
        map.put("systemCpuLoad",systemCpuLoad);
        map.put("totalMemorySize",totalPhysicalMemorySize);
        map.put("usedMemorySize",(totalPhysicalMemorySize-freePhysicalMemorySize));
            return map;
    }


    @PostMapping("listJobName")
    public Object listTaskName() {
        List<String> jobNames = jobService.listAllJobName();
        return ResponseUtil.ok(jobNames);
    }
}
