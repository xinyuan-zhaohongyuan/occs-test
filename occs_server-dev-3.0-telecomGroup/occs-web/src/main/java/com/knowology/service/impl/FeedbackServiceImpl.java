package com.knowology.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.knowology.dao.FeedbackMapper;
import com.knowology.model.Feedback;
import com.knowology.request.FeedbackQuery;
import com.knowology.service.FeedbackService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p></p>
 *
 * @author : Conan
 * @date : 2019-08-15 19:01
 **/
@Service
public class FeedbackServiceImpl implements FeedbackService {
    @Resource
    private FeedbackMapper feedbackMapper;

    @Override
    public Integer insertAll(List<Feedback> list) {
        return list != null? feedbackMapper.insertList(list) : 0;
    }

    @Override
    public List<String> search(FeedbackQuery feedbackQuery) {
        Example example = new Example(Feedback.class);
        example.selectProperties("node")
                .createCriteria()
                .andEqualTo("jobName",feedbackQuery.getJobName())
                .andEqualTo("scene",feedbackQuery.getScene())
                .andBetween("jobDate",feedbackQuery.getStartDate(),feedbackQuery.getEndDate());
        final List<Feedback> feedbacks = feedbackMapper.selectByExample(example);
        return feedbacks.stream().map(Feedback::getNode).filter(StringUtils::isNotBlank).collect(Collectors.toList());
    }

    @Override
    public List<String> listNodeByScene(String scene) {
        FeedbackQuery feedbackQuery = new FeedbackQuery();
        feedbackQuery.setScene(scene);
        List<String> nodeJsonList = search(feedbackQuery);
        final List<String> keys = nodeJsonList.stream()
                .map(JSONObject::parseObject)
                .flatMap(m -> m.keySet().stream())
                .distinct()
                .collect(Collectors.toList());
        return keys;
    }

    @Override
    public Map<String,Long> load(FeedbackQuery feedbackQuery) {
        List<String> nodeJsonList = search(feedbackQuery);
//        节点的所有tag
        final List<JSONObject> tags =
                nodeJsonList.stream()
                        .map(s -> filterAndReduceNode(s,feedbackQuery.getNode()))
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList());
        Set<String> allKeys =
                tags.stream()
                        .map(JSONObject::keySet)
                        .flatMap(Set::stream)
                        .collect(Collectors.toSet());
        Map<String,Long> result = new HashMap<>(allKeys.size());
        for (String key : allKeys) {
            for (JSONObject tag : tags) {
                result.put(key, Optional.ofNullable(result.get(key)).orElse(0L) + Optional.ofNullable((Long) tag.get(key)).orElse(0L));
            }
        }
        return result;
    }

    private JSONObject filterAndReduceNode(String jsonObjectString,String node) {
        JSONObject jsonObject = JSONObject.parseObject(jsonObjectString);
        return null == jsonObject.get(node)? null: (JSONObject) jsonObject.get(node);
    }

}
