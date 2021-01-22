package com.lyf.exception;

import com.alibaba.fastjson.JSONException;
import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

//@Slf4j
@ControllerAdvice
public class ExceptionHandler {

    Logger logger = LoggerFactory.getLogger(ExceptionHandler.class);

    @org.springframework.web.bind.annotation.ExceptionHandler
    @ResponseBody
    public String ErrorHandler(AuthorizationException e){
        logger.error("没有通过权限验证",e);
        return "没有权限";
    }


    @org.springframework.web.bind.annotation.ExceptionHandler
    @ResponseBody
    public String TimeStampHandler(JSONException e){

        logger.error("JSON解析异常",e);
        return "字符分解ERROR";
    }

}
