package com.iot.rashome.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iot.rashome.dao.SensorDataDao;
import com.iot.rashome.vo.SensorDataVO;

@Service
public class SensorDataService {
    
    @Autowired
    private SensorDataDao sensorDataDao;

    public SensorDataVO insert(SensorDataVO sensorDataVO){
        return sensorDataDao.save(sensorDataVO);
    }

    public SensorDataVO getLatestRecordBySensorID(Long sensorID){
        return sensorDataDao.findFirstBySensorIdOrderByCollectedDateDesc(sensorID);
    }

    public SensorDataVO getLatestRecordByCollectedBy(Long createBy){
        return sensorDataDao.findFirstByCollectedByOrderByCollectedDateDesc(createBy);
    }

    public List<SensorDataVO> getRecordBetweenDate(Date leftBorderDate, Date rightBorderDate){
        return sensorDataDao.findByCollectedDateBetween(leftBorderDate, rightBorderDate);
    }
}
