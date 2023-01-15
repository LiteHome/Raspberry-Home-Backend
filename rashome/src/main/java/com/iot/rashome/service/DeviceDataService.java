package com.iot.rashome.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iot.rashome.dao.DeviceDataDao;
import com.iot.rashome.vo.DeviceDataVO;

@Service
public class DeviceDataService {
    
    @Autowired
    private DeviceDataDao deviceDataDao;

    public DeviceDataVO insert(DeviceDataVO sensorDataVO){
        return deviceDataDao.save(sensorDataVO);
    }

    public DeviceDataVO getLatestSensorData(Long sensorId){
        return deviceDataDao.findFirstBySensorIdOrderByCollectedDateDesc(sensorId);
    }

    public DeviceDataVO getLatestMetrics(Long masterId){
        return deviceDataDao.findFirstByMasterIdAndSensorIdOrderByCollectedDateDesc(masterId, masterId);
    }
}