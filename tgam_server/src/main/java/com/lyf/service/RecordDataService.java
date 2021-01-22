package com.lyf.service;

import com.lyf.dao.domain.Record;
import com.lyf.dao.domain.RecordData;
import com.lyf.dao.domain.RecordInfo;

import java.util.List;

public interface RecordDataService {

  boolean addData(String flag, String date, String userId,String data); //flag:标志位 // List<Float>


  List<RecordInfo> getRecordInfo(String infoId);
  List<RecordData> getRecordData(String infoId);




}
