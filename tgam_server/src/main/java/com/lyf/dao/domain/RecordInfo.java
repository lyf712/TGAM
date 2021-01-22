package com.lyf.dao.domain;

import java.io.Serializable;
import java.sql.Time;
import java.sql.Timestamp;

public class RecordInfo implements Serializable {
    private Integer id;
    private String infoId;
    private Timestamp startTime;
    private Timestamp endTime;
    private Long totalTime;// 单位 s
    private String model;
    private String device;

    public RecordInfo() {
    }

    public RecordInfo(Integer id, String infoId, Timestamp startTime, Timestamp endTime, Long totalTime, String model, String device) {
        this.id = id;
        this.infoId = infoId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.totalTime = totalTime;
        this.model = model;
        this.device = device;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getInfoId() {
        return infoId;
    }

    public void setInfoId(String infoId) {
        this.infoId = infoId;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public Long getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(Long totalTime) {
        this.totalTime = totalTime;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    @Override
    public String toString() {
        return "RecordInfo{" +
                "id=" + id +
                ", infoId='" + infoId + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", totalTime=" + totalTime +
                ", model='" + model + '\'' +
                ", device='" + device + '\'' +
                '}';
    }
}
