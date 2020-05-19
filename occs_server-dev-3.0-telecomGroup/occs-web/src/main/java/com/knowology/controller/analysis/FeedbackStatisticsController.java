package com.knowology.controller.analysis;

import com.knowology.request.FeedbackQuery;
import com.knowology.service.FeedbackService;
import com.knowology.service.JobService;
import com.knowology.service.SceneService;
import com.knowology.util.ResponseUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * <p>反馈结果统计-通用版内容</p>
 *
 * @author : Conan
 * @date : 2019-08-16 10:48
 **/
@RestController
@RequestMapping("feedback")
public class FeedbackStatisticsController {

    @Autowired
    private FeedbackService feedbackService;
    @Autowired
    private SceneService sceneService;
    @Autowired
    private JobService jobService;

    @PostMapping("load")
    public Object load(FeedbackQuery feedbackQuery) {
        if (StringUtils.isAnyBlank(feedbackQuery.getScene(),feedbackQuery.getNode())) {
            return ResponseUtil.ok();
        }
        Map<String,Long> feedbackMap = feedbackService.load(feedbackQuery);
        return ResponseUtil.ok(feedbackMap);
    }

    @PostMapping("listScene")
    public Object listScene() {
        final List<String> scenes = sceneService.listScene();
        return ResponseUtil.ok(scenes);
    }

    @PostMapping("listNode")
    public Object listNode(String scene) {
        final List<String> nodes = feedbackService.listNodeByScene(scene);
        return ResponseUtil.ok(nodes);
    }

    @PostMapping("listJob")
    public Object listJob(String scene) {
        final List<String> jobs = jobService.listJobByScene(scene);
        return ResponseUtil.ok(jobs);
    }
}
