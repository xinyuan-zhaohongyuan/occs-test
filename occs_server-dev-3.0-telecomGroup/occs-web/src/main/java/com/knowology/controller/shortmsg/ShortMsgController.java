package com.knowology.controller.shortmsg;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.github.pagehelper.PageInfo;
import com.knowology.async.service.impl.ShortMsgAsyncService;
import com.knowology.config.annotation.FullName;
import com.knowology.model.*;
import com.knowology.service.JobService;
import com.knowology.service.ShortMsgService;
import com.knowology.util.DBDataUtil;
import com.knowology.util.ResponseUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 *短信模板/短信任务相关
 * @author xullent
 */
@RestController
@RequestMapping("shortMsg")
public class ShortMsgController {

    @Autowired
    private ShortMsgService shortMsgService;
    @Autowired
    private ShortMsgAsyncService shortMsgAsyncService;
    @Autowired
    private JobService jobService;

    @SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(ShortMsgController.class);

    /**
     * 短信模板列表数据
     * @param pageNum
     * @param pageSize
     * @return
     */
    @PostMapping("list")
    public Object list(@RequestParam(defaultValue = "1") int pageNum,
                       @RequestParam(defaultValue = "10") int pageSize){
        PageInfo<ShortMsgModel> info = shortMsgService.list(pageNum,pageSize);
        Map<String, Object> map = new HashMap<>(2);
        map.put("total", info.getPages());
        map.put("items", info.getList());
        return ResponseUtil.ok(map);
    }

    /**
     * 删除短信模板
     * @param id
     * @return
     */
    @PostMapping("delete")
    public Object delete(@RequestParam(value = "id", required = true) Long id) {
        int deleteMsg = shortMsgService.delete(id);
        if(deleteMsg <= 0){
            return ResponseUtil.fail("短信模板不存在");
        }
        return ResponseUtil.ok();
    }

    /**
     * 新增短信模板
     * @param shortMsgModel
     * @return
     */
    @PostMapping("add")
    public Object addShortMsg(@FullName String memberName, ShortMsgModel shortMsgModel){
        if(shortMsgModel == null){
            return  ResponseUtil.fail("参数不能为空");
        }
        shortMsgModel.setCreateTime(new Date());
        shortMsgModel.setCreator(memberName);
        int addShortMsg = shortMsgService.addShortMsg(shortMsgModel);
        if(addShortMsg <= 0){
            return  ResponseUtil.fail("添加失败");
        }
        return ResponseUtil.ok();
    }

    /**
     * 创建短信任务，即开始发送短信
     * @param memberName
     * @param entity
     * @return
     */
    @PostMapping("send")
    public Object send(@FullName String memberName, ShortMsgSend entity) {
        if (null == entity.getTelenumNameList()) {
            //号码组名称都为空
            return ResponseUtil.fail("号码组不能为空");
        }
        if ( entity.getTelenumNameList().length ==0 ||  entity.getTelenumNameList().length > 6) {
            //号码组名称都为空
            return ResponseUtil.fail("号码组个数为0或者大于6");
        }
        if (StringUtils.isBlank(entity.getContent())) {
            return ResponseUtil.fail("短信内容不能为空");
        }

//        List<PassiveNumDetail> passiveNumDetails = new ArrayList<>();
//        List<String> telenumNames = DBDataUtil.arrayListTransformArray(entity.getTelenumNameList());
//        if(telenumNames != null && telenumNames.size() > 0){
//            for(int i=0;i<telenumNames.size();i++){
//                List<PassiveNumDetail> passiveNumDetailsList = passiveNumService.selectByTelenumName(telenumNames.get(i));
//                passiveNumDetails.addAll(passiveNumDetailsList);
//            }
//        }
//        if ( null == passiveNumDetails || passiveNumDetails.size() <= 0) {
//            return ResponseUtil.fail("未找到需要发送短信的号码");
//        }
//        final int i = shortMsgService.insertShortMsg(entity,memberName);
        //异步发送短信
//        shortMsgAsyncService.sendMsg(passiveNumDetails,i,entity.getContent());
        //异步发送短信
        shortMsgAsyncService.sendMsg(memberName,entity);
        return ResponseUtil.ok();
    }

    /**
     * 分页查询发送短信任务
     * @param pageNum
     * @param pageSize
     * @return
     */
    @PostMapping("sendList")
    public Object sendList( @RequestParam(value = "pageNum",required = false,defaultValue = "1")Integer pageNum,
                        @RequestParam(value = "pageSize",required = false,defaultValue = "10") Integer pageSize) {
        PageInfo<ShortMsgSend> list = shortMsgService.select(pageNum,pageSize);
        Map<String, Object> map = new HashMap<>();
        map.put("total", list.getTotal());
        map.put("items", list.getList());
        return ResponseUtil.ok(map);
    }

    /**
     * 短信发送详情
     * @param pageNum
     * @param pageSize
     * @param id
     * @return
     */
    @PostMapping("sendListDetail")
    public Object sendListDetail(@RequestParam(value = "pageNum",required = false,defaultValue = "1")Integer pageNum,
                             @RequestParam(value = "pageSize",required = false,defaultValue = "10") Integer pageSize,
                             @RequestParam(value = "id",required = true)Long id) {
        PageInfo<ShortMsgDetail> list = shortMsgService.selectDetail(pageNum, pageSize,id);
        Map<String, Object> map = new HashMap<>();
        map.put("total", list.getTotal());
        map.put("items", list.getList());
        return ResponseUtil.ok(map);
    }

    /**
     * 加载被叫号码组列表
     * @return
     */
    @PostMapping("listPassiveNum")
    public Object listPassiveNum() {
        List<PassiveNum> passiveNums = jobService.listPassiveNum();
        return passiveNums;
    }

    /**
     * 加载被叫号码组列表
     * @return
     */
    @PostMapping("exportShortMsg")
    public Object exportShortMsg(HttpServletResponse response,Long id) throws Exception {
        ExcelTypeEnum type= ExcelTypeEnum.XLSX;
        response.setContentType("multipart/form-data");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename=" + "短信详情表" );
        ServletOutputStream out = response.getOutputStream();
        ExcelWriter writer = new ExcelWriter(out, type, true);
        List<ShortMsgDetail> shortMsgDetails = shortMsgService.selectShortMsgDetailById(id);
        DBDataUtil.desensitizationShortMsgDetail(shortMsgDetails);
        Sheet sheet1=new Sheet(1,0,ShortMsgDetail.class);
        sheet1.setSheetName("短信表");

        writer.write(shortMsgDetails,sheet1);
        writer.finish();
        // 关闭流
        out.close();
        return null;
    }
}
