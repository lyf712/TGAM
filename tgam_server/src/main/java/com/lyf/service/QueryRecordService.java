package com.lyf.service;

import com.lyf.controller.vomain.DataInfoVO;
import com.lyf.controller.vomain.DataVo;

import java.util.List;

public interface QueryRecordService {

    List<DataInfoVO> getDataInfo(String infoId);

    List<DataVo> getRecordData(String infoId);
}
