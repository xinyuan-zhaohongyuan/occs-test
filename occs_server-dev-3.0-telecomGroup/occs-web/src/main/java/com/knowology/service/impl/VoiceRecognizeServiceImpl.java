package com.knowology.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.knowology.dao.VoiceRecognizeMapper;
import com.knowology.model.VoiceRecognize;
import com.knowology.request.VoiceQuery;
import com.knowology.service.VoiceRecognizeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Transactional(rollbackFor = Exception.class)
@Service
public class VoiceRecognizeServiceImpl implements VoiceRecognizeService {

    @Resource
    private VoiceRecognizeMapper voiceRecognizeMapper;
    @Override
    public Map<String,List<VoiceRecognize>> recognizeList(VoiceQuery voiceQuery) {
        PageHelper.startPage(voiceQuery.getPageNum(),voiceQuery.getPageSize());
        List<VoiceRecognize> list = voiceRecognizeMapper.selectJobIds(voiceQuery);
        return transformRecognizeList(list,voiceQuery);
    }

    @Override
    public long countRecognizeList(VoiceQuery voiceQuery) {
        PageHelper.startPage(voiceQuery.getPageNum(),voiceQuery.getPageSize());
        List<VoiceRecognize> list = voiceRecognizeMapper.selectJobIds(voiceQuery);
        PageInfo<VoiceRecognize> info = new PageInfo<>(list);

        return info.getTotal();
    }

    @Override
    public PageInfo<VoiceRecognize> labelList(VoiceQuery voiceQuery) {
        PageHelper.startPage(voiceQuery.getPageNum(),voiceQuery.getPageSize());
        List<VoiceRecognize> list = voiceRecognizeMapper.labelList(voiceQuery);
        PageInfo<VoiceRecognize> info = new PageInfo<>(list);
        return info;
    }

    @Override
    public List<VoiceRecognize> selectLabelList(VoiceQuery voiceQuery) {
        return voiceRecognizeMapper.labelList(voiceQuery);
    }

    @Override
    public int update(VoiceRecognize voiceRecognize) {
//        Example example = new Example(VoiceRecognize.class);
        return voiceRecognizeMapper.updateByPrimaryKeySelective(voiceRecognize);
    }

    @Override
    public VoiceRecognize selectById(Integer id) {
        return voiceRecognizeMapper.selectByPrimaryKey(id);
    }

    /**
     * 先对jobId做好分页再进行下一层
     * @param list
     * @param voiceQuery
     * @return
     */
    private Map<String,List<VoiceRecognize>> transformRecognizeList(List<VoiceRecognize> list,VoiceQuery voiceQuery){
        Map<String,List<VoiceRecognize>> map=new TreeMap<String, List<VoiceRecognize>>();
        for(VoiceRecognize voiceRecognize : list ){
            voiceQuery.setJobId(voiceRecognize.getJobId());
            List<VoiceRecognize> voiceRecognizeList = voiceRecognizeMapper.recognizeList(voiceQuery);
            if(voiceRecognizeList != null && voiceRecognizeList.size() != 0){
                map.put(voiceRecognize.getJobId().toString(),voiceRecognizeList);
            }

        }
        return map;
    }
}
