package com.lyf.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

public interface LoginService {


     JSONArray loginService(String userId,String password,String deviceType);

     //返回code
     String  loginBySMS(String number);

     String checkCode(String number,String inputCode);

}
