package com.knowology.dao;

import com.knowology.config.MyMapper;
import com.knowology.model.VoiceRecognize;
import com.knowology.request.VoiceQuery;

import java.util.List;

/**
 * 语音标注
 */
public interface VoiceRecognizeMapper extends MyMapper<VoiceRecognize> {

    /**
     * 语音识别列表
     * @param voiceQuery
     * @return
     */
    List<VoiceRecognize> recognizeList(VoiceQuery voiceQuery);

    /**
     * 符合条件的jobId集合
     * @param voiceQuery
     * @return
     */
    List<VoiceRecognize> selectJobIds(VoiceQuery voiceQuery);

    /**
     * 标注列表
     * @return
     */
    List<VoiceRecognize> labelList(VoiceQuery voiceQuery);
}