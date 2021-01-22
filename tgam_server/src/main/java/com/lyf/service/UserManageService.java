package com.lyf.service;

import com.alibaba.fastjson.JSONArray;
import com.lyf.dao.domain.User;
import com.lyf.dao.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface UserManageService {

     JSONArray getAllUsers();

     User getUserByUserId(Long userId);

     User getUserByUserName(String userName);

     String insertUser(User user);

     JSONArray deleteById(Long userId);

     JSONArray deleteByUserName(String userName);

     JSONArray updateUser(User user);


}
