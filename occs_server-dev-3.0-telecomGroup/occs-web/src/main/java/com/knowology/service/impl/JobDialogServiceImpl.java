package com.knowology.service.impl;

import com.knowology.dao.JobDialogMapper;
import com.knowology.model.JobDialog;
import com.knowology.service.JobDialogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author xullent
 * @date 2020/2/18 14:49
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class JobDialogServiceImpl implements JobDialogService {

    @Resource
    private JobDialogMapper jobDialogMapper;

    @Override
    public List<JobDialog> selectJobContentByJobName(String jobName) {
        return jobDialogMapper.selectJobContentByJobName(jobName);
    }
}
