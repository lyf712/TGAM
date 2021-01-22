package com.lyf.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lyf.dao.domain.RecordData;
import com.lyf.dao.domain.RecordInfo;
import com.lyf.dao.domain.User;
import com.lyf.service.UserManageService;
import com.lyf.service.impl.RecordDataServiceImpl;
import com.lyf.task.DynamicTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.*;

/**
 * @Author lyf
 * @Date 2020-12-15
 *
 */


@Controller
public class RecordDataController {

    @Autowired
    RecordDataServiceImpl recordDataService;
    @Autowired
    UserManageService userManageService;
    @Autowired
    StringRedisTemplate redisTemplate;
    @Autowired
    DynamicTask dynamicTask;


    private String  key ;// 存储的键，多个用户呢》？？

    @RequestMapping("test")
    @ResponseBody
    @CrossOrigin
    public String insertData(String param){ // 用开始时间去存？？

        Date date = new Date(System.currentTimeMillis());

        redisTemplate.opsForValue().set("res",date+param);

        redisTemplate.expire("res", Duration.ofSeconds(6));

        recordDataService.test();

        return "yes";
    }

    /**
     * 请求过程
     * version1.0 默认间隔30s
     * 一个用户进行每30s进行post发数据过来存入缓存，当超过1min未发数据便将缓存存入数据库
     * 数据格式:
     * date
     * dataInfo（数据简介） []
     * data(数据包) []
     *
     * 前端数据：
     * 开始发送 date(start_time) + 数据包 +userId(token)
     * >>存入缓存,如何设置主键,使用list类型？？
     * 来一个存入list一个
     * .... 数据包
     * 结束发送：date(end_time) + 数据包 +userId(token)
     * >>判断 若end_time则从redis进行写入数据库
     *
     *
     * 测试1
     * 每个三十秒进行发送数据请求，将其放入Redis的list
     * @return
     */
    @RequestMapping("addData")
    @ResponseBody
    @CrossOrigin
     // redis的命名规范
    // https://www.cnblogs.com/joshua317/p/11995197.html
    // 返回信息：code ,message;
    public JSONArray addData(@RequestBody JSONObject jsonObject){ //flag:标志位 // List<Float>


        String flag = jsonObject.getString("flag");
        String date = jsonObject.getString("date");
        String userName = jsonObject.getString("username");// userId => username
        String data = jsonObject.getString("data");


        System.out.println("控制层接受数据:"+flag+" "+date+" "+userName+" "+data);

        JSONArray jsonArray = new JSONArray();
        Map<String,String> res = new HashMap<>();

       User user =  userManageService.getUserByUserName(userName);



        //进行传入的有效性判断
        if(flag==null||user==null){
            res.put("code","0");
            res.put("msg","无效参数传入");
        }else {
            boolean flags = recordDataService.addData(flag,date,userName,data);

            if(flags){
                res.put("code","1");
                res.put("msg","记录成功");
            }else{
                res.put("code","0");
                res.put("msg","服务异常");
            }
        }

        jsonArray.add(res);

        System.out.println("结束本次");
        return jsonArray;
    }


    @PostMapping("/getData")
    @ResponseBody
    @CrossOrigin
    public JSONArray getData(@RequestBody JSONObject jsonObject){//String userId,String date,Integer index,String deviceType

        String userId = jsonObject.getString("userId");
        String date = jsonObject.getString("date");
        String index = jsonObject.getString("index");

        System.out.println("接受的数据为"+userId+";"+date+";"+index);

        JSONArray jsonArray = new JSONArray();

        //.substring(1,userId.length()-1)  .substring(1,date.length()-1)
        String infoId = userId+"."+date+"."+index;

        //如果infoId 不符合标准则拦截

        Map<String,List> map = new HashMap<>();

        System.out.println("控制层InfoID："+infoId);

        List<RecordData> dataList = recordDataService.getRecordData(infoId);
        List<RecordInfo> infoList = recordDataService.getRecordInfo(infoId);

        map.put("dataInfo",infoList);
        map.put("data",dataList);
        jsonArray.add(map);

        return jsonArray;
    }
    // getQuery

}
