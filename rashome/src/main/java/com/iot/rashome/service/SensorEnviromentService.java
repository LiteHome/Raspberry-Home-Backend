package com.iot.rashome.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iot.rashome.base.BaseService;
import com.iot.rashome.dao.SensorEnviromentDao;
import com.iot.rashome.vo.SensorEnviromentVO;

@Service
public class SensorEnviromentService implements BaseService<SensorEnviromentVO> {
    
    @Autowired
    private SensorEnviromentDao sensorEnviromentDao;

    @Override
    public SensorEnviromentVO insert(SensorEnviromentVO sensorEnviromentVO){

        return sensorEnviromentDao.save(sensorEnviromentVO);
    }

    @Override
    public SensorEnviromentVO getLatestRecord(String sampledBy){
        return sensorEnviromentDao.findFirstBySampledByOrderBySampledDate(sampledBy);
    }

    @Override
    public List<SensorEnviromentVO> getRecordBetweenDate(Date leftBorderDate, Date rightBorderDate){
        return sensorEnviromentDao.findBySampledDateBetween(leftBorderDate, rightBorderDate);
    }
}