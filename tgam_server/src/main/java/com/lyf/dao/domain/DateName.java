package com.lyf.dao.domain;

import java.sql.Timestamp;

public class DateName {
    private Timestamp timestamp;
    private String userName;

    public DateName() {
    }

    public DateName(Timestamp timestamp, String userName) {
        this.timestamp = timestamp;
        this.userName = userName;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "DateName{" +
                "timestamp=" + timestamp +
                ", userName='" + userName + '\'' +
                '}';
    }
}
