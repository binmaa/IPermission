package com.ipermission.common;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class JsonData {
    private boolean ret;
    private String msg;
    private Object data;
    public JsonData(boolean ret){
        this.ret = ret;
    }
    public static JsonData success(String msg, Object data){
        JsonData jsonDate = new JsonData(true);
        jsonDate.data = data;
        jsonDate.msg = msg;
        return jsonDate;
    }
    public static JsonData success(){
        JsonData jsonDate = new JsonData(true);
        return jsonDate;
    }
    public static JsonData success(Object data){
        JsonData jsonDate = new JsonData(true);
        jsonDate.data = data;
        return jsonDate;
    }
    public static JsonData success(String msg){
        JsonData jsonDate = new JsonData(true);
        jsonDate.msg = msg;
        return jsonDate;
    }
    public static JsonData fail(String msg){
        JsonData jsonDate = new JsonData(false);
        jsonDate.msg = msg;
        return jsonDate;
    }
    public Map<String, Object> toMap(){
        Map<String, Object> res = new HashMap<>();
        res.put("ret",ret);
        res.put("msg",msg);
        res.put("data",data);
        return res;
    }

}
