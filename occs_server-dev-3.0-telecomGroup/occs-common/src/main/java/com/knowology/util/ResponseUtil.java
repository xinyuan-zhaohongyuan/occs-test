package com.knowology.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 响应操作结果
 *  {
 *      status： 返回码，
 *      msg：返回消息，
 *      data：  响应数据
 *  }
 */
public class ResponseUtil {
    /**
     * 响应报文头 json + utf-8
     */
    public static final String JSON_UTF8 = "application/json;charset=utf-8";

    public static Object ok() {
        Map<String, Object> obj = new HashMap<String, Object>();
        obj.put("status", 0);
        obj.put("message", "成功");
        return obj;
    }
    public static Object ok(String msg) {
        Map<String, Object> obj = new HashMap<String, Object>();
        obj.put("status", 0);
        obj.put("message", msg);
        return obj;
    }

    public static Object ok(Object data) {
        Map<String, Object> obj = new HashMap<String, Object>();
        obj.put("status", 0);
        obj.put("message", "成功");
        obj.put("data", data);
        return obj;
    }

    public static Object fail() {
        Map<String, Object> obj = new HashMap<String, Object>();
        obj.put("status", -1);
        obj.put("message", "错误");
        return obj;
    }

    public static Object fail(String message) {
        Map<String, Object> obj = new HashMap<String, Object>();
        obj.put("status", -1);
        obj.put("message", message);
        return obj;
    }

    /**
     * 未登陆或权限不足导致访问失败
     * @param errmsg
     * @return
     */
    public static Object failAuthorization( String errmsg) {
        Map<String, Object> obj = new HashMap<String, Object>();
        obj.put("status", 403);
        obj.put("message", errmsg);
        return obj;
    }

}

