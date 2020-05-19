package com.knowology.service;

import com.github.pagehelper.PageInfo;
import com.knowology.model.VoiceRecognize;
import com.knowology.request.VoiceQuery;

import java.util.List;
import java.util.Map;

/**
 * 语音识别列表/标注
 *
 * @author xullent
 */
public interface VoiceRecognizeService{

    /**
     * 语音识别列表
     * @return
     */
    Map<String,List<VoiceRecognize>>  recognizeList(VoiceQuery voiceQuery);


    long countRecognizeList(VoiceQuery voiceQuery);

    /**
     * 语音标注列表分页
     * @return
     */
    PageInfo<VoiceRecognize> labelList(VoiceQuery voiceQuery);

    /**
     * 语音识别列表分页
     * @return
     */
    List<VoiceRecognize> selectLabelList(VoiceQuery voiceQuery);

    /**
     * 语音标注/修改
     * @param voiceRecognize
     * @return
     */
    int update(VoiceRecognize voiceRecognize);

    /**
     * 通过id查询
     * @param id
     * @return
     */
    VoiceRecognize selectById(Integer id);
}
