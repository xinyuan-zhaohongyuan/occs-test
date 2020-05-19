package com.knowology.consumer;

import java.util.Date;
import java.util.Set;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.knowology.async.service.impl.JobDialogAsyncService;
import com.knowology.async.service.impl.VoiceRecognizeAsyncService;
import com.knowology.dao.JobDetailMapper;
import com.knowology.dao.JobDetailTaskMapper;
import com.knowology.dao.JobMapper;
import com.knowology.entity.ActiveMQContaint;
import com.knowology.excelVo.BussinessHallResult;
import com.knowology.model.JobDetail;
import com.knowology.model.JobDetailTask;
import com.knowology.service.BussinessDataService;
import com.knowology.service.QuotaService;

import tk.mybatis.mapper.entity.Example;

/**
 * @author : Conan
 * @Description 外呼结果反馈
 * @date : 2019-05-03 20:14
 **/
@Component
public class OutCallQueueListener {
	private static final Logger logger = LoggerFactory.getLogger(OutCallQueueListener.class);

	@Resource
	private JobDetailMapper jobDetailMapper;
	@Resource
	private JobDetailTaskMapper jobDetailTaskMapper;
	@Resource
	private JobMapper jobMapper;
	@Resource
	private VoiceRecognizeAsyncService voiceRecognizeAsyncService;
	@Resource
	private JobDialogAsyncService jobDialogAsyncService;
	@Autowired
	private QuotaService quotaService;
	@Autowired
	private BussinessDataService bussinessDataService;
	private final String JOB_THREE = "营业厅服务满意率";

	/**
	 * 针对消息进行业务处理
	 * 
	 * @param message
	 * @param session
	 */
	@JmsListener(destination = ActiveMQContaint.QUEUE_CALLRESULT, containerFactory = "jmsListenerContainerFactory")
	public void deal(TextMessage message, Session session) {
		String msg = null;
		try {
			msg = message.getText();
			message.acknowledge();
		} catch (JMSException e) {
			logger.error("接收外呼结果失败:", e);
			try {
				session.recover();
			} catch (JMSException e1) {
				logger.error("session recover失败:", e);
			}
			return;
		}
		logger.info("外呼结果消息内容：" + msg);
		JSONObject data = null;
		try {
			data = JSON.parseObject(msg);
		} catch (JSONException e) {
			logger.error("外呼结果消息格式有误:" + msg);
		}
		if (data != null) {
			// 回执通话内容语音持久化
			voiceRecognizeAsyncService.execute(data);
			// 通话内容持久化
			jobDialogAsyncService.execute(data);
			final String scene = data.getString("SCENE");
			final String uuid = data.getString("UUID");
			final String callStatus = data.getString("CALL_STATUS");
			if (StringUtils.isAnyBlank(uuid, scene)) {
				logger.error("外呼消息缺少UUID或者SCENE" + data);
				return;
			}

			JobDetail jobDetail = JSON.parseObject(msg, JobDetail.class);
			JobDetailTask jobDetailTask = JSON.parseObject(msg, JobDetailTask.class);

			jobDetail.setUpdateTime(new Date());
			Example example = new Example(JobDetail.class);
			example.createCriteria().andEqualTo("id", uuid);
			jobDetailMapper.updateByExampleSelective(jobDetail, example);

			// 备份表的更新
			jobDetailTask.setUpdateTime(new Date());
			jobDetailTask.setId(Integer.parseInt(uuid));
			Example exampleTask = new Example(JobDetail.class);
			exampleTask.createCriteria().andEqualTo("id", uuid);
			jobDetailTaskMapper.updateByExampleSelective(jobDetailTask, exampleTask);
			// 成功的就删除掉
			if ("成功".equals(callStatus.trim())) {
				jobDetailTaskMapper.deleteTask(Integer.parseInt(uuid));
			}
			JobDetail jobDetail12 = jobDetailMapper.selectRecordresult(Integer.parseInt(uuid));
			jobMapper.updateByCondition(jobDetailMapper.countCallNum(jobDetail12.getJobName()));
			//将intentfind持久化
			JobDetail jobDetail1 = jobDetailMapper.selectByPrimaryKey(uuid);
			if (JOB_THREE.equals(jobDetail1.getJobName())) {
				String intentfind = data.getString("intentfind");
				BussinessHallResult bussinessHallResult = jobDetailMapper.selectJobDetailAndBussinessData(uuid);
				bussinessDataService.updateIntentfind(bussinessHallResult.getId(),intentfind);
				// 更新配额
				if ("成功".equals(callStatus.trim())) {
					String province = bussinessHallResult.getProvince();
					Set<Object> set = quotaService.opsForRedisQuotaGet(JOB_THREE);
					for (Object quota : set) {
						String[] arr = quota.toString().split(":");
						if(province.equals(arr[0])){
							int num = Integer.valueOf(arr[1]);
							int newNum = num-1;
							quotaService.deleteValue(JOB_THREE, quota.toString());
							quotaService.opsForRedisQuotaAdd(JOB_THREE,arr[0]+":"+(newNum<0?0:newNum));
						}
					} 
				}
			}
		}
	}
}
