package com.lyf.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lyf.service.LoginService;
import com.lyf.utils.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

/***
 * @Author lyf
 * @Date 2020-12-12
 * @Version 1.0
 */


@Controller
public class LoginController {

    @Autowired
    LoginService loginService;

    private static final Logger log = LoggerFactory.getLogger(LoginController.class);// 若引入lombok 就可直接在注解即可

    /**
     * 用户名和密码登录
     * @param jsonObject
     * @return
     */
    @RequestMapping(value = "login",method = RequestMethod.POST,produces = "application/json")
    @ResponseBody
    @CrossOrigin
    public JSONArray login(@RequestBody JSONObject jsonObject){

        /**
         * String userId, @RequestParam(value = "password") String password, String deviceType
         */
        String userName = jsonObject.getString("username"); // 用户民登录
        String password = jsonObject.getString("password");
        String deviceType = jsonObject.getString("deviceType");

        if(userName==null||password==null||deviceType==null){
            JSONArray jsonArray = new JSONArray();
            Map<String,String> result= new HashMap<>();
            result.put("result","参数有误");
            jsonArray.add(result);
            return jsonArray;

        }

        log.info(userName+"登录");

        log.info(userName+";"+password+";"+deviceType);

        System.out.println("打印登录数据:"+userName+","+password+","+deviceType);

        JSONArray jsonArray = new JSONArray();

        jsonArray = loginService.loginService(userName,password,deviceType);

        System.out.println("data为:"+jsonArray);

        return jsonArray;
    }

    /***
     * token验证
     *
     * 前端发送token过来，然后验证header+payload
     *
     *
     *
     * @param jsonObject
     * @return
     */


    @PostMapping("loginByToken")
    @CrossOrigin
    public JSONArray loginByToken(@RequestBody JSONObject jsonObject) throws Exception {
        JSONArray jsonArray  = new JSONArray();

        String token = jsonObject.getString("token");

        JwtUtil.verifyToken(token);//

        

        return jsonArray;
    }

    /**
     * 短信登录验证
     *
     * 1.发送手机号过来
     * 2.返回验证码，60s有效，redis设置60s有效期
     *
     *
     * @param jsonObject
     * @return
     */


    // 点击获取验证码接口
    @RequestMapping("loginBySMS")
    @ResponseBody
   // @CrossOrigin
    public JSONArray loginBySMS(@RequestBody JSONObject jsonObject){

        JSONArray jsonArray = new JSONArray();

        Map<String,String> codeMap = new HashMap<>();
        Map<String,String> message =new HashMap<>();
        Map<String,String> flag =new HashMap<>();

        String phone = jsonObject.getString("number");

        String code = loginService.loginBySMS(phone);


        //codeMap.put("code",code);
        message.put("message","已发送验证码");
        flag.put("flag","1");

        jsonArray.add(codeMap);
        jsonArray.add(flag);
        jsonArray.add(message);


        return jsonArray;
    }

    // 点击登录接口
    @PostMapping("verifyCode")
    @ResponseBody
    @CrossOrigin
    public JSONArray verifyCode(@RequestBody JSONObject jsonObject){
        JSONArray jsonArray = new JSONArray();
        String number = jsonObject.getString("number");
        String code = jsonObject.getString("code");

        Map<String,String> result = new HashMap<>();
        Map<String,Integer> code1 = new HashMap<>();

        String result1=  loginService.checkCode(number,code);

        if("验证码失效".equals(result1))
        {
            result.put("message","验证码失效");
            code1.put("code",0);

        }else if("验证成功".equals(result1)){
            result.put("message","验证成功");
            code1.put("code",1);
        }else if("验证码有误".equals(result1)){
            result.put("message","验证码有误");
            code1.put("code",0);
        }


        jsonArray.add(result);
        jsonArray.add(code1);

        return jsonArray;
    }


}
