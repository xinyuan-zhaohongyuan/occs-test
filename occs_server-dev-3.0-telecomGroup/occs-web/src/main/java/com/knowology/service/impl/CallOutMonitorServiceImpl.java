package com.knowology.service.impl;

import com.knowology.annotation.Comment;
import com.knowology.dao.CallOutMonitorMapper;
import com.knowology.po.*;
import com.knowology.service.CallOutMonitorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 外呼情况监控统计
 * @author xullent
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class CallOutMonitorServiceImpl implements CallOutMonitorService {

    private static final Logger logger = LoggerFactory.getLogger(CallOutMonitorServiceImpl.class);

    @Resource
    private CallOutMonitorMapper callOutMonitorMapper;

    @Override
    public RealtimeExcute listRealtimeExcute(String jobName) {
        return callOutMonitorMapper.realtimeExcuteResult(jobName);
    }

    @Override
    public List<Map<String, String>> listCallStatus(String jobName) {
        CallStatus callStatusResult = callOutMonitorMapper.listCallStatusResult(jobName);

        return reflectSetCallOutMonitor(callStatusResult,CallStatus.class);
    }

    @Override
    public List<Map<String, String>> listTalkTime(String jobName) {
        TalkTime talkTimeResult = callOutMonitorMapper.listTalkTimeResult(jobName);
        return reflectSetCallOutMonitor(talkTimeResult,TalkTime.class);
    }

    @Override
    public List<Map<String, String>> listRounds(String jobName) {
        Rounds roundsResult = callOutMonitorMapper.listRoundsResult(jobName);
        return reflectSetCallOutMonitor(roundsResult,Rounds.class);
    }

    @Override
    public List<Map<String, String>> listHangup(String jobName) {
        Hangup hangupResult = callOutMonitorMapper.listHangupResult(jobName);
        return reflectSetCallOutMonitor(hangupResult,Hangup.class);
    }

    /**
     * 反射set返回值
     * 这里只用到Comment注解
     * @param object
     * @param classname
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private List<Map<String,String>> reflectSetCallOutMonitor(Object object, Class<?> classname) {
        Field[] fields = classname.getDeclaredFields();
        PropertyDescriptor pd = null;
        Comment comment = null;
        List<Map<String,String>> result = new ArrayList<>();
		HashMap map = null;
        try {
            for (int i = 0; i < fields.length; i++) {
                map = new HashMap(fields.length);
                pd = new PropertyDescriptor(fields[i].getName(),classname);
                if (fields[i].isAnnotationPresent(Comment.class) ) {
                    comment = fields[i].getAnnotation(Comment.class);
                    Method getMethod = pd.getReadMethod();
                    map.put("key",comment.value());
                    String objValue="";
                    if(object == null){
                        objValue="0-0";
                    }else{
                        objValue = getMethod.invoke(object).toString();
                    }
                    if(objValue.contains("-") && !"-".equals(objValue)){
                        String value = objValue.split("-")[0];
                        String count = objValue.toString().split("-")[1];
                        map.put("count",count);
                        map.put("value",value);
                    }else{
                        map.put("count",0);
                        map.put("value",0);
                    }
                    result.add(map);
                }
            }
        } catch (IntrospectionException | IllegalAccessException | IllegalArgumentException |
                InvocationTargetException e) {
            logger.error("reflect "+classname+" has error,Object is["+object+"]:",e);
        }
        return result;
    }
}
