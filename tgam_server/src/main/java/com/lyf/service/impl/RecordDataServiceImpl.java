package com.lyf.service.impl;

import com.lyf.dao.domain.Record;
import com.lyf.dao.domain.RecordData;
import com.lyf.dao.domain.RecordInfo;
import com.lyf.dao.mapper.RecordDataMapper;
import com.lyf.dao.mapper.RecordInfoMapper;
import com.lyf.dao.mapper.RecordMapper;
import com.lyf.service.RecordDataService;
import com.lyf.service.status.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Duration;
import java.util.*;

@Service
@EnableScheduling
public class RecordDataServiceImpl implements RecordDataService {

    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired
    RecordMapper recordMapper;

    @Autowired
    RecordInfoMapper recordInfoMapper;

    @Autowired
    RecordDataMapper recordDataMapper;

    private String key;

   // @Scheduled(fixedRate = 2000)
    public void test(){
        System.out.println(redisTemplate.opsForValue().get("res"));
        System.out.println("yes");
    }

    /***
     *
     * @param flag
     * @param date
     * @param userName
     * @param data
     * @return
     *
     * 1.防止中途插入？设置一个命令牌
     * 2.并发插入？
     */

    @Override
    public boolean addData(String flag, String date, String userName, String data) {

        System.out.println("业务层收到数据"+flag+";"+date+";"+userName+";"+data);

        if(flag.equals("start_time")){//开始记录处理，进行判断第几次进行插入

            //若传入多了"" 需要加.substring(1,flag.length()-1)进行处理   使用web请求则无需，但URL就需要加上

            // SELECT MAX(index) from record where user_id = userId and timestamp between ();
            // 时间限制在今天

            System.out.println("Service start recording data...");

            // =>userName;
            int index = recordMapper.queryIndexByName(userName);


            key = "record:"+userName+":"+index;//缓存当天清除  .substring(1,userId.length()-1)

            System.out.println("Service start:recordUserId:"+userName.substring(1,userName.length()-1));

            Map<String,String> map = new LinkedHashMap<>();
            // 思考在类的作用域时间是多久，可以将map放外面先进行map的灌入到end_time再统一放进缓存吗
            map.put(date,data);

            redisTemplate.opsForList().rightPush(key,String.valueOf(map));//

            redisTemplate.expire(key, Duration.ofSeconds(6000)); //100min失效

            return true;

        }else if(flag.equals("process")&&key!=null){//记录中
            //.substring(1,flag.length()-1)
            System.out.println("Service process:record."+userName);

            Map<String,String> map = new LinkedHashMap<>();
            // 思考在类的作用域时间是多久，可以将map放外面先进行map的灌入到end_time再统一放进缓存吗
            map.put(date,data);
            // 开始记录
            System.out.println("key:"+key+"; data:"+String.valueOf(map));

            redisTemplate.opsForList().rightPush(key,String.valueOf(map));//

            return true;

        }else if(flag.equals("end_time")){//结束记录
            //.substring(1,flag.length()-1)

            System.out.println("record data finished...");

            Map<String,String> map = new LinkedHashMap<>();
            // 思考在类的作用域时间是多久，可以将map放外面先进行map的灌入到end_time再统一放进缓存吗
            map.put(date,data);
            // 开始记录
            redisTemplate.opsForList().rightPush(key,String.valueOf(map));//

            //开始写入数据库  （判断是否不完整数据？？）

            List<Map<String,List<Float>>> list = new
                    LinkedList<>();

            // 最后一次会丢包
            List<String> str  = redisTemplate.opsForList().range(key,0,100);

            // 写入数据
             writeToDB(str,key);
             return true;
        }

        return false;
    }

    // 获取记录信息
    @Override
    public List<RecordInfo> getRecordInfo(String infoId) {

        List<RecordInfo> list = recordInfoMapper.getRecordInfo(infoId);

        return list;
    }

    // 获取记录数据
    @Override
    public List<RecordData> getRecordData(String infoId) {

        List<RecordData> list = recordDataMapper.getRecordData(infoId);

        return list;
    }


    // 处理函数

    /**
     *
     *
     * @param list
     *
     * 1.redis 获取当前时段的数据
     * get key   record:userId:index    key每天进清除，因此只能获取当天的，此key能够获取，第二天则失效不能写入
     * ，那么考虑边界的12点进行测试呢=> 可以设置时效（一段时间后进行清除），那么key将会出现重名？？
     * 暂且不考虑，，关于key命名再待商量
     *
     * 2.
     *
     */


    // 写入数据库
    public void writeToDB(List<String> list,String key) {

        Integer userId ;
        String userName;
        String infoId;
        Integer index;
        Timestamp startTime;
        Timestamp endTime;
        long totalTime; // s为单位
        // 附加信息

        // 处理数据类型
        String[] keyInfo = key.split("\\:");//分隔出 record  userId index

        //userId = Integer.valueOf(keyInfo[1]);

        userName = keyInfo[1];

        index = Integer.valueOf(keyInfo[2]);

        String firstData = list.get(0);
        String endData = list.get(list.size()-1);

        String[] startInfo = firstData.split("\\=");
        String[] endInfo = endData.split("\\=");

        System.out.println("time:"+startInfo[0].substring(1,startInfo[0].length())+";"+endInfo[0].substring(1,startInfo[0].length()));

        startTime = Timestamp.valueOf( startInfo[0].substring(1,startInfo[0].length()));
        endTime = Timestamp.valueOf( endInfo[0].substring(1,startInfo[0].length()));

        totalTime = (endTime.getTime()-startTime.getTime())/1000;

        System.out.println("分解为："+userName+"；"+index+";");

        infoId = userName+"."+startInfo[0].substring(1,11)+"."+index;
        System.out.println("infoId"+infoId);

        List<RecordData> recordDataList = new ArrayList<>();


        System.out.println("开始写入数据库");

        int count = 0; // 应该以前端定义的计数为准


        for (String e : list) {
            System.out.println(e);
            Timestamp timestamp ;
            String[] dataInfo = e.split("\\=");

            System.out.println("dataINFO:"+dataInfo[0]+";"+dataInfo[1]);
            timestamp = Timestamp.valueOf(dataInfo[0].substring(1,20));
            System.out.println("timestampTest"+timestamp);

            String[] detailData = dataInfo[1].split("\\,");
            detailData[detailData.length-1]= detailData[detailData.length-1].substring(0,detailData[detailData.length-1].length()-1);
            for(String s:detailData){
                System.out.println(s);
            }
//

            recordDataList.add(new RecordData(null,infoId,String.valueOf(count),timestamp,Float.valueOf(detailData[0].substring(1,detailData[0].length())),Float.valueOf(detailData[1])
            ,Float.valueOf(detailData[2]),Float.valueOf(detailData[3]),Float.valueOf(detailData[4]),Float.valueOf(detailData[5]),Float.valueOf(detailData[6]),Float.valueOf(detailData[7].substring(0,detailData[7].length()-1))));


            count++;

        }
        // 写入record {id,userId,infoId,index,timeStamp}
        // null,userId(传入),infoID(拼凑),index和timeStamp（分解）

        // 写入recordData

        int flag ;
        Record record = new Record(null,userName,infoId,index,startTime);

        flag = recordMapper.insertRecord(record);

        if(flag==0){
            System.out.println("插入record失败");
        }else{
            System.out.println("插入record成功");
        }

        String model="model1";
        String device = "ios";

        int flag2 ;
        RecordInfo recordInfo = new RecordInfo(null,infoId,startTime,endTime,totalTime,model,device);
        recordInfoMapper.insertRecordInfo(recordInfo);




        // 记录数据
        int flag3;
        flag3 = recordDataMapper.insertBatchRecordData(recordDataList);

        // 需要将此次进行清空（不应清空，
        // 应设置index来明确第几次（设置时效为一天），
        // 前端查询可以使用缓存进行查询）
    }


}
