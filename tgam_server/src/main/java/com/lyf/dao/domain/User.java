package com.lyf.dao.domain;

import java.io.Serializable;
import java.sql.Date;

public class User implements Serializable {

    private Long id;
    //private Long userId;
    private String userName;//用户名登录
    private String password;
    private String realName;//真实姓名
    private Integer age;
    private String job;
    private Float height;
    private Float weight;
    private Date birthday;
    private String idNumber;
    private String headImg;

    public User() {
    }

    public User(Long id, String userName, String password, String realName, Integer age, String job, Float height, Float weight, Date birthday, String idNumber, String headImg) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.realName = realName;
        this.age = age;
        this.job = job;
        this.height = height;
        this.weight = weight;
        this.birthday = birthday;
        this.idNumber = idNumber;
        this.headImg = headImg;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String name) {
        this.realName = name;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
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
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", realName='" + realName + '\'' +
                ", job='" + job + '\'' +
                ", age=" + age +
                ", height=" + height +
                ", weight=" + weight +
                ", birthday=" + birthday +
                ", idNumber=" + idNumber +
                ", headImg='" + headImg + '\'' +
                '}';
    }
}
