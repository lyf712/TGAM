package com.lyf.sdk;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.ecs.model.v20140526.DescribeInstancesRequest;
import com.aliyuncs.ecs.model.v20140526.DescribeInstancesResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import org.apache.tomcat.util.json.JSONParser;

public class Sms {

//    public static void main(String[] args){
//        // 创建DefaultAcsClient实例并初始化
//        DefaultProfile profile = DefaultProfile.getProfile(
//                "<your-region-id>",          // 地域ID
//                "<your-access-key-id>",      // RAM账号的AccessKey ID
//                "<your-access-key-secret>"); // RAM账号AccessKey Secret
//        IAcsClient client = new DefaultAcsClient(profile);
//        // 创建API请求并设置参数
//        DescribeInstancesRequest request = new DescribeInstancesRequest();
//        request.setPageSize(10);
//        // 发起请求并处理应答或异常
//        DescribeInstancesResponse response;
//        try {
//            response = client.getAcsResponse(request);
//            for (DescribeInstancesResponse.Instance instance:response.getInstances()) {
//                System.out.println(instance.getPublicIpAddress());
//            }
//        } catch (ServerException e) {
//            e.printStackTrace();
//        } catch (ClientException e) {
//            e.printStackTrace();
//        }
//    }


    public static void main(String[] args){

//        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "***", "***");
//        IAcsClient client = new DefaultAcsClient(profile);
//        CommonRequest request = new CommonRequest();
//        request.setSysMethod(MethodType.POST);
//        request.setSysDomain("dysmsapi.aliyuncs.com");
//        request.setSysVersion("2017-05-25");
//        request.setSysAction("SendSms");
//        request.putQueryParameter("RegionId", "cn-hangzhou");
//        request.putQueryParameter("PhoneNumbers", "15895965792");
//        request.putQueryParameter("SignName", "健康云小平台");
//        request.putQueryParameter("TemplateCode", "SMS_205883913");
//        request.putQueryParameter("TemplateParam", "{\"code\":\"2312\"}");
//        try {
//            CommonResponse response = client.getCommonResponse(request);
//            System.out.println(response.getData());
//
//        } catch (ServerException e) {
//            e.printStackTrace();
//        } catch (ClientException e) {
//            e.printStackTrace();
//        }
    }

    // 发送短信
    public void sendSms(String phone,String code){

        // code = "{\"code\":\"2312\"}";

        System.out.println("Sms开始发送短信1："+code);
        code = "{\"code\":\""+code+"\"}";

        System.out.println("Sms开始发送短信2："+code);

        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "***", "***");
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", "健康云小平台");
        request.putQueryParameter("TemplateCode", "SMS_205883913");
        request.putQueryParameter("TemplateParam", code);
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());

        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }

    }

    // 产生随机码




}
