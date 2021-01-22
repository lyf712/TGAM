package com.lyf.controller.vomain;

import java.sql.Timestamp;
import java.util.Arrays;

public class DataVo {
    private Integer attention;
    private Integer meditation ;
    private Float[] rawEeg; //Integer
    private Timestamp timestamp;

    public DataVo() {
    }

    public DataVo(Integer attention, Integer meditation, Float[] rawEeg, Timestamp timestamp) {
        this.attention = attention;
        this.meditation = meditation;
        this.rawEeg = rawEeg;
        this.timestamp = timestamp;
    }

    public Integer getAttention() {
        return attention;
    }

    public void setAttention(Integer attention) {
        this.attention = attention;
    }

    public Integer getMeditation() {
        return meditation;
    }

    public void setMeditation(Integer meditation) {
        this.meditation = meditation;
    }

    public Float[] getRawEeg() {
        return rawEeg;
    }

    public void setRawEeg(Float[] rawEeg) {
        this.rawEeg = rawEeg;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "DataVo{" +
                "attention=" + attention +
                ", meditation=" + meditation +
                ", rawEeg=" + Arrays.toString(rawEeg) +
                ", timestamp=" + timestamp +
                '}';
    }
}
