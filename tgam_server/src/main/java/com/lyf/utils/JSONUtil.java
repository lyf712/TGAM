package com.lyf.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class JSONUtil {


    public static void main(String[]args){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name","李云飞");
        jsonObject.put("data",JSONObject.parse("[1,2,3,1,2,3,2,3]"));
        jsonObject.put("data2", JSONArray.parse(""));

        JSONArray jsonArray = new JSONArray();

    }



}

