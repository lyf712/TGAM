package com.lyf.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lyf.controller.vomain.UserVo;
import com.lyf.dao.domain.User;
import com.lyf.dao.mapper.UserMapper;
import com.lyf.sdk.Sms;
import com.lyf.service.LoginService;
import com.lyf.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    StringRedisTemplate redisTemplate;


    @Override
    public JSONArray loginService(String userName, String password, String deviceType) {

        System.out.println("login服务层接受到数据为："+userName+" "+password+" "+deviceType);

        JSONArray jsonArray = new JSONArray();
        Map<String,String> message = new HashMap<>();
        Map<String,UserVo> result = new HashMap<>();
        Map<String,Integer> code = new HashMap<>();
        Map<String,String> token = new HashMap<>();

        // 获取用户信息
      //  User user =  userMapper.queryByUserId(Integer.valueOf(userId));

        User user = userMapper.queryByUserName(userName);
       // System.out.println("user Pwd"+user.getPassword());

        if(user!=null){
            // 检查登录设备？
            if(password.equals(user.getPassword())){//密码正确
                message.put("message","登录成功");
                code.put("code",1);
                UserVo userVo = new UserVo();
                // 返回用户信息
                userVo.setRealName(user.getRealName());
                userVo.setAge(user.getAge());
                userVo.setJob(user.getJob());
                userVo.setWeight(user.getWeight());
                userVo.setHeight(user.getHeight());
                userVo.setBirthday(user.getBirthday());
                userVo.setIdNumber(user.getIdNumber());
                userVo.setHeadImg(user.getHeadImg());

                result.put("result",userVo);  // 密码正确才返回用户信息
                // 放入token
                try{
                    String tokenStr = JwtUtil.createToken(String.valueOf(user.getId()),user.getPassword());

                    token.put("token",tokenStr);

                    redisTemplate.opsForValue().set(String.valueOf(user.getId()),user.getPassword());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else{
                message.put("message","密码错误");
                code.put("code",0);
                result.put("result",null);
            }
            token.put("token",null);// 检查登录设备？

        }else{// 无此用户
            message.put("message","无此用户");
            code.put("code",0);
            result.put("result",null);
            token.put("token",null);// 检查登录设备？
        }

        jsonArray.add(message);
        jsonArray.add(result);
        jsonArray.add(code);
        jsonArray.add(token);

        return  jsonArray;
    }

    @Override
    public String loginBySMS(String number) {


        Random random = new Random();
        String code = ""+random.nextInt(9)+
                random.nextInt(9)+ random.nextInt(9)
                + random.nextInt(9);

        Sms sms  = new Sms();

        redisTemplate.opsForValue().set("code:"+number,code);
        // 60s有效期
        redisTemplate.expire("code:"+number, Duration.ofSeconds(60));
        // 发送登录信息
        // 发送短信可能出现什么错误？？
        sms.sendSms(number,code);

        // 考虑记录信息
        return code;//返回验证码
    }

    @Override
    public String checkCode(String number,String inputCode) {



        String realCode = redisTemplate.opsForValue().get("code:"+number);


        System.out.println("验证的code为"+inputCode+";realCode:"+realCode);

        if(realCode==null){
            return "验证码失效";
        }

        if(realCode.equals(inputCode)){
            return "验证成功";
        }

        return "验证码有误";
    }





}
