package com.knowology.controller.task;

import com.github.pagehelper.PageInfo;
import com.knowology.config.annotation.FullName;
import com.knowology.model.StrategyDetail;
import com.knowology.service.StrategyService;
import com.knowology.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 重呼策略
 * @author xullent
 */
@RestController
@RequestMapping("strategy")
public class StrategyController {
    @SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(StrategyController.class);
    @Autowired
    private StrategyService strategyService;

    /**
     * 重呼策略列表
     * @param pageNum
     * @param pageSize
     * @param strategyName
     * @return
     */
    @PostMapping("list")
    public Object listDetails(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              String strategyName){
        PageInfo<StrategyDetail> list = strategyService.selectStrategyList(pageNum,pageSize,strategyName);
        Map<String, Object> map = new HashMap<>(4);
        map.put("total", list.getTotal());
        map.put("items", list.getList());
        return ResponseUtil.ok(map);
    }

    /**
     * 查看重呼策略详情
     * @param id
     * @return
     */
    @PostMapping("select")
    public Object selectStartegy(Long id){
        if (id == null) {
            return ResponseUtil.fail("参数不能为空");
        }
        StrategyDetail strategyDetail = strategyService.selectStarttegy(id);
        return ResponseUtil.ok(strategyDetail);
    }

    /**
     * 删除重呼策略
     * @param id
     * @return
     */
    @PreAuthorize("hasAuthority('strategy_delete')")
    @PostMapping("delete")
    public Object deleteStrategy(@RequestParam(value = "id", required = true) Long id) {
        if (id == null) {
            return ResponseUtil.fail("参数不能为空");
        }
        int deleteStartegy = strategyService.deleteStrategy(id);
        if (deleteStartegy <= 0) {
            return ResponseUtil.fail("外呼策略不存在");
        }
        return ResponseUtil.ok();
    }

    /**
     * 新增重呼策略
     * @param memberName
     * @param strategyDetail
     * @return
     */
    @PreAuthorize("hasAuthority('strategy_add')")
    @PostMapping("add")
    public Object addStrategy(@FullName String memberName, StrategyDetail strategyDetail){
        if (strategyDetail.getStrategyName() == null ||
            strategyDetail.getAgainCallInterval() == null ||
            strategyDetail.getFailureCase() == null ||
            strategyDetail.getCallTimes() == null) {
            return ResponseUtil.fail("必要参数不能为空");
        }
        //检查策略名称已经存在
        StrategyDetail strategy = strategyService.selectOneByStrategyName(strategyDetail.getStrategyName());
        if(strategy != null){
            return ResponseUtil.fail("策略名称已经存在!");
        }
        //创建时间 创建人
        strategyDetail.setCreateTime(new Date());
        strategyDetail.setCreator(memberName);
        int addStrategy = strategyService.addStrategy(strategyDetail);
        if (addStrategy <= 0) {
            return ResponseUtil.fail("新增重呼策略失败");
        }
        return ResponseUtil.ok();
    }
}
