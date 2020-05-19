package com.knowology.controller.tele;

import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageInfo;
import com.knowology.async.service.impl.PassNumberAsyncService;
import com.knowology.config.annotation.FullName;
import com.knowology.model.PassiveNum;
import com.knowology.model.PassiveNumDetail;
import com.knowology.service.PassiveNumService;
import com.knowology.util.EasyExcelUtil;
import com.knowology.util.ResponseUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
* @ClassName TelePassiveNumController
* @Description 被叫号码管理
* @author 反正不是Conan
* @Date 2019/4/16 17:38
* @version 1.0.0
*/

@RestController
@RequestMapping("passive")
public class PassiveNumController {

    private static final Logger logger = LoggerFactory.getLogger(PassiveNumController.class);
    @Autowired
    private PassiveNumService passiveNumService;
    @Autowired
    private PassNumberAsyncService passNumberAsyncService;

    /**
     * 分页查询被叫号码信息
     * @param telenumGroupName
     * @param pageNum
     * @param pageSize
     * @return
     */
    @PostMapping("select")
    public Object select(@RequestParam(value = "telenumGroupName",required = false)String telenumGroupName,
                         @RequestParam(value = "pageNum",required = false,defaultValue = "1")Integer pageNum,
                         @RequestParam(value = "pageSize",required = false,defaultValue = "10") Integer pageSize) {
        PageInfo<PassiveNum> list = passiveNumService.select(pageNum, pageSize,telenumGroupName);
        Map<String, Object> map = new HashMap<>(4);
        map.put("total", list.getTotal());
        map.put("items", list.getList());
        return ResponseUtil.ok(map);
    }

    /**
     * 逐条输入
     * @return
     */
    @PreAuthorize("hasAuthority('numberGroup_add')")
    @PostMapping("eachAdd")
    public Object eachAdd(@FullName String creator,
                          @RequestParam(value = "telenumGroupName")String telenumGroupName,
                          @RequestParam(value = "description",required = false)String description,
                          @RequestParam(value = "passiveNumDetail") String passiveNumDetail) {
        int flag = passiveNumService.selectPassiveNumByName(telenumGroupName);
        if (flag > 0) {
            return ResponseUtil.fail("号码组名称已经存在");
        }
        if (StringUtils.isBlank(passiveNumDetail)) {
            return ResponseUtil.fail("请输入号码");
        }
        List<PassiveNumDetail> passiveNumDetailsArrayList = new ArrayList<>();
        JSONArray jsonArray = JSONArray.parseArray(passiveNumDetail);
        //phoneNum为key用来去重
        Map<String,String> pmap = new HashMap<String,String>();
        for (int i =0; i<jsonArray.size(); i++) {
            PassiveNumDetail detail = new PassiveNumDetail();
            String phoneNum = jsonArray.getJSONObject(i).getString("phoneNum");
            if(phoneNum == null || "".equals(phoneNum)){
                continue;
            }
            //phoneNum是否重复
            if(pmap.containsKey(phoneNum)){
            	ResponseUtil.fail(phoneNum+"号码重复");
            }
            pmap.put(phoneNum, null);
            detail.setPhoneNum(phoneNum);
            detail.setTelenumGroupName(telenumGroupName);
            passiveNumDetailsArrayList.add(detail);
        }
        PassiveNum passiveNum = new PassiveNum();
        passiveNum.setUpdateTime(new Date());
        passiveNum.setTelenumGroupName(telenumGroupName);
        passiveNum.setCreateTime(new Date());
        passiveNum.setDescription(description);
        passiveNum.setCreator(creator);
        passiveNumService.addPassiveNum(passiveNum,passiveNumDetailsArrayList);
        return ResponseUtil.ok();
    }

    @PreAuthorize("hasAuthority('numberGroup_add')")
    @PostMapping("fileAdd")
    public Object fileAdd(@FullName String creator,
                          @RequestParam(value = "telenumGroupName")String telenumGroupName ,
                          @RequestParam(value = "description",required = false)String description,
                          MultipartFile file) {
        if (file == null) {
            return ResponseUtil.fail("请选择文件");
        }
        if (file.getOriginalFilename().endsWith("xlsx") || file.getOriginalFilename().endsWith("xls")) {
            PassiveNum passiveNum = new PassiveNum();
            passiveNum.setUpdateTime(new Date());
            passiveNum.setTelenumGroupName(telenumGroupName);
            passiveNum.setCreateTime(new Date());
            passiveNum.setDescription(description);
            passiveNum.setCreator(creator);
            List<PassiveNumDetail> passiveNumDetailsArrayList = judgeFile(file);
            passiveNumService.addPassiveNum(passiveNum,passiveNumDetailsArrayList);
            return ResponseUtil.ok();
        }
        return ResponseUtil.fail("请上传excel模板数据");
    }

    /**
     * 添加号码组
     * @param telenumGroupName
     * @param description
     * @param passiveNumDetail
     * @param file
     * @return
     */
    @PreAuthorize("hasAuthority('numberGroup_add')")
    @PostMapping("addPassiveNum")
    public Object addPassiveNumInfo(@FullName String memberName,
                                    @RequestParam(value = "telenumGroupName",required = true)String telenumGroupName ,
                                    @RequestParam(value = "description",required = false)String description,
                                    @RequestParam(value = "method",required = true) String method,
                                    @RequestParam(value = "passiveNumDetail",required = false) String passiveNumDetail,
                                    MultipartFile file) {
        PassiveNum passiveNum = new PassiveNum();
        passiveNum.setUpdateTime(new Date());
        passiveNum.setTelenumGroupName(telenumGroupName);
        passiveNum.setCreateTime(new Date());
        passiveNum.setDescription(description);
        passiveNum.setCreator(memberName);
        int flag = passiveNumService.selectPassiveNumByName(telenumGroupName);
        if (flag > 0) {
            return ResponseUtil.fail("号码组名称已经存在");
        }
        if (passiveNumDetail == null && file == null) {
            return ResponseUtil.fail("请添加号码");
        }
        if (method != null && "文件导入".equals(method)) {
            if (file == null) {
                return ResponseUtil.fail("没选择文件");
            }
            if (file.getOriginalFilename().endsWith("xlsx") || file.getOriginalFilename().endsWith("xls")) {
//                List<PassiveNumDetail> passiveNumDetailsArrayList = judgeFile(file);
            	//异步执行excel上传号码组入库，这里还是会阻塞必须当前和异步线程都执行完才会返回
                passNumberAsyncService.excute(file,passiveNum);
                return ResponseUtil.ok();
            }
            return ResponseUtil.fail("请上传excel模板数据");
        }
        List<PassiveNumDetail> passiveNumDetailsArrayList = new ArrayList<>();
        JSONArray jsonArray = JSONArray.parseArray(passiveNumDetail);
        //phoneNum为key用来去重
        Map<String,String> pmap = new HashMap<String,String>();
        for (int i =0; i<jsonArray.size(); i++) {
            PassiveNumDetail detail = new PassiveNumDetail();
            String phoneNum = jsonArray.getJSONObject(i).getString("phoneNum");
            logger.info("phoneNum : " + phoneNum);
            if(phoneNum == null || "".equals(phoneNum.trim())){
                continue;
            }
            //phoneNum是否重复
            if(pmap.containsKey(phoneNum)){
            	ResponseUtil.fail(phoneNum+"号码重复");
            }
            pmap.put(phoneNum, null);
            detail.setPhoneNum(phoneNum);
            passiveNumDetailsArrayList.add(detail);
        }
        passiveNumService.addPassiveNum(passiveNum,passiveNumDetailsArrayList);
        return ResponseUtil.ok();
    }

    /**
     * 逐条输入形式向号码组中追加号码
     * @param telenumGroupName
     * @param passiveNumDetail
     * @return
     */
    @PreAuthorize("hasAuthority('numberGroup_update')")
    @PostMapping("eachUpdate")
    public Object eachUpdate(@RequestParam("telenumGroupName") String telenumGroupName,
                             @RequestParam("passiveNumDetail") String passiveNumDetail) {
        if (telenumGroupName == null) {
            return ResponseUtil.fail("号码组名称不能为空");
        }
        if (passiveNumDetail == null) {
            return ResponseUtil.fail("请添加号码");
        }
        List<PassiveNumDetail> passiveNumDetailsArrayList = new ArrayList<>();
        JSONArray jsonArray = JSONArray.parseArray(passiveNumDetail);
        //phoneNum为key用来去重
        Map<String,String> pmap = new HashMap<String,String>();
        for (int i =0; i<jsonArray.size(); i++) {
            PassiveNumDetail detail = new PassiveNumDetail();
             String phoneNum = jsonArray.getJSONObject(i).getString("phoneNum");
            if(phoneNum == null || "".equals(phoneNum)){
                continue;
            }
            //phoneNum是否重复
            if(pmap.containsKey(phoneNum)){
            	ResponseUtil.fail(phoneNum+"号码重复");
            }
            pmap.put(phoneNum, null);
            detail.setPhoneNum(phoneNum);
            detail.setTelenumGroupName(telenumGroupName);
            passiveNumDetailsArrayList.add(detail);
        }
        passiveNumService.addPassiveNumDetail(telenumGroupName,passiveNumDetailsArrayList);
        return ResponseUtil.ok();
    }

    /**
     * 文件形式向号码组中追加号码
     * @param telenumGroupName
     * @param file
     * @return
     */
    @PreAuthorize("hasAuthority('numberGroup_update')")
    @PostMapping("fileUpdate")
    public Object fileUpdate(@RequestParam("telenumGroupName") String telenumGroupName,
                             MultipartFile file) {
        if (telenumGroupName == null) {
            return ResponseUtil.fail("号码组名称不能为空");
        }
        if (file.getOriginalFilename().endsWith("xlsx") || file.getOriginalFilename().endsWith("xls")) {
            List<PassiveNumDetail> passiveNumDetailsArrayList = judgeFile(file);
            //phoneNum为key用来去重
            Map<String,String> pmap = new HashMap<String,String>();
            for(PassiveNumDetail passiveNumDetail : passiveNumDetailsArrayList){
            	String phoneNum = passiveNumDetail.getPhoneNum();
            	if(pmap.containsKey(phoneNum)){
                	ResponseUtil.fail(phoneNum+"号码重复");
                }
                pmap.put(phoneNum, null);
            }
            passiveNumDetailsArrayList.forEach(passiveNumDetail -> passiveNumDetail.setTelenumGroupName(telenumGroupName));
            passiveNumService.addPassiveNumDetail(telenumGroupName,passiveNumDetailsArrayList);
            return ResponseUtil.ok();
        }
        return ResponseUtil.fail("请上传excel模板数据");
    }

    /**
     * @param file
     * @return
     */
    public static List<PassiveNumDetail> judgeFile(MultipartFile file) {
        List<PassiveNumDetail> passiveNumDetailsArrayList = new ArrayList<>();
            List<Object> list = EasyExcelUtil.readExcel(file,PassiveNumDetail.class);
            for(Object object : list){
                passiveNumDetailsArrayList.add((PassiveNumDetail) object);
            }
        return passiveNumDetailsArrayList;
    }

    /**
     * 查询号码组的详细信息
     * @param id
     * @return
     */
    @PostMapping("listDetail")
    public Object listDetail(@RequestParam(value = "id",required = true) Integer id ,
                             @RequestParam(value = "passiveNum",required = false)String passiveNum,
                                      @RequestParam(value = "pageNum",required = false,defaultValue = "1")Integer pageNum,
                                      @RequestParam(value = "pageSize",required = false,defaultValue = "10")Integer pageSize){

        PageInfo<PassiveNumDetail> info = passiveNumService.listDetail(id,pageSize,pageNum,passiveNum);
        PassiveNum passiveNums = passiveNumService.selectTeleNumGroupInfo(id);
        Map<String,Object> retMap = new HashMap<>(4);
        //数据放头部
        retMap.put("head", passiveNums);
        retMap.put("total", info.getTotal());
        retMap.put("items", info.getList());
        return ResponseUtil.ok(retMap);
    }

    @PostMapping("download")
    public String download(HttpServletRequest request) {
        final String scheme = request.getScheme();
        final String serverName = request.getServerName();
        final int port = request.getServerPort();
        return scheme+"://"+serverName+":"+port+"/"+"号码组示例.xlsx";
    }

    /**
     * 删除详情号码的信息
     * @param id
     * @return
     */
    @PostMapping("deleteDetail")
    public Object deleteDetail(@RequestParam(value = "id", required = true) Integer id) {
        if (id == null) {
            return ResponseUtil.fail("参数不能为空");
        }
        int deleteMsg = passiveNumService.deleteDetail(id);
        if (deleteMsg <= 0) {
            return ResponseUtil.fail("详情号码不存在");
        }
        return ResponseUtil.ok();
    }

    @PreAuthorize("hasAuthority('numberGroup_delete')")
    @PostMapping("deletePassiveNum")
    public Object deletePassiveNum(@RequestParam(value = "id",required = true)Integer id) {
        if (id == null) {
            return ResponseUtil.fail("参数不能为空");
        }
        passiveNumService.deletePassiveNum(id);
        return ResponseUtil.ok();
    }

}
