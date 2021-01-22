package com.lyf.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class TestController {

    Logger logger = LoggerFactory.getLogger(TestController.class);

    @PostMapping("testParam")
    @ResponseBody
    JSONObject testParam(@RequestBody JSONObject jsonObject){
        System.out.println("打印数据"+jsonObject);
        logger.info("记录数据"+jsonObject);
        return jsonObject;
    }


    @PostMapping("testHttp")
    @ResponseBody
    JSONArray testHttp(HttpServletRequest request){

        JSONArray jsonArray = new JSONArray();
        System.out.println(request.getContentType());

        request.getAttribute("name");
        jsonArray.add(request.getAttribute("name"));
        jsonArray.add(request.getAttribute("data"));

        System.out.println("param:"+request.getParameter("command"));
        System.out.println("attribute:"+request.getAttribute("command"));

        return jsonArray;
    }


    @PostMapping("testRecordData")
    @ResponseBody
    JSONArray testRecordData(@RequestBody JSONObject jsonObject){
        JSONArray jsonArray = new JSONArray();

        System.out.println("接受到的数据为:"+jsonObject);
        jsonObject.put("ReceiveData",jsonObject);
        return jsonArray;
    }


    @PostMapping(value = "testPost",produces = "application/json")
    @ResponseBody
    public String testPost(@RequestBody JSONObject jsonObject){
        String userId = jsonObject.getString("userId");
        String password = jsonObject.getString("password");
        System.out.println(userId+";"+password);
        return "ok";
    }

    @RequestMapping("/testParam")
    @ResponseBody
    public String testParam(){
        System.out.println("yes");
        return "OK";
    }


}
