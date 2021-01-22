package com.lyf.service.impl;

import com.lyf.controller.vomain.DataInfoVO;
import com.lyf.controller.vomain.DataVo;
import com.lyf.dao.domain.RecordData;
import com.lyf.dao.domain.RecordInfo;
import com.lyf.dao.mapper.RecordDataMapper;
import com.lyf.dao.mapper.RecordInfoMapper;
import com.lyf.service.QueryRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


@Service
public class QueryRecordServiceImpl implements QueryRecordService {


    @Autowired
    RecordInfoMapper recordInfoMapper;

    @Autowired
    RecordDataMapper recordDataMapper;

    @Override
    public List<DataInfoVO> getDataInfo(String infoId) {

        System.out.println("业务层获取dataInfo"+infoId);

        List<RecordInfo> list = new ArrayList<>();

        list = recordInfoMapper.getRecordInfoByDate(infoId+"%");//模糊查询infoID

        List<DataInfoVO> list1 = new ArrayList<>();

        for(RecordInfo recordInfo:list){
            DataInfoVO dataInfoVO = new DataInfoVO();
            dataInfoVO.setPattern("驾车");
            dataInfoVO.setStartTime(recordInfo.getStartTime());
            dataInfoVO.setEndTime(recordInfo.getEndTime());
            list1.add(dataInfoVO);
        }

        return list1;
    }


    @Override
    public List<DataVo> getRecordData(String infoId) {

        System.out.println("业务层获取dataData"+infoId);

        List<RecordData> list = new ArrayList<>();

        list = recordDataMapper.getRecordByDate(infoId+"%");//模糊查询infoID

        List<DataVo> list1 = new ArrayList<>();

        for(RecordData recordData :list){
           DataVo dataVo = new DataVo();
            Random random = new Random();
            dataVo.setAttention(20+random.nextInt(30));
            dataVo.setMeditation(40+random.nextInt(40));

            Float[]egg = new Float[8];
            Arrays.fill(egg,0F);

            egg[0] = recordData.getChannel1();
            egg[1] = recordData.getChannel2();
            egg[2] = recordData.getChannel3();
            egg[3] = recordData.getChannel4();
            egg[4] = recordData.getChannel5();
            egg[5] = recordData.getChannel6();
            egg[6] = recordData.getChannel7();
            egg[7] = recordData.getChannel8();
            dataVo.setRawEeg(egg);

            dataVo.setTimestamp(recordData.getTimestamp());
            list1.add(dataVo);
        }
        System.out.println("打印数据"+list1);
        return list1;
    }




}
