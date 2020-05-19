package com.knowology.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>Redis工具类</p>
 *
 * @author : Conan
 * @date : 2019-08-06 15:25
 **/
@Service
public class RedisService {
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    public Boolean delete(String key) {
        return redisTemplate.delete(key);
    }
    
    public Long deleteValue(String key,Object[] values) {
        return redisTemplate.opsForSet().remove(key, values);
    }
    
    public void opsForValueSet(String key, Object value) {
        redisTemplate.opsForValue().set(key,value);
    }

    public Object opsForValueGet(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void opsForHashPut(String key,String hashKey,Object value) {
        redisTemplate.opsForHash().put(key,hashKey,value);
    }

    public Object opsForHashGet(String key,String hashKey) {
        return redisTemplate.opsForHash().get(key, hashKey);
    }

    public void opsForHashPutAll(String key, Map<Object,Object> map) {
        redisTemplate.opsForHash().putAll(key,map);
    }

    public Long opsForSetAdd(String key,Object... values) {
        return redisTemplate.opsForSet().add(key, values);
    }

    public Object opsForSetPop(String key) {
        return redisTemplate.opsForSet().pop(key);
    }

    public List<Object> opsForSetPop(String key,long count) {
        return redisTemplate.opsForSet().pop(key,count);
    }

    public Set<Object> opsForSetMembers(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    public Long opsForListLeftPush(String key,Object value) {
        return redisTemplate.opsForList().leftPush(key, value);
    }

    public Object opsForListLeftPop(String key) {
        return redisTemplate.opsForList().leftPop(key);
    }

    public Long opsForListRightPush(String key,Object value) {
        return redisTemplate.opsForList().rightPush(key, value);
    }

    public Object opsForListRightPop(String key) {
        return redisTemplate.opsForList().rightPop(key);
    }

    public Long opsForListLeftPushAll(String key , Collection<Object> collection) {
        return redisTemplate.opsForList().leftPushAll(key,collection);
    }

    public List<Object> opsForListLeftRange(String key,long start, long end) {
        return redisTemplate.opsForList().range(key,start,end);
    }

    public List<Object> opsForListLeftRangeAll(String key) {
        return redisTemplate.opsForList().range(key,0,-1);
    }
}
