package com.iot.rashome.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iot.rashome.dao.SensorDataDao;
import com.iot.rashome.vo.DeviceDataVO;

@Service
public class SensorDataService {
    
    @Autowired
    private SensorDataDao sensorDataDao;

    public DeviceDataVO insert(DeviceDataVO sensorDataVO){
        return sensorDataDao.save(sensorDataVO);
    }

    public DeviceDataVO getLatestRecordBySensorID(Long sensorID){
        return sensorDataDao.findFirstBySensorIdOrderByCollectedDateDesc(sensorID);
    }

    public DeviceDataVO getLatestRecordByCollectedBy(Long createBy){
        return sensorDataDao.findFirstByCollectedByOrderByCollectedDateDesc(createBy);
    }

    public List<DeviceDataVO> getRecordBetweenDate(Date leftBorderDate, Date rightBorderDate){
        return sensorDataDao.findByCollectedDateBetween(leftBorderDate, rightBorderDate);
    }
}
