package com.knowology.controller.task;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import com.knowology.config.annotation.FullName;
import com.knowology.model.TimeStrategy;
import com.knowology.service.TimeStrategyService;
import com.knowology.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 时间策略
 * @author xullent
 */
@RestController
@RequestMapping("timeStrategy")
public class TimeStrategyController {
    @SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(TimeStrategyController.class);
    @Autowired
    private TimeStrategyService timetrategyService;

    /**
     * 时间策略列表
     * @param pageNum
     * @param pageSize
     * @param strategyName
     * @return
     */
    @PostMapping("list")
    public Object listTimeStrategy(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              String strategyName){
        PageInfo<TimeStrategy> list = timetrategyService.selectStrategyList(pageNum,pageSize,strategyName);
        Map<String, Object> map = new HashMap<>(4);
        map.put("total", list.getTotal());
        map.put("items", list.getList());
        return ResponseUtil.ok(map);
    }

    /**
     * 删除时间策略
     * @param id
     * @return
     */
    @PreAuthorize("hasAuthority('temporalStrategy_delete')")
    @PostMapping("delete")
    public Object deleteStrategy(@RequestParam(value = "id", required = true) Long id) {
        if (id == null) {
            return ResponseUtil.fail("参数不能为空");
        }
        int deleteStartegy = timetrategyService.deleteStrategy(id);
        if (deleteStartegy <= 0) {
            return ResponseUtil.fail("时间策略不存在");
        }
        return ResponseUtil.ok();
    }

    /**
     * 新增时间策略
     * @param timeStrategy
     * @return
     */
    @PreAuthorize("hasAuthority('temporalStrategy_add')")
    @PostMapping("add")
    public Object addStrategy(@FullName String memberName, TimeStrategy timeStrategy){
        if( timeStrategy.getStrategyName() == null ||
            timeStrategy.getRepeatWeek() == null ||
            timeStrategy.getExecuteTime() == null){
            return ResponseUtil.fail("缺少时间策略必要参数");
        }
        //检测时间策略是否存在
        TimeStrategy strategy = timetrategyService.selectOneByStrategyName(timeStrategy.getStrategyName());
        if(strategy != null){
            return ResponseUtil.fail("策略名称已经存在!");
        }
        if(!isJSONValid(timeStrategy.getExecuteTime())){
            return ResponseUtil.fail("时间段格式错误!");
        }
        timeStrategy.setCreateTime(new Date());
        timeStrategy.setCreator(memberName);
        int addTimeStrategy = timetrategyService.addStrategy(timeStrategy);
        if (addTimeStrategy <= 0) {
            return ResponseUtil.fail("新增时间策略失败");
        }
        return ResponseUtil.ok();
    }

    /**
     * 暴力检测字符串是否可以转换为JSON
     * @param jsonInString
     * @return
     */
    private final boolean isJSONValid(String jsonInString ) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            mapper.readTree(jsonInString);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
