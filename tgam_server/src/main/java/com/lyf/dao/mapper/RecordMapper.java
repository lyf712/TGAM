package com.lyf.dao.mapper;

import org.springframework.stereotype.Repository;
import com.lyf.dao.domain.Record;
import java.util.List;

@Repository
public interface RecordMapper {
    // 查询当天最大index,若需查询某天的就需要时间限制
    // date(字段) = curdate();

//    insert：   插入n条记录，返回影响行数n。（n>=1，n为0时实际为插入失败）
//
//    update：更新n条记录，返回影响行数n。（n>=0）
//
//    delete： 删除n条记录，返回影响行数n。（n>=0）
//    selectAll :List<T>  若selectOne为空则报错null


    int selectIndex(Integer userId);//

    int queryIndexByName(String userName);

    List<Record> selectAll(Integer userId);

    int insertRecord(Record record);


}
