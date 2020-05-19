package com.knowology.controller.task;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.github.pagehelper.PageInfo;
import com.knowology.config.annotation.FullName;
import com.knowology.dto.JobDetailExportExcel;
import com.knowology.model.Job;
import com.knowology.model.JobDetail;
import com.knowology.model.JobDialog;
import com.knowology.model.PassiveNum;
import com.knowology.request.JobDetailQuery;
import com.knowology.request.JobQuery;
import com.knowology.request.QuotaJob;
import com.knowology.service.JobDialogService;
import com.knowology.service.JobService;
import com.knowology.service.QuotaService;
import com.knowology.service.SceneService;
import com.knowology.service.ShortMsgService;
import com.knowology.service.StrategyService;
import com.knowology.service.TimeStrategyService;
import com.knowology.util.DBDataUtil;
import com.knowology.util.ResponseUtil;
import com.knowology.valid.AddCheck;
import com.knowology.valid.DeleteCheck;
import com.knowology.valid.SearchCheck;

/**
 * @author : Conan
 * @Description 外呼任务
 * @date : 2019-04-17 14:58
 **/

@RestController
@RequestMapping("job")
public class JobController {
	private static final Logger logger = LoggerFactory.getLogger(JobController.class);

    @Value("${task.jobdetail.num}")
    private Integer detailNum;
    @Autowired
    private JobService jobService;
    @Autowired
    private SceneService sceneService;

    @Autowired
    private StrategyService strategyService;
    @Autowired
    private TimeStrategyService timeStrategyService;
    @Autowired
    private ShortMsgService shortMsgService;
    @Autowired
    private QuotaService quotaService;
    @Autowired
    private JobDialogService jobDialogService;


    /**
     * 任务列表
     * @param jobQuery
     * @return
     */
    @PostMapping("list")
    public Object list(@Validated(SearchCheck.class) JobQuery jobQuery) {
        PageInfo<Job> list = jobService.list(jobQuery);
        Map<String, Object> map = new HashMap<>(4);
        map.put("total", list.getTotal());
        map.put("items", list.getList());
        return ResponseUtil.ok(map);
    }
    /**
     * 任务详情列表
     * @return
     */
    @PostMapping("listDetails")
    public Object listDetails(@Validated(SearchCheck.class) JobDetailQuery jobDetailQuery) {
        Job job = jobService.getJobByName(jobDetailQuery.getJobName());
        if (job == null) {
            return ResponseUtil.fail("该任务名称不存在");
        }
        PageInfo<JobDetail> listDetails = jobService.listDetails(jobDetailQuery.getPageNum(), jobDetailQuery.getPageSize(), jobDetailQuery.getJobName());
        Map<String, Object> map = new HashMap<>(5);
        map.put("head", job);
        map.put("total", listDetails.getTotal());
        map.put("items", listDetails.getList());
        return ResponseUtil.ok(map);
    }

    /**
     * 查询任务详情列表
     * @param jobDetailQuery
     * @return
     */
    @PostMapping("searchDetails")
    public Object searchDetails(@Validated(SearchCheck.class) JobDetailQuery jobDetailQuery) {
        PageInfo<JobDetail> searchDetails = jobService.searchDetails(jobDetailQuery);
        Map<String, Object> map = new HashMap<>(4);
        map.put("total", searchDetails.getTotal());
        map.put("items", searchDetails.getList());
        return ResponseUtil.ok(map);
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @PostMapping("add")
    public Object addJob(@FullName String memberName,@Validated(AddCheck.class) JobQuery jobQuery) {
        if (StringUtils.isBlank(jobQuery.getJobName())) {
            return ResponseUtil.fail("任务名称不能为空");
        }
        if(jobQuery.getPassivenumNameList() == null || jobQuery.getPassivenumNameList().length ==0 || jobQuery.getPassivenumNameList().length >6){
            return ResponseUtil.fail("号码组个数为0或者大于6");
        }
        //判断任务名是否存在相同记录
        Integer integer = jobService.countByTaskName(jobQuery.getJobName());
        if (integer != null && !integer.equals(0)) {
            return ResponseUtil.fail("任务名称已存在");
        }
        //添加创建人
        jobQuery.setCreator(memberName);
        jobService.add(jobQuery);
        return ResponseUtil.ok();
    }

    @PostMapping("delete")
    public Object delete(@Validated(DeleteCheck.class) JobQuery jobQuery) {
        jobService.delete(jobQuery);
        return ResponseUtil.ok();
    }

    /**
     * 暂停任务
     *
     * @param job
     * @return
     */
    @PostMapping("pauseJob")
    public Object pauseJob(@FullName String memberName,Job job) {
        jobService.pauseJob(memberName,job);
        return ResponseUtil.ok();
    }

    /**
     * 启动任务
     *
     * @param job
     * @return
     */
    @PostMapping("resumeJob")
    public Object resumeJob(@FullName String memberName,Job job) {
        jobService.resumeJob(memberName,job);
        return ResponseUtil.ok();
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
     * 加载场景
     * @return
     */
    @PostMapping("listScene")
    public Object listScene() {
        List<String> scenes = sceneService.listScene();
        return ResponseUtil.ok(scenes);
    }

    /**
     * 加载外呼策略
     * @return
     */
    @PostMapping("listStrategy")
    public Object listStrategy(){
        List<String> strategyDetails = strategyService.getStrategyList();
        strategyDetails.add(0,"不重呼");
        return ResponseUtil.ok(strategyDetails);
    }

    /**
     * 加载时间策略
     * @return
     */
    @PostMapping("listTimeStrategy")
    public Object listTimeStrategy(){
        List<String> strategyDetails = timeStrategyService.getStrategyList();
        return ResponseUtil.ok(strategyDetails);
    }

    /**
     * 外呼ID对应的通话ID
     *
     * @return
     */
    @PostMapping("/content")
    public Object talkContent(Integer id) {
        if (null == id || id == 0) {
            return ResponseUtil.ok(null);
        }
        JobDetail jobDetail = jobService.selectRecordresult(id);
        if (null == jobDetail || null == jobDetail.getContent()) {
            return ResponseUtil.ok();
        }
        JSONArray jsonArray = null;
        //通话内容为空抛出catch异常进行返回
        try{
        	jsonArray = JSON.parseArray(jobDetail.getContent());
        }catch(JSONException e){
        	logger.info("通话内容为："+jobDetail.getContent());
        	return ResponseUtil.ok("通话内容为空");
        }
        return ResponseUtil.ok(jsonArray);
    }

    /**
     * 外呼ID对应的通话URL流
     *
     * @return
     */
    @GetMapping("/audio/{id}")
    public Object getAudio(@PathVariable("id") Integer id, HttpServletRequest request, HttpServletResponse response) throws IOException {
        JobDetail jobDetail = jobService.selectRecordresult(id);
        if (null == jobDetail) {
            return ResponseUtil.fail();
        }
        String url = jobDetail.getUrl();
        if (StringUtils.isBlank(url)) {
            return ResponseUtil.fail("未找到对应音频");
        }

        File audio = new File(url);

        if (!audio.exists()) {
            return ResponseUtil.fail("音频不存在");
        }
        OutputStream os = response.getOutputStream();
        FileInputStream fis = new FileInputStream(audio);
        String range = request.getHeader("Range");
        if (StringUtils.isNotBlank(range)) {
            String[] rs = range.split("\\=");
            range = rs[1].split("\\-")[0];
            long length = audio.length();
            int irange = Integer.parseInt(range);
            length = length - irange;
            response.addHeader("Accept-Ranges", "bytes");
            response.addHeader("Content-Length", length + "");
            response.addHeader("Content-Range", "bytes " + range + "-" + length + "/" + length);
            response.addHeader("Content-Type", "audio/wav;charset=UTF-8");
        }
        int len;
        byte[] b = new byte[1024];
        while ((len = fis.read(b)) != -1) {
            os.write(b, 0, len);
        }
        fis.close();
        os.close();
        return null;
    }

    /**
     * 加载短信模板
     * @return
     */
    @PostMapping("listShortMsgModel")
    public Object listShortMsgModel(){
        List<String> shortMsgNameList = shortMsgService.shortMsgNameList();
        return ResponseUtil.ok(shortMsgNameList);
    }

    /**
     * 将录音内容导出到txt文件中
     * @param id
     * @param response
     * @return
     */
    @GetMapping("/audio/exportTxt")
    public Object exportTxtTxt(Integer id ,HttpServletResponse response) {
        JobDetail jobDetail = jobService.selectRecordresult(id);
        if (null == jobDetail || null == jobDetail.getContent()) {
            return ResponseUtil.fail("没有文本内容");
        }
        JSONArray jsonArray = JSON.parseArray(jobDetail.getContent());

        List<String> phoneList = jsonArray.toJavaList(String.class);
        // 导出文件
        response.setContentType("text/plain");
        String fileName = "通话记录"+id;
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ".txt");
        BufferedOutputStream buff = null;
        StringBuffer write = new StringBuffer();
        String enter = "\r\n";
        ServletOutputStream outSTr = null;
        try {
            outSTr = response.getOutputStream();
            buff = new BufferedOutputStream(outSTr);
            // 把内容写入文件
            if (phoneList.size() > 0) {
                for (int i = 0; i < phoneList.size(); i++) {
                    write.append(phoneList.get(i));
                    write.append(enter);
                }
            }
            buff.write(write.toString().getBytes("UTF-8"));
            buff.flush();
            buff.close();
        } catch (Exception e) {
        	logger.error("exportTxtTxt读取IO流异常：" ,e.getMessage());
        } finally {
            try {
                buff.close();
                outSTr.close();
            } catch (Exception e) {
            	logger.error("exportTxtTxt关闭IO流异常：" ,e.getMessage());
            }
        }
        return  ResponseUtil.ok();
    }

    /**
     * 导出录音文件
     * @param id
     * @param response
     * @return
     * @throws IOException
     */
    @GetMapping("/audio/exportAudio")
    public Object exportAudio(Integer id ,HttpServletResponse response) throws IOException {
        JobDetail jobDetail = jobService.selectRecordresult(id);
        if (null == jobDetail) {
            return ResponseUtil.fail("没有录音文件");
        }
        String url = jobDetail.getUrl();
        response.reset();
        String fname =  id + ".wav";
        response.setHeader("content-disposition", "attachment;filename=" + fname);
        response.addHeader("Content-Type", "audio/wav;charset=UTF-8");
        BufferedOutputStream bos = null;
        BufferedInputStream bis = null;
        try {
            bis = new BufferedInputStream(new FileInputStream(url));
            bos = new BufferedOutputStream(response.getOutputStream());

            byte[] buff = new byte[2048];
            int bytesRead;
            while( (bytesRead = bis.read(buff)) > 0) {
                bos.write(buff,0,bytesRead);
                buff = new byte[2048];
            }
        } catch(final IOException e) {
        	logger.error("exportAudio读取IO流异常：" ,e.getMessage());
        } catch(Exception e) {
        	logger.error("exportAudio异常：" ,e.getMessage());
        }finally {
            if (bis != null){
                bis.close();
            }
            if (bos != null)
            {
                bos.flush();
                bos.close();
                bos=null;
            }
        }
        response.flushBuffer();
        return null;
    }
    /**
     * 导出数据
     * @param response
     * @return
     * @throws Exception
     */
    @PostMapping("export")
    public Object exportExcel(HttpServletResponse response, JobDetailQuery jobDetailQuery) throws Exception {
        ExcelTypeEnum type=ExcelTypeEnum.XLSX;
        response.setContentType("multipart/form-data");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename=" + "任务详情表" + type.getValue());
        ServletOutputStream out = response.getOutputStream();
        ExcelWriter writer = new ExcelWriter(out, type, true);
        List<JobDetail> jobDetails = jobService.searchExportDetails(jobDetailQuery);
        //电话号码脱敏
        DBDataUtil.desensitizationJobdetail(jobDetails);
        Sheet sheet1=new Sheet(1,0,JobDetailExportExcel.class);
        sheet1.setSheetName("任务表");

        List<JobDialog> jobDialogList = jobDialogService.selectJobContentByJobName(jobDetailQuery.getJobName());

        Sheet sheet2=new Sheet(2,0,JobDialog.class);
        sheet2.setSheetName("对话文本");

        writer.write(jobDetails,sheet1);
        writer.write(jobDialogList,sheet2);
        writer.finish();
        // 关闭流
        out.close();
        return null;
    }

    /**
     * 根据id进行任务复制
     * @param id
     * @return
     */
    @PostMapping("jobDetail")
    public Object jobDetail(Integer id){
        if(id == null){
            return ResponseUtil.fail("任务id为空");
        }
        Job job = jobService.getJobById(id);
        return ResponseUtil.ok(job);
    }

    /**
     * 修改任务信息--支持未执行和执行中的任务
     * @param memberName
     * @param jobQuery
     * @return
     */
    @PostMapping("updateJob")
    public Object updateJob(@FullName String memberName,JobQuery jobQuery){
        if(jobQuery== null || jobQuery.getId() == null){
            return ResponseUtil.fail("任务id为空");
        }
        jobQuery.setUpdateUser(memberName);
        jobService.update(jobQuery);
        return ResponseUtil.ok();
    }

    @PostMapping("restartJob")
    public Object restartJob(@FullName String memberName,String jobName){
        if(StringUtils.isBlank(jobName)){
            return ResponseUtil.fail("任务名称不能为空");
        }
        Job job = jobService.getJobByName(jobName);
        if(!"已完成".equals(job.getStatus())){
            return ResponseUtil.fail("只能对已完成的任务发起重呼操作");
        }
        jobService.restartJob(memberName,job);
        return ResponseUtil.ok();
    }

    /**
     * 集团智能回访任务配额功能
     * 2019-12-06
     * @returnB
     */
    @PostMapping("quota")
    public Object quotaForJob(@Validated @RequestBody QuotaJob quotaJob) {
        final int i = quotaService.addQuota(quotaJob);
        if (i <= 0) {
            return ResponseUtil.fail("配额失败");
        }
        return ResponseUtil.ok();
    }

    /**
     * 根据任务名称查看配额详细信息
     * @param jobName
     * @return
     */
    @PostMapping("quotaDetails")
    public Object quotaDetails( String jobName){
        if(jobName == null || "".equals(jobName)){
            return ResponseUtil.fail("任务名称不能为空");
        }
        return ResponseUtil.ok(quotaService.quotaDetails(jobName));
    }
}
