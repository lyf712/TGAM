package com.lyf.dao.mapper;

import com.lyf.dao.domain.RecordData;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecordDataMapper {


    // 添加记录 id(自增主键) userId, rec
    void insertOneRecord(RecordData recordData);

    Integer insertBatchRecordData(List<RecordData> list);

    List<RecordData> getRecordData(String infoId);//指定具体某次

    List<RecordData> getRecordByDate(String infoId);// 模糊查询这一天所有

}
