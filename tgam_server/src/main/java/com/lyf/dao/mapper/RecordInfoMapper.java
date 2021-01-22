package com.lyf.dao.mapper;

import com.lyf.dao.domain.DateName;
import com.lyf.dao.domain.RecordInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecordInfoMapper {

    boolean insertRecordInfo(RecordInfo recordInfo);
    List<RecordInfo> getRecordInfo(String InfoId);


    List<RecordInfo> getRecordInfoByDate(String infoId);//String date,String userName


}
