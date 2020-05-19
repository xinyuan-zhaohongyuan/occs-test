package com.knowology.controller.voice;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.github.pagehelper.PageInfo;
import com.knowology.config.annotation.FullName;
import com.knowology.model.VoiceRecognize;
import com.knowology.request.VoiceQuery;
import com.knowology.service.VoiceRecognizeService;
import com.knowology.util.ResponseUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

/**
 * 语音识别列表/标注
 * @author xullent
 */
@RestController
@RequestMapping("voice")
public class VoiceRecognizeController {
    private static final Logger logger = LoggerFactory.getLogger(VoiceRecognizeController.class);
    @Autowired
    private VoiceRecognizeService voiceRecognizeService;

    /**
     * 语音识别列表
     * @return
     */
    @PostMapping("recognizeList")
    public Object recognizeList(VoiceQuery voiceQuery){
        logger.info("voiceQuery : " + voiceQuery.toString());
        Map<String,Object> map = new HashMap<>();
        map.put("total", voiceRecognizeService.countRecognizeList(voiceQuery));
        Map<String,List<VoiceRecognize>> list = voiceRecognizeService.recognizeList(voiceQuery);

        map.put("items", list);
        return ResponseUtil.ok(map);
    }

    /**
     * 对语音进行标注/修改
     * @return
     */
    @PostMapping("label")
    public Object label(@FullName String creator, VoiceRecognize voiceRecognize){
        if(voiceRecognize == null ){
            ResponseUtil.fail("参数不能为空!");
        }
        if(voiceRecognize.getId() == null || voiceRecognize.getId() == 0 || "".equals(voiceRecognize.getId())){
            ResponseUtil.fail("id不能为空!");
        }
        voiceRecognize.setLabelPersion(creator);
        voiceRecognize.setLabelTime(new Date());
        voiceRecognize.setLabelStatus("已标注");
        voiceRecognizeService.update(voiceRecognize);
        return ResponseUtil.ok();
    }

    /**
     * 语音标注列表
     * @return
     */
    @PostMapping("labelList")
    public Object labelList(VoiceQuery voiceQuery){
        PageInfo<VoiceRecognize> list = voiceRecognizeService.labelList(voiceQuery);
        Map<String,Object> map = new HashMap<>();
        map.put("total", list.getTotal());
        map.put("items", list.getList());
        return ResponseUtil.ok(map);
    }

    /**
     * ID对应的通话URL流
     *
     * @return
     */
//    @GetMapping("/audio/{id}")
//    public Object getAudio(Integer id, HttpServletRequest request, HttpServletResponse response) throws IOException {
//        VoiceRecognize voiceRecognize = voiceRecognizeService.selectById(id);
//        if (null == voiceRecognize) {
//            return ResponseUtil.fail();
//        }
//        String url = voiceRecognize.getUrl();
//        if (StringUtils.isBlank(url)) {
//            return ResponseUtil.fail("未找到对应音频");
//        }
//
//        File audio = new File(url);
//        if (!audio.exists()) {
//            return ResponseUtil.fail("音频不存在");
//        }
//        //总时长(秒)
//        long t1 = WavVoiceUtil.getTimeLen(audio);
//        System.out.println("录音总长度： " + t1);
//        long start = Long.parseLong(voiceRecognize.getBeginTime());
//        System.out.println("开始时间： " + start);
//        long end = Long.parseLong(voiceRecognize.getEndTime());
//        System.out.println("借宿时间： " + end);
//        if(start<0 || end<=0 || start>=t1 || end>t1 || start>=end){
//            return ResponseUtil.fail("音频起始截止时间错误");
//        }
//        //音频数据大小（44为128kbps比特率wav文件头长度）
//        long wavSize = audio.length()-44;
//        System.out.println("音频数据大小： " + wavSize);
//        //截取的音频数据大小
//        long splitSize = (wavSize/t1)*(end-start);
//        OutputStream os = response.getOutputStream();
//        FileInputStream fis = new FileInputStream(audio);
//        //截取时跳过的音频数据大小
//        long skipSize = (wavSize/t1)*start;
//        int splitSizeInt = Integer.parseInt(String.valueOf(splitSize));
//        int skipSizeInt = Integer.parseInt(String.valueOf(skipSize));
//
//        //存放文件大小,4代表一个int占用字节数
//        ByteBuffer buf1 = ByteBuffer.allocate(4);
//        //放入文件长度信息
//        buf1.putInt(splitSizeInt+36);
//        //代表文件长度
//        byte[] flen = buf1.array();
//        //存放音频数据大小，4代表一个int占用字节数
//        ByteBuffer buf2 = ByteBuffer.allocate(4);
//        //放入数据长度信息
//        buf2.putInt(splitSizeInt);
//        //代表数据长度
//        byte[] dlen = buf2.array();
//        //数组反转
//        flen = WavVoiceUtil.reverse(flen);
//        dlen = WavVoiceUtil.reverse(dlen);
//        //定义wav头部信息数组
//        byte[] head = new byte[44];
//        //读取源wav文件头部信息
//        fis.read(head, 0, head.length);
//        //4代表一个int占用字节数
//        for(int i=0; i<4; i++){
//            //替换原头部信息里的文件长度
//            head[i+4] = flen[i];
//            //替换原头部信息里的数据长度
//            head[i+40] = dlen[i];
//        }
//        //存放截取的音频数据
//        byte[] fbyte = new byte[splitSizeInt+head.length];
//        //放入修改后的头部信息
//        for(int i=0; i<head.length; i++){
//            fbyte[i] = head[i];
//        }
//        //存放截取时跳过的音频数据
//        byte[] skipBytes = new byte[skipSizeInt];
//        //跳过不需要截取的数据
//        fis.read(skipBytes, 0, skipBytes.length);
//        //读取要截取的数据到目标数组
//        fis.read(fbyte, head.length, fbyte.length-head.length);
//        fis.close();
//
//        os.write(fbyte);
//        os.flush();
//        os.close();
//        return ResponseUtil.ok();
//    }
    @GetMapping("/audio/{id}")
    public Object getAudio(@PathVariable("id") Integer id, HttpServletRequest request, HttpServletResponse response) throws IOException {
        VoiceRecognize voiceRecognize = voiceRecognizeService.selectById(id);
        if (null == voiceRecognize) {
            return ResponseUtil.fail();
        }
        String url = voiceRecognize.getUrl();
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
     * 导出标注数据
     * @param response
     * @return
     * @throws Exception
     */
    @PostMapping("export")
    public Object exportExcel(HttpServletResponse response, VoiceQuery voiceQuery) throws Exception {
        ExcelTypeEnum type=ExcelTypeEnum.XLSX;
        response.setContentType("multipart/form-data");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename=" + "任务详情表" + type.getValue());
        ServletOutputStream out = response.getOutputStream();
        ExcelWriter writer = new ExcelWriter(out, type, true);
        List<VoiceRecognize> list = voiceRecognizeService.selectLabelList(voiceQuery);
        Sheet sheet1=new Sheet(1,0,VoiceRecognize.class);
        sheet1.setSheetName("任务表");
        writer.write(list,sheet1);
        writer.finish();
        // 关闭流
        out.close();
        return ResponseUtil.ok();
    }
}
