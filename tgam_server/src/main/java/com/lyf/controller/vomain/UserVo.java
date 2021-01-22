package com.lyf.controller.vomain;

import java.sql.Date;

public class UserVo {
    private String realName;
    private Integer age;
    private String job;
    private Float height;
    private Float weight;
    private Date birthday;
    private String idNumber;
    private String headImg;

    public UserVo() {
    }

    public UserVo(String realName, Integer age, String job, Float height, Float weight, Date birthday, String idNumber, String headImg) {
        this.realName = realName;
        this.age = age;
        this.job = job;
        this.height = height;
        this.weight = weight;
        this.birthday = birthday;
        this.idNumber = idNumber;
        this.headImg = headImg;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public Float getHeight() {
        return height;
    }

    public void setHeight(Float height) {
        this.height = height;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    @Override
    public String toString() {
        return "UserVo{" +
                "realName='" + realName + '\'' +
                ", age=" + age +
                ", job='" + job + '\'' +
                ", height=" + height +
                ", weight=" + weight +
                ", birthday=" + birthday +
                ", idNumber='" + idNumber + '\'' +
                ", headImg='" + headImg + '\'' +
                '}';
    }
}
