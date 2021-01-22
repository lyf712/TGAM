package com.lyf.utils;


/**
 * Description  Token生成工具
 * header + payload + signature
 *
 * AUTHOR lyf
 *
 *流程分析：
 * 1、此类生成一个token（包括header+payload+signature）,返回到客户端,并将此token的签名
 * 放入Redis或者MySQL数据库
 * 2、客户端发送数据带token(包含header和payload)
 * 3、检验token 以防伪造,解析token为三部分,检查header和payload生成的签名是否和发来的一致
 * 4、检查数据库或者缓存是否存在此签名
 *
 */





public class TokenUtil {

    public static final String TOKEN_AES_KEY ="";


    public void test(){



    }


}
