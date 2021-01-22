package com.lyf.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lyf.dao.domain.User;
import com.lyf.dao.mapper.RecordMapper;
import com.lyf.service.UserManageService;
import org.mybatis.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("userManager")
public class UserManageController {

    @Autowired
    UserManageService userManageService;

    Logger logger;

    @RequestMapping(value = "queryAll",method = RequestMethod.POST,produces = "application/json")
    @CrossOrigin
    JSONArray queryAllUsers(){
        return userManageService.getAllUsers();
    }

    @RequestMapping(value = "queryUserByName",method = RequestMethod.POST,produces = "application/json")
    @CrossOrigin
    JSONArray getUserByUserName(@RequestBody JSONObject jsonObject){ //String userName

        String userName = jsonObject.getString("username");

        System.out.println("获取name为"+userName);

        JSONArray jsonArray = new JSONArray();
        Map<String,User> map = new HashMap<>();

        map.put("user",userManageService.getUserByUserName(userName));

        jsonArray.add(map);

        return jsonArray;
    }

    @RequestMapping(value = "queryUserById",method = RequestMethod.POST,produces = "application/json")
    @CrossOrigin
    JSONArray getUserByUserId(@RequestBody JSONObject jsonObject){ //String userId

        String userId = jsonObject.getString("userID");

        JSONArray jsonArray = new JSONArray();
        Map<String,User> map = new HashMap<>();

        map.put("user",userManageService.getUserByUserId(Long.valueOf(userId)));

        jsonArray.add(map);
        return jsonArray;
    }


    @RequestMapping(value = "insertOne",method = RequestMethod.POST,produces = "application/json")
    @CrossOrigin
    JSONArray insertUser(@RequestBody JSONObject jsonObject){ //User user
       //  String userId = jsonObject.getString("userId");
        User user = JSON.parseObject(jsonObject.toJSONString(),User.class);
        String result= null;
        JSONArray jsonArray = new JSONArray();
        Map<String,Integer> map = new HashMap<>();
        Map<String,String> map1 = new HashMap<>();


        if(user.getUserName()!=null){ // 不为空才进行插入此用户
            result   =  userManageService.insertUser(user);

            if("已注册".equals(result)){  //userManageService.insertUser(user)
                map.put("code",0);
            }else {
                map.put("code",1);
            }

        }else{

            map.put("code",0);
            result = "无效插入,未输入用户名";
        }

        map1.put("message",result); //userManageService.insertUser(user)
        jsonArray.add(map);
        jsonArray.add(map1);

        return jsonArray;
    }



    @RequestMapping(value = "deleteUser",method = RequestMethod.POST,produces = "application/json")
    @CrossOrigin
    JSONArray deleteUserByUserName(@RequestBody JSONObject jsonObject){
        JSONArray jsonArray = new JSONArray();
        User user = JSONObject.parseObject(String.valueOf(jsonObject),User.class);
        if(user.getUserName()!=null){
            jsonArray = userManageService.deleteByUserName(user.getUserName());
        }else {
            Map<String,String> map = new HashMap<>();
            Map<String,String> map1 = new HashMap<>();
            map.put("code","0");
            map1.put("message","无效用户名");
            jsonArray.add(map);
            jsonArray.add(map1);
        }
        return jsonArray;
    }

    @RequestMapping(value = "updateUser",method = RequestMethod.POST,produces = "application/json")
    @CrossOrigin
    JSONArray update(@RequestBody JSONObject jsonObject){

        User user = JSONObject.parseObject(String.valueOf(jsonObject),User.class);

        JSONArray jsonArray =  userManageService.updateUser(user);

        return jsonArray;
    }






}
