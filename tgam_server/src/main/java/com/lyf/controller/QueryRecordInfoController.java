package com.lyf.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lyf.controller.vomain.DataInfoVO;
import com.lyf.service.QueryRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 * @Author 李云飞
 * @Date 2020-12-29
 * @Version tgam 1.0
 * function:查询数据信息和数据
 *
 */



@RestController
@RequestMapping("record")
public class QueryRecordInfoController {

    @Autowired
    QueryRecordService queryRecordService;

    @RequestMapping(value = "/queryDataInfo",produces = "application/json")
    @CrossOrigin
    public JSONObject queryDataInfo(@RequestBody JSONObject jsonObject){

        JSONObject jsonObject1 = new JSONObject();

        String userName = jsonObject.getString("username");

        String date = jsonObject.getString("date");

        String dataInfo = userName+"."+date;

        List<DataInfoVO> list = queryRecordService.getDataInfo(dataInfo);

        List<Map> list1 = new ArrayList<>();
        for(DataInfoVO dataInfoVO :list){
            Map<String,String> detailInfo = new HashMap<>();
            detailInfo.put("beginDate",dataInfoVO.getStartTime().toString().substring(0,16));//beginTime更好？
            detailInfo.put("endDate",dataInfoVO.getEndTime().toString().substring(0,16));
            detailInfo.put("pattern","驾驶");

            list1.add(detailInfo);
        }

        jsonObject1.put("info",list1);
        return jsonObject1;
    }



}
