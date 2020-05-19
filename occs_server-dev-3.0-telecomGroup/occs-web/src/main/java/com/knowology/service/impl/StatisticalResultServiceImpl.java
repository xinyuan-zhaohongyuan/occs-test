package com.knowology.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.knowology.annotation.Comment;
import com.knowology.constant.DataConstant;
import com.knowology.dao.JobDetailMapper;
import com.knowology.dao.StatiscalResultMapper;
import com.knowology.model.JobDetail;
import com.knowology.po.OverAll;
import com.knowology.po.Reason;
import com.knowology.po.StatiscalResult;
import com.knowology.po.UnStatiscalResult;
import com.knowology.service.StatisticalResultService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.Column;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author : Conan
 * @Description 反馈结果统计
 * @date : 2019-04-26 14:21
 **/
@Transactional(rollbackFor = Exception.class)
@Service
public class StatisticalResultServiceImpl implements StatisticalResultService {
    private static final Logger logger = LoggerFactory.getLogger(StatisticalResultServiceImpl.class);

    @Resource
    private JobDetailMapper jobDetailMapper;
    @Resource
    private StatiscalResultMapper statiscalResultMapper;



    @Override
    public List<String> listProvinces() {
        List<String> provinces = statiscalResultMapper.listProvinces();
        return provinces;
    }

    private List<StatiscalResult> trimOverAllRankResultData(List<StatiscalResult> list) {
        List<StatiscalResult> statiscalResults = list;
        if (statiscalResults == null) {
            return null;
        }
        Iterator<StatiscalResult> iterator = statiscalResults.iterator();
        while (iterator.hasNext()) {
            StatiscalResult statiscalResult = (StatiscalResult) iterator.next();
            if (statiscalResult != null && StringUtils.isAnyBlank(statiscalResult.getProvince(), statiscalResult.getBusinessHallName())) {
                //排名数据为空，暂时去掉
                iterator.remove();
            }
        }
        return statiscalResults;
    }



    /**
     * 反射set返回值
     *
     * @param object
     * @param classname
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unused", "unchecked" })
	private List<Map<String, String>> reflectSetPieCharts(Object object, Class classname) {
        Field[] fields = classname.getDeclaredFields();
        PropertyDescriptor pd = null;
        Comment comment = null;
        Column column = null;
        List<Map<String, String>> result = new ArrayList<>();
        HashMap map = null;
        try {
            for (int i = 0; i < fields.length; i++) {
                map = new HashMap();
                pd = new PropertyDescriptor(fields[i].getName(), classname);
                if (fields[i].isAnnotationPresent(Comment.class) &&
                        fields[i].isAnnotationPresent(Column.class)) {
                    comment = fields[i].getAnnotation(Comment.class);
                    column = fields[i].getAnnotation(Column.class);

                    Method getMethod = pd.getReadMethod();
                    //过滤字段为null的列
                    if (object == null || getMethod.invoke(object) == null) {
                        continue;
                    }
                    map.put("key", comment.value());
                    map.put("value", getMethod.invoke(object));
                    map.put("column", column.name());
                    result.add(map);
                }
            }
        } catch (IntrospectionException | IllegalAccessException | IllegalArgumentException |
                InvocationTargetException e) {
            logger.error("reflect " + classname + " has error,Object is[" + object + "]:", e);
        }
        return result;
    }

    /**
     * 加载JobDetail列表
     *
     * @param jobName
     * @return
     */
    @Override
    public List<JobDetail> listJobDetails(String jobName) {
        List<JobDetail> jobDetailList = jobDetailMapper.listJobDetails(jobName);
        return jobDetailList;
    }

    /**
     * 整体满意度饼图数据
     *
     * @param province
     * @return
     */
    @Override
    public List<Map<String, String>> listOverAllResult(String province) {
        OverAll overAllResult = statiscalResultMapper.listOverAll(province);
        return reflectSetPieCharts3(overAllResult, OverAll.class);
    }

    /**
     * 整体满意度排名数据
     *
     * @param province
     * @return
     */
    @Override
    public PageInfo<StatiscalResult> listOverAllRankResult(Integer pageNum, Integer pageSize, String province) {
        PageHelper.startPage(pageNum,pageSize);
        List<StatiscalResult> list = statiscalResultMapper.listOverAllRank(province);
        PageInfo<StatiscalResult> info = new PageInfo<>(trimOverAllRankResultData(list));
        return info;
    }

    @Override
    public List<Map<String, String>> unStatiscalResult() {
        UnStatiscalResult unStatiscalResult = statiscalResultMapper.unStatiscalResult();
        if(unStatiscalResult == null){
            return new ArrayList<>();
        }
        return reflectSetPieCharts2(unStatiscalResult, UnStatiscalResult.class);
    }

    @Override
    public List<Reason> unStatiscalList(String node) {
        String column = null;
        if (DataConstant.WAIT_TIME.equals(node)) {
            column = DataConstant.WAIT_TIME_COLUMN;
        }else if (DataConstant.HALL_ENV.equals(node)) {
            column = DataConstant.HALL_ENV_COLUMN;
        }else if (DataConstant.SERVICE.equals(node)) {
            column = DataConstant.SERVICE_COLUMN;
        }else if (DataConstant.SKILL.equals(node)) {
            column = DataConstant.SKILL_COLUMN;
        }else if (DataConstant.OTHER.equals(node)) {
            column = DataConstant.OTHER_COLUMN;
        }
        List<Reason> result = statiscalResultMapper.unStatiscalList(column);
        return result;
    }
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private List<Map<String, String>> reflectSetPieCharts2(Object object, Class classname) {
        Field[] fields = classname.getDeclaredFields();
        PropertyDescriptor pd = null;
        Comment comment = null;
        List<Map<String, String>> result = new ArrayList<>();
        HashMap map = null;
        try {
            for (int i = 0; i < fields.length; i++) {
                map = new HashMap();
                pd = new PropertyDescriptor(fields[i].getName(), classname);
                if (fields[i].isAnnotationPresent(Comment.class)) {
                    comment = fields[i].getAnnotation(Comment.class);
                    Method getMethod = pd.getReadMethod();
                    map.put("key", comment.value());
                    System.out.println("getMethod : " + getMethod.toString());
                    System.out.println(getMethod.invoke(object) == null);
                    map.put("count", getMethod.invoke(object) == null? 0:getMethod.invoke(object));
                    result.add(map);
                }
            }
        } catch (IntrospectionException | IllegalAccessException | IllegalArgumentException |
                InvocationTargetException e) {
            logger.error("reflect " + classname + " has error,Object is[" + object + "]:", e);
        }
        return result;
    }
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private List<Map<String,String>> reflectSetPieCharts3(Object object, Class classname) {
        Field[] fields = classname.getDeclaredFields();
        PropertyDescriptor pd = null;
        Comment comment = null;
        List<Map<String,String>> result = new ArrayList<>();
        HashMap map = null;
        try {
            for (int i = 0; i < fields.length; i++) {
                map = new HashMap();
                pd = new PropertyDescriptor(fields[i].getName(),classname);
                if (fields[i].isAnnotationPresent(Comment.class) ) {
                    comment = fields[i].getAnnotation(Comment.class);

                    Method getMethod = pd.getReadMethod();

                    map.put("key",comment.value());
                    String objValue = "";
                    if(object != null  ){
                        objValue = getMethod.invoke(object).toString();
                    }

                    if(objValue.contains("-") && !"-".equals(objValue)){
                        System.out.println("objValue : " + objValue);
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
