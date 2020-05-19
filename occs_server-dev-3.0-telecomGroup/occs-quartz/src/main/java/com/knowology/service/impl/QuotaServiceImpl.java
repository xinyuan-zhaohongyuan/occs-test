package com.knowology.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.knowology.dao.JobMapper;
import com.knowology.dao.QuotaMapper;
import com.knowology.dto.JobCountCallNum;
import com.knowology.model.Quota;
import com.knowology.request.QuotaJob;
import com.knowology.service.QuotaService;
import com.knowology.service.RedisService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * <p></p>
 *
 * @author : Conan
 * @date : 2019-12-10 14:57
 **/
@Transactional(rollbackFor = Exception.class)
@Service
public class QuotaServiceImpl implements QuotaService {

    private final String JOB_ONE = "营业厅服务满意率";
//    private final String JOB_TWO = "政企交付满意度";
//    private final String JOB_THREE = "智慧家庭交付满意率";
    /**
     * 配额key的前缀
     */
    private static final String QUOTA_KEY_PREFIX = "task";
    @Resource
    private QuotaMapper quotaMapper;
    @Autowired
    private RedisService redisService;

    @Resource
    private JobMapper jobMapper;
    @Override
    public int addQuota(QuotaJob quotaJob) {
        ///每次编辑配额后，都先删除配额，再增加新的配额
        deleteQuota(quotaJob.getJobName());
        Quota quota = new Quota();
        quota.setQuotaKey(QUOTA_KEY_PREFIX+quotaJob.getJobName().hashCode());
        quota.setJobName(quotaJob.getJobName());
        JSONArray quotaArray = quotaJob.getQuota();
        quotaTotal(quotaJob.getJobName(),quotaArray);
        quota.setQuota(quotaArray.toJSONString().trim());
        final int num = quotaMapper.insert(quota);
        redisService.opsForSetAdd(quota.getQuotaKey(),quotaJob.getQuota().toArray());
        return num;
    }

    @Override
    public boolean deleteQuota(String jobName) {
        if (StringUtils.isBlank(jobName)) {
            return false;
        }
        Example example = new Example(Quota.class);
        example.createCriteria().andEqualTo("quotaKey",QUOTA_KEY_PREFIX+jobName.hashCode());
        quotaMapper.deleteByExample(example);
        redisService.delete(QUOTA_KEY_PREFIX+jobName.hashCode());
        return true;
    }

    @Override
    public String opsForRedisQuotaPop(String jobName) {
        return  (String)redisService.opsForSetPop(QUOTA_KEY_PREFIX + jobName.hashCode());
    }

    @Override
    public long opsForRedisQuotaAdd(String jobName, Object... values) {
        return redisService.opsForSetAdd(QUOTA_KEY_PREFIX+jobName.hashCode(),values);
    }

    @Override
    public JSONArray quotaDetails(String jobName) {
        Quota quota = quotaMapper.selectQuotaByJobName(jobName);
        if(quota == null){
            return null;
        }
        String quotaContent = quota.getQuota();
        JSONArray array = JSONArray.parseArray(quotaContent);
        if(!jobName.equals(JOB_ONE)){

            array.addAll(getTotal(array));
        }
        return array;
    }

    /**
     * 计算一下数量
     * @param array
     * @return
     */
    private static JSONArray getTotal(JSONArray array){
        JSONArray result = new JSONArray();
        Map<String,Integer> map = new HashMap<>();
        for (int i = 0; i < array.size(); i++) {
            String value = array.getString(i);
            String[] arr = value.split(":");
            Integer num = Integer.parseInt(arr[3]);
            System.out.println(arr[0] + ": " + arr[1]);
            if(map.containsKey(arr[0] + "总数")){
                map.put(arr[0] + "总数",map.get(arr[0] + "总数")+num);
            }else{
                map.put(arr[0] + "总数",num);
            }
            if(map.containsKey(arr[0]+":"+arr[2] + "总数")){
                map.put(arr[0]+":"+arr[2] + "总数",map.get(arr[0]+":"+arr[2] + "总数")+num);
            }else{
                map.put(arr[0]+":"+arr[2] + "总数",num);
            }
            if(map.containsKey(arr[0]+":"+arr[1] + "总数")){
                map.put(arr[0]+":"+arr[1] + "总数",map.get(arr[0]+":"+arr[1] + "总数")+num);
            }else{
                map.put(arr[0]+":"+arr[1] + "总数",num);
            }
        }
        for(Map.Entry<String,Integer> entry:map.entrySet()){
            result.add(entry.getKey() + ":" + entry.getValue());
        }
       return result;
    }

    /**
     * 计算一下配合总数并更新相应任务里的总数
     * @param jobName
     * @param array
     */
    private void quotaTotal(String jobName,JSONArray array){
        Integer total = 0;
        Integer num = 0;
        for(int i=0;i<array.size();i++){
            String value = array.getString(i);
            String[] arr = value.split(":");
            if(jobName.equals(JOB_ONE)){
                num = Integer.parseInt(arr[1]);

            }else{
                num = Integer.parseInt(arr[3]);
            }
            total += num;
        }
        JobCountCallNum jobCountCallNum = new JobCountCallNum();
        jobCountCallNum.setJobName(jobName);
        jobCountCallNum.setTotal(total);
        jobMapper.updateByCondition(jobCountCallNum);
    }

	@Override
	public Set<Object> opsForRedisQuotaGet(String jobName) {
		return redisService.opsForSetMembers(QUOTA_KEY_PREFIX + jobName.hashCode());
	}

	@Override
	public void deleteValue(String jobName ,Object... values) {
		redisService.deleteValue(QUOTA_KEY_PREFIX+jobName.hashCode(), values);
	}
}
