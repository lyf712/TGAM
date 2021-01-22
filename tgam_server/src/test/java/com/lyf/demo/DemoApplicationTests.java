package com.lyf.demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.impl.JWTParser;
import com.auth0.jwt.interfaces.Claim;

import com.lyf.dao.domain.RecordData;
import com.lyf.dao.mapper.*;
import com.lyf.rabbitmq.provider.MessageProvider1;
import com.lyf.utils.JwtUtil;
import com.lyf.utils.TimeUtil;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.sql.Timestamp;
import java.util.*;

@SpringBootTest
class DemoApplicationTests {

    @Autowired
    UserMapper userMapper;


    @Autowired
    RecordMapper recordMapper;

    @Autowired
    RecordDataMapper recordDataMapper;
    @Autowired
    RecordInfoMapper recordInfoMapper;

    @Autowired
    RecordDataTableMapper recordDataTableMapper;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    StringRedisTemplate redisTemplate2;

    @Autowired
    MessageProvider1 messageProvider1;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Test
    void contextLoads() {

    }



    @Test
    void test() throws InterruptedException {

        Random random = new Random();
        for(int i=0;i<10;i++){
            String code = ""+random.nextInt(9)+
                    random.nextInt(9)+ random.nextInt(9)
                    + random.nextInt(9);
            System.out.println("code"+code);
        }

    }

    @Test
    void testToken() throws Exception {


        String tokenStr = JwtUtil.createToken("Alan21","32");

        Map<String, Claim> map = JwtUtil.parseToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiLmtYvor5UiLCJ1c2VyTmFtZSI6IjMyIiwiZXhwIjoxNjA4OTAzNTg1LCJ1c2VySWQiOiJBbGFuMjEiLCJpYXQiOjE2MDg5MDM1MzV9.T6-9pOw1qVUYGbCXYDd19AnKOBWYIG6cdPaLl8-2VWs");

        System.out.println(map.get("userId").asString());

        System.out.println(map.get("userName").asString());

        Object[] arr = map.values().toArray();

        for(int i =0;i<arr.length;i++)
        {
            System.out.println(arr[i].toString());

        }

        System.out.println(tokenStr);
        JWTParser jwtParser=new JWTParser();



//       Header header = jwtParser.parseHeader(tokenStr);
//       Payload payload = jwtParser.parsePayload(tokenStr);
//        System.out.println(header+":"+payload);
//        Jws<Claims> jws = JWTParsers.parser().setSigningKey(SECRET).parseClaimsJws(token);
//        String signature = jws.getSignature();
//        Map<String, String> header = jws.getHeader();
//        Claims Claims = jws.getBody();
//
//        for(int i =0;i<10;i++)
//        messageProvider1.send("helo");
//        MessageProvider1 messageProvider1 = new MessageProvider1();
//
//        for(int i =0;i<20;i++){
//            messageProvider1.send("hello");
//        }
    }


    @Test
    void testS(){
        String s  ="A man, a plan, a canal: Panama";
        String str = "";
        int i=0,j=s.length()-1;
        String s1 = s.toLowerCase();
        System.out.println("s1"+s1);

        while(i<j){
            // 进行是否是字符串判断
            while(s1.charAt(i)<'a'||s1.charAt(i)>'z'){ i++;}

            while(s1.charAt(j)<'a'||s1.charAt(j)>'z') {j--;}

            if(s1.charAt(i)!=s1.charAt(j)){
                // return false;
                System.out.println("false");
            }else {
                i++;
                j--;
            }
        }
        //return true;
        System.out.println("true");
    }



    @Test
    void testQueryRecordInfo(){

        List<RecordData> list = new ArrayList<>();
        RecordData recordData = new RecordData();
        recordData.setChannel1(1F);
        recordData.setChannel2(2F);
        recordData.setChannel3(3F);
        list.add(recordData);

        JSONObject jsonObject = new JSONObject();
        jsonObject = JSONObject.parseObject("{attention=0, timestamp='2020-12-30-13-48-08', meditation=0, raw_eeg=[52, 207, 232, 108, 207, 248, 182, 74], battery=200}".replace('=',':'));

        String str = "[{attention=0, timestamp='2020-12-30-13-48-08', meditation=0, raw_eeg=[52, 207, 232, 108, 207, 248, 182, 74], battery=200}, {attention=0, timestamp='2020-12-30-13-48-08', meditation=0, raw_eeg=[52, 207, 232, 108, 207, 248, 182, 74], battery=200}, {attention=0, timestamp='2020-12-30-13-48-08', meditation=0, raw_eeg=[52, 207, 232, 108, 207, 248, 182, 74], battery=200}, {attention=0, timestamp='2020-12-30-13-48-08', meditation=0, raw_eeg=[52, 207, 232, 108, 207, 248, 182, 74], battery=200}, {attention=0, timestamp='2020-12-30-13-48-08', meditation=0, raw_eeg=[52, 207, 232, 108, 207, 248, 182, 74], battery=200}, {attention=0, timestamp='2020-12-30-13-48-08', meditation=0, raw_eeg=[52, 207, 232, 108, 207, 248, 182, 74], battery=200}, {attention=0, timestamp='2020-12-30-13-48-08', meditation=0, raw_eeg=[52, 207, 232, 108, 207, 248, 182, 74], battery=200}, {attention=0, timestamp='2020-12-30-13-48-08', meditation=0, raw_eeg=[52, 207, 232, 108, 207, 248, 182, 74], battery=200}, {attention=0, timestamp='2020-12-30-13-48-08', meditation=0, raw_eeg=[52, 207, 232, 108, 207, 248, 182, 74], battery=200}, {attention=0, timestamp='2020-12-30-13-48-08', meditation=0, raw_eeg=[52, 207, 232, 108, 207, 248, 182, 74], battery=200}]".replace('=',':');


        System.out.println("ces"+jsonObject);
        System.out.println(jsonObject.getString("raw_eeg"));



    }

    @Test
    void testJSONArray(){

             String str = "[{attention=0, timestamp=2020-12-30-13-48-08, meditation=0, raw_eeg=[52, 207, 232, 108, 207, 248, 182, 74], battery=200}, " +
               "{attention=0, timestamp=2020-12-30 13:48:08, meditation=0, raw_eeg=[52, 207, 232, 108, 207, 248, 182, 74], battery=200}, "+
               "{attention=0, timestamp=2020-12-30-13-48-08, meditation=0, raw_eeg=[52, 207, 232, 108, 207, 248, 182, 74], battery=200}]";


             String str2 = str.replace('=',':');

             String str3 = JSON.toJSONString(str2);

             JSONArray  jsonArray = JSONArray.parseArray(str2);

             Integer len =  jsonArray.size();

             for(int i =0;i<len;i++){
                 JSONObject jsonObject = JSONObject.parseObject(jsonArray.get(i).toString());

                 System.out.println(jsonObject.get("raw_eeg"));

                 System.out.println(TimeUtil.timeToStandard(jsonObject.get("timestamp").toString()));


                 System.out.println(Timestamp.valueOf(TimeUtil.timeToStandard(jsonObject.get("timestamp").toString())));

             }


             System.out.println(jsonArray);


    }

    @Test
    void testTime()
    {
//        String time = "2020-12-30-13-48-08";
//        String tempTime = time.substring(0,10)+" "+time.substring(11,19).replace('-',':');
//
//        System.out.println(tempTime);
//        Timestamp.valueOf(tempTime);

        String str = "[1,2,3,4,5]";
        System.out.println(str.substring(1,2));
        System.out.println(str.substring(3,4));
    }



    @Test
    void test2(){
        System.out.println("hello");

    }


}

