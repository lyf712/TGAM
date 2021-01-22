package com.lyf.utils;

import java.sql.Timestamp;
import java.util.Date;

/**
 *
 * Date的使用（sql,util）
 */


public class TimeUtil {

    public static void main(String[]args){
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        System.out.println("timeStamp"+timestamp+";date"+date);

        System.out.println(TimeUtil.getDate());

        System.out.println(TimeUtil.getTime());

    }

    // 获取日期
    public static  String getDate(){
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        String time = timestamp.toString();
        String str = time.substring(0,10);
        return str;
    }

    public static String getTime(){
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        String time = timestamp.toString();
        String str = time.substring(11,19);
        return str;
    }


    public static String getAllTime(){
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        String time = timestamp.toString();
       // String str = time.substring(11,19);
        return time;
    }

    public static String timeToStandard(String time){

        String testStr = "2020-12-30-13-48-08";

        return time.substring(0,10)+" "+time.substring(11,19).replace('-',':');
    }

}
