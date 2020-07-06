package com.zhangbao.eblog.common.lang;

import java.util.HashMap;
import java.util.Map;

public class ResultData extends HashMap<String,Object> {
    private static final long serialVersionUID = 1L;

    public ResultData() {
        put("status", 0);
        put("msg", "操作成功");
    }


    public static ResultData error() {
        return error(1, "操作失败");
    }

    public static ResultData error(String msg) {
        return error(500, msg);
    }

    public static ResultData error(int status, String msg) {
        ResultData resultData = new ResultData();
        resultData.put("status", status);
        resultData.put("msg", msg);
        return resultData;
    }

    public static ResultData ok(String msg) {
        ResultData resultData = new ResultData();
        resultData.put("msg", msg);
        return resultData;
    }

    public static ResultData ok(Map<String, Object> map) {
        ResultData resultData = new ResultData();
        resultData.putAll(map);
        return resultData;
    }
    public static ResultData ok(Object list,Object count) {
        ResultData resultData = new ResultData();
        resultData.put("data", list);
        resultData.put("count", count);
        return resultData;
    }
    public static ResultData ok(String key,Object value) {
        ResultData resultData = new ResultData();
        resultData.put(key, value);
        return resultData;
    }
    public static ResultData ok(int status, String msg,String data) {
        ResultData resultData = new ResultData();
        resultData.put("status", status);
        resultData.put("msg", msg);
        resultData.put("data", data);
        return resultData;
    }
    public static ResultData ok(int status, String data) {
        ResultData resultData = new ResultData();
        resultData.put("status", status);
        resultData.put("data", data);
        return resultData;
    }

    public static ResultData ok() {
        return new ResultData();
    }


    public static ResultData ok(Object object) {
        ResultData resultData = new ResultData();
        resultData.put("status", "0");
        resultData.put("msg", "数据获取成功");
        resultData.put("data", object);
        return resultData;
    }

    public ResultData action(String action){
        return this.put("action",action);
    }
    @Override
    public ResultData put(String key, Object value) {
        super.put(key, value);
        return this;
    }

}
