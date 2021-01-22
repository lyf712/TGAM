package com.lyf.dao.domain;

import java.sql.Timestamp;

public class RecordData {

    private Integer id;
    private String  infoId;
    private String dataIndex;

    private Timestamp timestamp;
    private Float channel1;
    private Float channel2;
    private Float channel3;
    private Float channel4;
    private Float channel5;
    private Float channel6;
    private Float channel7;
    private Float channel8;

    public RecordData() {
    }

    public RecordData(Integer id, String infoId, String dataIndex, Timestamp timestamp, Float channel1, Float channel2, Float channel3, Float channel4, Float channel5, Float channel6, Float channel7, Float channel8) {
        this.id = id;
        this.infoId = infoId;
        this.dataIndex = dataIndex;
        this.timestamp = timestamp;
        this.channel1 = channel1;
        this.channel2 = channel2;
        this.channel3 = channel3;
        this.channel4 = channel4;
        this.channel5 = channel5;
        this.channel6 = channel6;
        this.channel7 = channel7;
        this.channel8 = channel8;
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

    public String getDataIndex() {
        return dataIndex;
    }

    public void setDataIndex(String dataIndex) {
        this.dataIndex = dataIndex;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public Float getChannel1() {
        return channel1;
    }

    public void setChannel1(Float channel1) {
        this.channel1 = channel1;
    }

    public Float getChannel2() {
        return channel2;
    }

    public void setChannel2(Float channel2) {
        this.channel2 = channel2;
    }

    public Float getChannel3() {
        return channel3;
    }

    public void setChannel3(Float channel3) {
        this.channel3 = channel3;
    }

    public Float getChannel4() {
        return channel4;
    }

    public void setChannel4(Float channel4) {
        this.channel4 = channel4;
    }

    public Float getChannel5() {
        return channel5;
    }

    public void setChannel5(Float channel5) {
        this.channel5 = channel5;
    }

    public Float getChannel6() {
        return channel6;
    }

    public void setChannel6(Float channel6) {
        this.channel6 = channel6;
    }

    public Float getChannel7() {
        return channel7;
    }

    public void setChannel7(Float channel7) {
        this.channel7 = channel7;
    }

    public Float getChannel8() {
        return channel8;
    }

    public void setChannel8(Float channel8) {
        this.channel8 = channel8;
    }

    @Override
    public String toString() {
        return "RecordData{" +
                "id=" + id +
                ", infoId='" + infoId + '\'' +
                ", dataIndex='" + dataIndex + '\'' +
                ", timestamp=" + timestamp +
                ", channel1=" + channel1 +
                ", channel2=" + channel2 +
                ", channel3=" + channel3 +
                ", channel4=" + channel4 +
                ", channel5=" + channel5 +
                ", channel6=" + channel6 +
                ", channel7=" + channel7 +
                ", channel8=" + channel8 +
                '}';
    }
}
