package com.knowology.util;

import com.alibaba.fastjson.JSONObject;

/**
 * @description:
 * @author: Conan
 * @create: 2019-02-28 16:42
 **/
public class JSONUtil {
	/***
	* @Description: 封装key value JSON对象
	* @Param:
	* @return: com.alibaba.fastjson.JSONObject
	* @Author: Conan
	* @Date: 2019/2/28
	*/
	public static JSONObject okJson(String key,Object value){
		JSONObject obj = new JSONObject();
		obj.put("key",key);
		obj.put("value",value);
		return obj;
	}
}
