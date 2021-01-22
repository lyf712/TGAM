package com.lyf.controller.vomain;

import java.sql.Timestamp;

public class DataInfoVO {

    private Timestamp startTime;
    private Timestamp endTime;
    private String pattern;

    public DataInfoVO() {
    }

    public DataInfoVO(Timestamp startTime, Timestamp endTime, String pattern) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.pattern = pattern;
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

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public String toString() {
        return "DataInfoVO{" +
                "startTime=" + startTime +
                ", endTime=" + endTime +
                ", pattern='" + pattern + '\'' +
                '}';
    }
}
