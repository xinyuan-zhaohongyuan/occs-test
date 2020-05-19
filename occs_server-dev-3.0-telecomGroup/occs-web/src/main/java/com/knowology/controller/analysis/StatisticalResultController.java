package com.knowology.controller.analysis;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.github.pagehelper.PageInfo;
import com.knowology.model.JobDetail;
import com.knowology.po.Reason;
import com.knowology.po.StatiscalResult;
import com.knowology.service.StatisticalResultService;
import com.knowology.util.DBDataUtil;
import com.knowology.util.ResponseUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @author : Conan
 * @Description 反馈结果统计
 * @date : 2019-04-25 14:55
 **/

@RestController
@RequestMapping("statistical")
public class StatisticalResultController {

    @Autowired
    private StatisticalResultService statisticalService;

    /**
     * 整体满意度
     * @param province
     * @return
     */
    @PostMapping("load")
    public Object load(String province){
        List<Map<String,String>> overAllResult = statisticalService.listOverAllResult(province);
        return ResponseUtil.ok(overAllResult);
    }

    /**
     * 排名数据 NEW
     * @param province
     * @return
     */
    @PostMapping("overAllRank")
        public Object listOverAllRank(@RequestParam(defaultValue = "1") Integer pageNum,
                                      @RequestParam(defaultValue = "10") Integer pageSize, String province){

        PageInfo<StatiscalResult> list = statisticalService.listOverAllRankResult(pageNum,pageSize,province);
        Map<String, Object> map = new HashMap<>();
        map.put("total", list.getTotal());
        map.put("items", list.getList());
        return ResponseUtil.ok(map);
    }

    /**
     * 不满意结果统计
     * @return
     */
    @PostMapping("unStatiscal")
    public Object unStatiscalResult(){
        List<Map<String,String>> unStatiscalResult = statisticalService.unStatiscalResult();
        if(unStatiscalResult == null || unStatiscalResult.size() < 0){
            return ResponseUtil.ok();
        }
        for(int i =0;i < unStatiscalResult.size();i++){
            unStatiscalResult.get(i).put("rank",i+"");
        }
        return ResponseUtil.ok(unStatiscalResult);
    }
    /**
     * 具体不满意原因列表
     */
    @PostMapping("unStatiscalList")
    public Object unStatiscalList(@RequestParam("node") String node) {
        if (StringUtils.isBlank(node)) {
            return ResponseUtil.fail();
        }
        List<Reason> result = statisticalService.unStatiscalList(node);
        return ResponseUtil.ok(result);
    }
    /**
     * 导出不满意数据
     * @param response
     * @return
     * @throws Exception
     */
    @PostMapping("exportExcel")
    public Object exportExcel(HttpServletResponse response) throws Exception {
        ExcelTypeEnum type=ExcelTypeEnum.XLSX;
        response.setContentType("multipart/form-data");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename=" + "不满意情况统计表" + type.getValue());
        ServletOutputStream out = response.getOutputStream();
        ExcelWriter writer = new ExcelWriter(out, type, true);
        List<JobDetail> lists = statisticalService.listJobDetails("");
        DBDataUtil.desensitizationJobdetail(lists);
        Sheet sheet1=new Sheet(1,0,JobDetail.class);
        sheet1.setSheetName("信息表");
        writer.write(lists,sheet1);
        writer.finish();
        // 关闭流
        out.close();
        return null;
    }

    @PostMapping("listProvince")
    public Object listProvince() {
        List<String> provinces = statisticalService.listProvinces();
        return ResponseUtil.ok(provinces);
    }
}
