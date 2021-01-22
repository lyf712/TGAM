package com.lyf.dao.domain;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

public class Record implements Serializable {

    private Integer id;
    private String  userName;
    private String infoId;//自己进行拼接 userName+YY-MM-dd+Index
    private Integer index;
    private Timestamp timeStamp; //时间戳

    public Record() {
    }

    public Record(Integer id, String userName, String infoId, Integer index, Timestamp timeStamp) {
        this.id = id;
        this.userName = userName;
        this.infoId = infoId;
        this.index = index;
        this.timeStamp = timeStamp;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getInfoId() {
        return infoId;
    }

    public void setInfoId(String infoId) {
        this.infoId = infoId;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Timestamp getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Timestamp timeStamp) {
        this.timeStamp = timeStamp;
    }

    @Override
    public String toString() {
        return "Record{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", infoId='" + infoId + '\'' +
                ", index=" + index +
                ", timeStamp=" + timeStamp +
                '}';
    }
}
