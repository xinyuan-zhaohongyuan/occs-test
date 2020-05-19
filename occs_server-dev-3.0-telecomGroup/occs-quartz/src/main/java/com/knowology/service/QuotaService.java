package com.knowology.service;

import java.util.Set;

import com.alibaba.fastjson.JSONArray;
import com.knowology.request.QuotaJob;

public interface QuotaService {
    /**
     * 添加配额
     * @return
     */
    int addQuota(QuotaJob quotaJob);

    /**
     * 删除配额
     * @param jobName
     * @return
     */
    boolean deleteQuota(String jobName);
    
    /**
     * 删除指定值
     * @param key
     * @param values
     */
    void deleteValue(String jobName ,Object... values);

    /**
     * redis中的配额pop
     * @param jobName
     * @return
     */
    String opsForRedisQuotaPop(String jobName);
    
    /**
     * redis中的配额get
     * @param jobName
     * @return
     */
    Set<Object> opsForRedisQuotaGet(String jobName);
    
    /**
     * redis中的配额add
     * @param jobName
     * @param values
     * @return
     */
    long opsForRedisQuotaAdd(String jobName, Object... values);

    /**
     * 数据库里存的配额数量
     * @param jobName
     * @return
     */
    JSONArray quotaDetails(String jobName);
}
