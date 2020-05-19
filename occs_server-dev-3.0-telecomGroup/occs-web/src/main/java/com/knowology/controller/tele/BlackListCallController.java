package com.knowology.controller.tele;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.knowology.config.annotation.FullName;
import com.knowology.model.BlackListCall;
import com.knowology.service.BlackListCallService;
import com.knowology.util.EasyExcelUtil;
import com.knowology.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.*;


/**
 * 黑名单
 * @author xullent
 */
@RestController
@RequestMapping("blacknum")
public class BlackListCallController {
    @SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(BlackListCallController.class);

    @Autowired
    private BlackListCallService blackListCallService;


    @PostMapping("list")
    public Object blackListCallResult(@RequestParam(defaultValue = "1") Integer pageNum,
                                      @RequestParam(defaultValue = "10") Integer pageSize,
                                          String slurNum){
        PageInfo<BlackListCall>  list= blackListCallService.selectBlackListCallResult(pageNum,pageSize,slurNum);
        Map<String, Object> map = new HashMap<>(4);
        map.put("total", list.getTotal());
        map.put("items", list.getList());
        return ResponseUtil.ok(map);
    }

    /**
     * 删除黑名单
     * @param id
     * @return
     */
    @PreAuthorize("hasAuthority('blacklist_delete')")
    @PostMapping("delete")
    public Object deleteBlackNum(@RequestParam(value = "id", required = true) Long id) {
        if (id == null) {
            return ResponseUtil.fail("参数不能为空");
        }
        int deleteBlackNum = blackListCallService.deleteBlackNum(id);
        if (deleteBlackNum <= 0) {
            return ResponseUtil.fail("黑名单ID不存在");
        }
        return ResponseUtil.ok();
    }

    /**
     * 逐个输入
     * @return
     */
    @PostMapping("eachAdd")
    @PreAuthorize("hasAuthority('blacklist_add')")
    public Object eachAdd(@FullName String creator, @RequestParam String blackNumList) {
        JSONArray jsonArray = JSON.parseArray(blackNumList);
        if( jsonArray == null|| jsonArray.size() == 0 ){
            return ResponseUtil.fail("请添加号码");
        }
        for (int i = 0; i < jsonArray.size(); i++){
            JSONObject ob = (JSONObject) jsonArray.get(i);
            String blackNum= ob.getString("blackNum");
            List<BlackListCall> list = blackListCallService.selectBlackList(blackNum);
            if(list.size() > 0){
                continue;
            }
            BlackListCall blackListCall = new BlackListCall();
            blackListCall.setBlackNum(blackNum);
            blackListCall.setCreateTime(new Date());
            blackListCall.setCreator(creator);
            blackListCallService.addblackNum(blackListCall);
        }
        return ResponseUtil.ok();
    }

    /**
     * 通过文件形式导入
     * @return
     */
    @PreAuthorize("hasAuthority('blacklist_add')")
    @PostMapping("fileAdd")
    public Object fileAdd(@FullName String creator,MultipartFile file) {
        if (file == null ) {
            return ResponseUtil.fail("请选择文件");
        }
        if (file.getOriginalFilename().endsWith("xlsx") || file.getOriginalFilename().endsWith("xls")) {
            List<String> blackNumLists = judgeFile(file);
            for(String blackNum : blackNumLists){
                List<BlackListCall> list = blackListCallService.selectBlackList(blackNum);
                if(list.size() > 0){
                    continue;
                }
                BlackListCall blackListCall = new BlackListCall();
                blackListCall.setBlackNum(blackNum);
                blackListCall.setCreateTime(new Date());
                blackListCall.setCreator(creator);
                blackListCallService.addblackNum(blackListCall);
            }
            return ResponseUtil.ok();
        }
        return ResponseUtil.fail("请上传excel模板数据");
    }

    /**
     * 下载模板
     * @param request
     * @return
     */
//    @PreAuthorize("hasAuthority('blacklist_delete')")
    @PostMapping("download")
    public String download(HttpServletRequest request) {
        return request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/"+"黑名单号码示例.xlsx";
    }

    /**
     * @param file
     * @return
     */
    public static List<String> judgeFile(MultipartFile file) {
        List<String> blackNumList = new ArrayList<>();
        List<Object> list = EasyExcelUtil.readExcel(file,BlackListCall.class);
        for(Object object : list){
            BlackListCall blackListCall = (BlackListCall) object;
            blackNumList.add(blackListCall.getBlackNum());
        }
        return blackNumList;
    }
}
