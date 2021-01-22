package com.lyf.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lyf.dao.mapper.RecordMapper;
import com.lyf.service.RecordService;
import com.lyf.utils.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author lyf
 * @Data 2020-12-24
 * @Descroption
 * function: 数据实时上传
 *
 */


@Controller
@RequestMapping("record")
@EnableScheduling
public class RecordController {


    /**
     *
     * @param jsonObject
     * @return
     *
     * 一、交互流程
     * 1.前端发送数据过来(30s传输一次)
     *
     * client：
     *
     * command :"00000000"    八位、一字节，控制命令(只解析前两位)
     * 000 开始发送  001发送
     * 010 暂停 011恢复  100 结束
     * dataInfo :{
     *     deviceName:
     *     deviceType:
     *     recordIndex: // 当天第几次记录
     *     index:  // 包的第几次
     *     data:[]
     *     startTime:
     *     expectTime:
     *     totalTime: (实际时长,前端计算,最后一次写入)
     * }
     * token(username代替):
     * server:
     * response
     * code:
     * responseTime :   收到的时间
     * index : 收到的index
     * message: "" 描述收到情况
     *
     * data
     * /**        {
     * //        “attention” = 23;
     * //            "eeg_delta" = 181;
     * //            "eeg_high_alpha" = 31;
     * //            "eeg_high_beta" = 218;
     * //            "eeg_low_alpha" = 232;
     * //            "eeg_low_beta" = 4;
     * //            "eeg_low_gamma" = 188;
     * //            "eeg_mid_gamma" = 102;
     * //            "eeg_theta" = 170;
     * //        “meditation” = 63；
     * //        “timestamp” = "2020-09-05 07:01:45 +0000";
     * //        }
     *         */



    @Autowired
    RecordService recordService;

    @Autowired
    RecordMapper recordMapper;

    // 记录第几次记录数据的key
    static private String key = "";

    static private Integer timeout = null;
    static private Integer interval = null;

    static private int addRequestCount = 0;
    static private int recordIndex = 0;

    static private Timestamp firstQueryTime = null ;
    static private Timestamp lastRequestTime = null;
    static private Long time = 0L;//记录时长


    static private int flag = 0 ;//判断是否在进行存取数据


    Logger logger = LoggerFactory.getLogger(RecordController.class);


    //@Scheduled(fixedRate = 60000)  //或者比对缓存和计数（前端发来,自身计数）
    public void checkCon(){

      //  System.out.println("连接检查...");
        if(addRequestCount!=0){ //当不等于0时,发起过第一次请求
            addRequestCount--;// 消费,当发起过请求之后，定期消费请求次数，当未进行请求，一直消费至0则判断未长时间未连接
            if(addRequestCount==0){
                // 长时间未连接,进行数据异常存入
                recordService.suspendRecord();

            }
        }
    }


    @PostMapping(value = "/add",produces = "application/json")
    @ResponseBody
    @CrossOrigin
    public JSONArray addData(@RequestBody JSONObject jsonObject){

        // 请求计数
//        addRequestCount ++;

        System.out.println("res"+jsonObject);

        JSONArray jsonArray = new JSONArray();
        Map<String,String> code =new HashMap<>();
        Map<String,Timestamp> resTime = new HashMap<>();
        Map<String,Integer> index = new HashMap<>();
        Map<String,String> message = new HashMap<>();

        String command = jsonObject.getString("commandByte"); // 命令字节
        String dataIndex = jsonObject.getString("index");// 包序号

        String UUID = jsonObject.getString("UUID"); // 包的唯一标识符
        String userName = jsonObject.getString("username"); // 用户名
        String data = jsonObject.getString("data");// data  array

        String dataInfo = jsonObject.getString("info");// info  dictionary
        String dataMessage = jsonObject.getString("message");

        System.out.println("command"+command);



        // 缺失处理
        if(command==null||dataInfo==null||userName==null){
            code.put("code","0");
            Date date = new Date();
            resTime.put("responseTime",new Timestamp(date.getTime()));
            message.put("message","参数缺失");
            jsonArray.add(code);
            jsonArray.add(resTime);
            jsonArray.add(message);
            return jsonArray;
        }

        if("000".equals(command.substring(0,3))){
            // 开始处理数据

            // 开始记录时间
            String startTime = TimeUtil.getAllTime();

//            Map<String,Integer> map = JSONObject.parseObject(dataInfo,java.util.Map.class);
//
//            interval = map.get("interval");
//            timeout = map.get("timeout");

            firstQueryTime = Timestamp.valueOf(startTime);
            flag = 1;

            Date date = new Date();

            resTime.put("responseTime",new Timestamp(date.getTime()));

            // 后端查询第几次记录
             recordIndex = recordMapper.queryIndexByName(userName);


             lastRequestTime = Timestamp.valueOf(TimeUtil.getAllTime());

            // 查询记录获取index

            index.put("index",Integer.valueOf( dataIndex)); //返回当天第几次记录的第几次包,开始处理应该始终为0,即第一个包
            //准备开始处理数据,是否检验index？？

            this.key = userName+":"+ TimeUtil.getDate()+":"+recordIndex;

            logger.info("开始记录数据:记录key为"+key);
            // 记录缓存(第一次记录)
           boolean flag =  recordService.startRecord(key,startTime,dataIndex,data);

           if(flag){
               code.put("code","1");
               message.put("message","已收到并开始记录数据");
           }else {
               code.put("code","0");
               message.put("message","已收到但记录数据失败");
           }


        } else if("001".equals(command.substring(0,3))){
            // 发送
            logger.info("记录数据:记录key为"+key+"data为"+data);
            flag = 1;
            Timestamp now = Timestamp.valueOf(TimeUtil.getAllTime());
            lastRequestTime = now;

            Date date = new Date();

            resTime.put("responseTime",new Timestamp(date.getTime()));

            index.put("index",Integer.valueOf(dataIndex)); //返回当天第几次记录的第几次包

            boolean flag = recordService.handleRecord(now,dataIndex,data);//userName,recordIndex,

            if(flag){
                code.put("code","1");
                message.put("message","已接收并成功处理第"+dataIndex+"个包的发送请求");
            }else {
                code.put("code","0");
                message.put("message","已接收但未成功处理第"+dataIndex+"个包的发送请求");
            }

        }else if("010".equals(command.substring(0,3))){

            flag = 0;



            // 暂停
            code.put("code","1");
            Date date = new Date();
            resTime.put("responseTime",new Timestamp(date.getTime()));
            message.put("message","已收到暂停记录数据");
            // 解析index
            String index1 = jsonObject.getString("index");
            index.put("index",Integer.valueOf(dataIndex));
            recordService.suspendRecord();


        }else if("011".equals(command.substring(0,3))){

            // 恢复
            code.put("code","1");
            Date date = new Date();
            resTime.put("responseTime",new Timestamp(date.getTime()));
            message.put("message","已收到恢复记录数据");
            // 解析index
            String index1 = jsonObject.getString("index");
            index.put("index",Integer.valueOf(dataIndex));
            recordService.recoverRecord();

        }else if("100".equals(command.substring(0,3))){

            flag=0;
            logger.info("结束记录数据:记录key为"+key+"data为"+data);
            Timestamp now = Timestamp.valueOf(TimeUtil.getAllTime());

            lastRequestTime = now;

            //结束
            code.put("code","1");
            Date date = new Date();
            resTime.put("responseTime",new Timestamp(date.getTime()));
            message.put("message","已收到结束记录数据");
            // 解析index
            String recordIndex = jsonObject.getString("recordIndex");
            //String dataIndex = jsonObject.getString("dataIndex");

            String endTime = TimeUtil.getAllTime();

           boolean flag =  recordService.endRecord(dataIndex,endTime,data);// 根据服务响应的情况来具体响应  userName,recordIndex,
            if(flag){
                code.put("code","1");
                message.put("message","已成功记录第"+recordIndex+"次记录");
            }else {
                code.put("code","0");
                message.put("message","未成功记录第"+recordIndex+"次记录");
            }
        }

        jsonArray.add(code);
        jsonArray.add(resTime);
        jsonArray.add(index);
        jsonArray.add(message);

        return jsonArray;
    }

    @RequestMapping(value = "/queryFlag",produces = "application/json")
    @ResponseBody
    @CrossOrigin
    public JSONObject getFlag(){//@RequestBody JSONObject jsonObject1
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        Map<String,Integer> flags = new HashMap<>();
        flags.put("flag",flag);

        Map<String,String> message = new HashMap<>();

        Map<String,Long> times = new HashMap<>();
        Long time = 0L;
        if(firstQueryTime!=null&&lastRequestTime!=null)
        {
            time  = (lastRequestTime.getTime()-firstQueryTime.getTime())/1000;//s
            times.put("time",time);
            jsonArray.add(times);
        }


       if(flag==1){
           message.put("message","正在发送中");
       }else{
           message.put("message","未在发送中");
       }

       jsonArray.add(flags);
       jsonArray.add(message);
       jsonObject.put("FlagInfo",jsonArray);
        return jsonObject;
    }
}
