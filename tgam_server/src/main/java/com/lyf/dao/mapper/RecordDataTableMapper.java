package com.lyf.dao.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RecordDataTableMapper {

    // 动态创建表
    void createTable(@Param("newTableName")String tableName);

    // 动态删除表
    void deleteTable(@Param("tableName") String tableName);


}
