package com.lyf.controller;

import com.alibaba.fastjson.JSONObject;
import com.lyf.controller.vomain.DataInfoVO;
import com.lyf.controller.vomain.DataVo;
import com.lyf.service.QueryRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/***
 * @Author 李云飞
 * @Date 2020-12-29
 * @Version tgam 1.0
 * function:查询数据信息和数据
 *
 */



@RestController
@RequestMapping("record")
public class QueryRecordDataController {


    @Autowired
    QueryRecordService queryRecordService;


    @RequestMapping(value = "/queryData",produces = "application/json")
    @CrossOrigin
    public JSONObject queryDataInfo(@RequestBody JSONObject jsonObject){

        JSONObject jsonObject1 = new JSONObject();

        String userName = jsonObject.getString("username");

        String date = jsonObject.getString("date");

        String dataInfo = userName+"."+date;

        List<DataInfoVO> list = queryRecordService.getDataInfo(dataInfo);

        List<DataVo> list1 = queryRecordService.getRecordData(dataInfo);


        List<Map> list2 = new ArrayList<>();
        List<Map> list3 = new ArrayList<>();

        for(DataInfoVO dataInfoVO :list){
            Map<String,String> detailInfo = new HashMap<>();
            detailInfo.put("beginDate",dataInfoVO.getStartTime().toString().substring(10,18));//beginTime更好？
            detailInfo.put("endDate",dataInfoVO.getEndTime().toString().substring(10,18));
            detailInfo.put("pattern","驾驶");
            list2.add(detailInfo);
        }

        for(DataVo dataVo:list1){
            Map<String,Object> detailData = new HashMap<>();
            detailData.put("attention",dataVo.getAttention().toString());
            detailData.put("meditation",dataVo.getMeditation().toString());
            detailData.put("timestamp",dataVo.getTimestamp().toString());
            detailData.put("raw_eeg",dataVo.getRawEeg());
            list3.add(detailData);
        }


        jsonObject1.put("info",list2);
        jsonObject1.put("data",list3);


        return jsonObject1;
    }

}
