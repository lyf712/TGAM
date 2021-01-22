package com.lyf.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.lyf.dao.domain.Record;
import com.lyf.dao.domain.RecordData;
import com.lyf.dao.domain.RecordInfo;
import com.lyf.dao.mapper.RecordDataMapper;
import com.lyf.dao.mapper.RecordInfoMapper;
import com.lyf.dao.mapper.RecordMapper;
import com.lyf.service.RecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Service
public class RecordServiceImpl implements RecordService {

    @Autowired
    RecordMapper recordMapper;

    @Autowired
    RecordInfoMapper recordInfoMapper;

    @Autowired
    RecordDataMapper recordDataMapper;


    Logger logger = LoggerFactory.getLogger(RecordService.class);

    // 记录key,并发时设计set进行存储？？
    static private String key ="";
    private String startTime ="";
    private String endTime = "";

    @Autowired
    StringRedisTemplate redisTemplate;

    @Override
    public boolean startRecord(String key,String startTime,String index,String data) {

        if(this.key=="")
        {
            redisTemplate.opsForList().leftPush(key,"record#"+startTime+"#"+0+"#"+data);
        }

        // 记录
        this.key = key;
        // String keys =this.key + ":"+index;
        // data处理,解析data; 或者前端解析jsonObject获取相应的值
        this.startTime = startTime;

        redisTemplate.expire(this.key, Duration.ofSeconds(6000));//失效时间具体而定

        return true;
    }

    // 存入Redis缓存中
    @Override  //String userName,String recordIndex,
    public boolean handleRecord(Timestamp now,String dataIndex,String data) {

        if("".equals(this.key)){
            return false;
        }

       // if(redisTemplate.opsForValue().get())
        redisTemplate.opsForList().leftPush(this.key,"record#"+now+"#"+dataIndex+"#"+data); // 插入异常的反馈？？

        System.out.println("key为"+this.key);

        return true;
    }

    @Override
    public boolean suspendRecord() {

         // 写入tempTable : 存放非正常结束请求的数据

        System.out.println("暂停处理");
       // Time.sleep(1000);


        return false;
    }

    @Override
    public boolean recoverRecord() {
        return false;
    }

    @Override ///String userName,String recordIndex,
    public boolean endRecord(String dataIndex,String endTime,String data) {

       // String key = userName+":"+TimeUtil.getDate()+":"+recordIndex+dataIndex;
        //

        if("".equals(this.key)){
            return false;
        }
        redisTemplate.opsForList().leftPush(this.key,"record#"+endTime+"#"+dataIndex+"#"+data); // 插入异常的反馈？？

        this.endTime = endTime;

        Long len = redisTemplate.opsForList().size(this.key);

        List<String> list = redisTemplate.opsForList().range(this.key,0,len);


        storageData(this.key,startTime,endTime,list);

        // 清除此次记录的缓存key

        this.key = "";//置空,防止还未开始记录就已有key
        this.endTime ="";
        this.startTime = "";
        return true;
    }

    public String storageData(String keys,String startTime,String endTime,List<String> content){

        System.out.println("正常写入库...");
        // 写入record  user_name info_id  index timestamp
        // key username+date+recordIndex
        String[] info = keys.split("\\:");
        String userName = info[0];
        String date = info[1];
        String index = info[2];

        if(index==null||userName==null||date==null){
            return "参数为空";
        }

        String infoId = userName+"."+date+"."+index;

        Record record = new Record();
        record.setIndex(Integer.valueOf(index));
        record.setInfoId(infoId);

        record.setTimeStamp(Timestamp.valueOf(startTime));
        record.setUserName(userName);

//        System.out.println("记录record"+record);

        recordMapper.insertRecord(record);

        // 写入record_info

        Long totalTime = (Timestamp.valueOf(endTime).getTime()-Timestamp.valueOf(startTime).getTime())/1000;
        String model = "驾驶";
        String device = "iPhone";

        RecordInfo recordInfo = new RecordInfo();
        recordInfo.setInfoId(infoId);
        recordInfo.setStartTime(Timestamp.valueOf(startTime));
        recordInfo.setEndTime(Timestamp.valueOf(endTime));
        recordInfo.setTotalTime(totalTime);
        System.out.println("recordInfo"+recordInfo);
        recordInfoMapper.insertRecordInfo(recordInfo);

        //遍历写入（data应为一个list）

        List<RecordData> recordDataList = new ArrayList<>();


        System.out.println("content"+content);

         // content中间的一个data
//        [{attention=0, timestamp=2020-12-30-13-48-08, meditation=0, raw_eeg=[52, 207, 232, 108, 207, 248, 182, 74], battery=200},
//        {attention=0, timestamp=2020-12-30-13-48-08, meditation=0, raw_eeg=[52, 207, 232, 108, 207, 248, 182, 74], battery=200},
//        {attention=0, timestamp=2020-12-30-13-48-08, meditation=0, raw_eeg=[52, 207, 232, 108, 207, 248, 182, 74], battery=200},
//        {attention=0, timestamp=2020-12-30-13-48-08, meditation=0, raw_eeg=[52, 207, 232, 108, 207, 248, 182, 74], battery=200},
//        {attention=0, timestamp=2020-12-30-13-48-08, meditation=0, raw_eeg=[52, 207, 232, 108, 207, 248, 182, 74], battery=200},
//        {attention=0, timestamp=2020-12-30-13-48-08, meditation=0, raw_eeg=[52, 207, 232, 108, 207, 248, 182, 74], battery=200},
//        {attention=0, timestamp=2020-12-30-13-48-08, meditation=0, raw_eeg=[52, 207, 232, 108, 207, 248, 182, 74], battery=200},
//        {attention=0, timestamp=2020-12-30-13-48-08, meditation=0, raw_eeg=[52, 207, 232, 108, 207, 248, 182, 74], battery=200},
//        {attention=0, timestamp=2020-12-30-13-48-08, meditation=0, raw_eeg=[52, 207, 232, 108, 207, 248, 182, 74], battery=200},
//        {attention=0, timestamp=2020-12-30-13-48-08, meditation=0, raw_eeg=[52, 207, 232, 108, 207, 248, 182, 74], battery=200}]

        logger.info("写入数据库收到的信息为:"+String.valueOf(content));



        // 写入record_data
        for(int i =content.size()-1;i>=0;i--){//String dataArr:content

            String dataArr = content.get(i);
            System.out.println("testData"+dataArr);

            String[] dataInfo = dataArr.split("\\#");
            String timestamp = dataInfo[1];//dataArr.substring(7,30);//截取响应请求时间
            String dataIndex = dataInfo[2];
            System.out.println("timestamp"+timestamp);


           // String data = dataInfo[3].substring(1,dataInfo[3].length()-1);//dataArr.substring(34,dataArr.length()-1);
           // String[] dataArr2 = data.split("\\,");

            JSONArray dataArray = new JSONArray();
           /// JSONObject realData = new JSONObject();
            try{
               dataArray = JSONArray.parseArray(dataInfo[3].replace('=',':'));
            }catch (JSONException e){
                logger.error(e.getMessage());
                e.printStackTrace();

                System.out.println("数据解析异常"+e.getMessage()+";"+e.getLocalizedMessage());


            }

            System.out.println("dataArray为"+dataArray);


            RecordData recordData = new RecordData();

            int len = dataArray.size();

            System.out.println("dataIndex"+dataIndex);

            for(int j=0 ;j<len;j++){
                JSONObject jsonObject = JSONObject.parseObject(dataArray.get(j).toString());


                String rawEeg = jsonObject.getString("raw_eeg");//.substring(1,jsonObject.getString("raw_eeg").length()-1);
                JSONArray rawEegArr = JSONArray.parseArray(rawEeg);
                Integer len2 = rawEegArr.size();

                String timestamp1 = jsonObject.getString("timestamp");
                String tempTime = timestamp1.substring(0,10)+" "+timestamp1.substring(11,19).replace('-',':');

                System.out.println("测试"+rawEeg+","+timestamp1);


                recordData.setDataIndex(dataIndex);
                recordData.setInfoId(infoId);
                recordData.setTimestamp(Timestamp.valueOf(tempTime));
                recordData.setChannel1(0<len2?Float.valueOf(rawEegArr.get(0).toString()):null);
                recordData.setChannel2(1<len2?Float.valueOf(rawEegArr.get(1).toString()):null);
                recordData.setChannel3(2<len2?Float.valueOf(rawEegArr.get(2).toString()):null);
                recordData.setChannel4(3<len2?Float.valueOf(rawEegArr.get(3).toString()):null);
                recordData.setChannel5(4<len2?Float.valueOf(rawEegArr.get(4).toString()):null);
                recordData.setChannel6(5<len2?Float.valueOf(rawEegArr.get(5).toString()):null);
                recordData.setChannel7(6<len2?Float.valueOf(rawEegArr.get(6).toString()):null);
                recordData.setChannel8(7<len2?Float.valueOf(rawEegArr.get(7).toString()):null);




//                recordData.setDataIndex(dataIndex);
//                recordData.setInfoId(infoId);
//                recordData.setTimestamp(Timestamp.valueOf(timestamp));
//                recordData.setChannel1(0<len?Float.valueOf(dataArray.get(0).toString()):null);
//                recordData.setChannel2(1<len?Float.valueOf(dataArray.get(1).toString()):null);
//                recordData.setChannel3(2<len?Float.valueOf(dataArray.get(2).toString()):null);
//                recordData.setChannel4(3<len?Float.valueOf(dataArray.get(3).toString()):null);
//                recordData.setChannel5(4<len?Float.valueOf(dataArray.get(4).toString()):null);
//                recordData.setChannel6(5<len?Float.valueOf(dataArray.get(5).toString()):null);
//                recordData.setChannel7(6<len?Float.valueOf(dataArray.get(6).toString()):null);
//                recordData.setChannel8(7<len?Float.valueOf(dataArray.get(7).toString()):null);



                recordDataList.add(recordData);
            }

            System.out.println("recordData"+recordData);
        }

        redisTemplate.delete(this.key);
        this.key = "";//置空,防止还未开始记录就已有key
        this.endTime ="";
        this.startTime = "";

        System.out.println("recordList:"+recordDataList);

        recordDataMapper.insertBatchRecordData(recordDataList);

        return "记录成功";
    }


    public void storageData(String dataArr){

//        String str = " [{attention=0, timestamp='2020-12-30-13-48-08', meditation=0, raw_eeg=[52, 207, 232, 108, 207, 248, 182, 74], battery=200}, " +
//                "{attention=0, timestamp='2020-12-30-13-48-08', meditation=0, raw_eeg=[52, 207, 232, 108, 207, 248, 182, 74], battery=200}, "+
//                "{attention=0, timestamp='2020-12-30-13-48-08', meditation=0, raw_eeg=[52, 207, 232, 108, 207, 248, 182, 74], battery=200}]";




        String str2 = dataArr.replace('=',':');

        JSONArray jsonArray = JSONArray.parseArray(str2);

        Integer len =  jsonArray.size();

        for(int i =0;i<len;i++){

            JSONObject jsonObject = JSONObject.parseObject(jsonArray.get(i).toString());

            System.out.println(jsonObject.get("raw_eeg"));
        }

        System.out.println(jsonArray);

    }





}
