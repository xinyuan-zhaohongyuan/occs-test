package com.knowology.service;

import com.knowology.model.Feedback;
import com.knowology.request.FeedbackQuery;

import java.util.List;
import java.util.Map;

public interface FeedbackService {

    /**
     * 批量插入
     * @param list
     * @return
     */
    Integer insertAll(List<Feedback> list);

    /**
     * 查询数据
     * @param feedbackQuery
     * @return
     */
    List<String> search(FeedbackQuery feedbackQuery);

    /**
     * 查询场景的所有节点
     * @param scene
     * @return
     */
    List<String> listNodeByScene(String scene);

    /**
     * 页面显示标签统计数据
     * @param feedbackQuery
     * @return key标签value数量
     */
    Map<String, Long> load(FeedbackQuery feedbackQuery);

}
