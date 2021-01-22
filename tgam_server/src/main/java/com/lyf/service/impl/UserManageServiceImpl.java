package com.lyf.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.lyf.controller.vomain.UserVo;
import com.lyf.dao.domain.User;
import com.lyf.dao.mapper.UserMapper;
import com.lyf.service.UserManageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserManageServiceImpl implements UserManageService {

    @Autowired
    UserMapper userMapper;

    Logger logger = LoggerFactory.getLogger(UserManageService.class);

    @Override
    public JSONArray getAllUsers() {
        JSONArray jsonArray = new JSONArray();
        List<User> userList = userMapper.queryAll();

        for(User user:userList){
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
            jsonArray.add(userVo);
        }

        return jsonArray;
    }

    @Override
    public User getUserByUserId(Long userId) {

        return userMapper.queryByUserId(userId);
    }

    @Override
    public User getUserByUserName(String userName) {

       // JSONArray jsonArray = new JSONArray();

        User user  = userMapper.queryByUserName(userName);

        return user;
    }

    @Override
    public String insertUser(User user) {

       User user1 =  userMapper.queryByUserName(user.getUserName());

       if(user1!=null){ // 按照ID传入查询到该用户不空则已注册
           return "已注册";
       }
        userMapper.insertOne(user);
           return "插入成功";
    }


    /**
     *
     * @param userId
     * @return
     *
     * 先进行userID 的查询，无此用户返回无此用户，
     * 有个该ID再进行进一步的处理
     *
     */



    @Override
    public JSONArray deleteById(Long userId) {

        JSONArray jsonArray = new JSONArray();
        Map<String ,String > map = new HashMap<>();
        Map<String ,Integer > map2 = new HashMap<>();
        User user = userMapper.queryByUserId(userId);

        if(user==null){
            // 无此ID
            map.put("message","无此ID");
            map2.put("code",0);
        }else{
            boolean flag = userMapper.deleteById(userId);
            if(flag){
                map.put("message","删除"+userId+"成功");
                map2.put("code",1);
                System.out.println("业务层进行删除"+userId+"用户");
                logger.info(System.currentTimeMillis()+"删除了"+userId+"用户");
            }else{
                map2.put("code",0);
                map.put("message","删除"+userId+"失败");
            }
        }
        jsonArray.add(map);
        return jsonArray;
    }



    @Override
    public JSONArray deleteByUserName(String userName) {

        JSONArray jsonArray = new JSONArray();
        Map<String ,String > map = new HashMap<>();
        Map<String ,Integer > map2 = new HashMap<>();
        User user = userMapper.queryByUserName(userName);

        if(user==null){
            // 无此ID
            map.put("message","无此用户");
            map2.put("code",0);
        }else{
            boolean flag = userMapper.deleteByUserName(userName);
            if(flag){
                map.put("message","删除"+userName+"成功");
                map2.put("code",1);
                System.out.println("业务层进行删除"+userName+"用户");
                logger.info(System.currentTimeMillis()+"删除了"+userName+"用户");
            }else{
                map2.put("code",0);
                map.put("message","删除"+userName+"失败");
            }
        }
        jsonArray.add(map);
        return jsonArray;
    }



    @Override
    public JSONArray updateUser(User user) {
        JSONArray jsonArray = new JSONArray();

        Map<String,String> map0 = new HashMap<>();
        Map<String,String> map = new HashMap<>();
        Map<String,List<String>> map1 = new HashMap<>();
        List<String> updateContent = new LinkedList<>();
        updateContent.add("更新"+user.getUserName());

        if(user.getUserName()!=null){
            System.out.println("准备更新UserName为"+user.getUserName());
             updateContent.add("更新userName为"+user.getUserName());
        }

        if(user.getPassword()!=null){
            System.out.println("准备更新password为"+user.getPassword());
            updateContent.add("更新password为"+user.getPassword());

        }

        if(user.getJob()!=null){
            System.out.println("准备更新Job为"+user.getJob());
            updateContent.add("更新job为"+user.getJob());

        }

        if(user.getAge()!=null){
            System.out.println("准备更新age为"+user.getAge());
            updateContent.add("更新age为"+user.getAge());

        }

        if(user.getHeight()!=null){
            System.out.println("准备更新height为"+user.getHeight());
            updateContent.add("更新height为"+user.getHeight());

        }

        if(user.getWeight()!=null){
            System.out.println("准备更新weight为"+user.getWeight());
            updateContent.add("更新weight为"+user.getWeight());

        }
        //userManageService.
        if(user.getUserName()==null){
            map0.put("code","0");
            map.put("message","无效用户名,无法更新");
        }else{
            boolean flag = userMapper.updateUserInfo(user);
            if(flag){
                map0.put("code","1");
                map.put("message","更新"+user.getUserName()+"成功");


                logger.info(user.getUserName()+"进行了数据更新...");
            }else{
                map0.put("code","0");
                map.put("message","服务异常,更新"+user.getUserName()+"失败");
            }
        }

        map1.put("updateInfo",updateContent);
        jsonArray.add(map0);
        jsonArray.add(map);
        jsonArray.add(map1);
        return jsonArray;
    }
}
