package com.iot.rashome.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.iot.rashome.dao.DeviceDataDao;
import com.iot.rashome.vo.DeviceDataVO;

@Service
public class DeviceDataService {
    
    @Autowired
    private DeviceDataDao deviceDataDao;

    /**
     * 插入数据
     * @param sensorDataVO
     * @return
     */
    @Transactional(
        propagation = Propagation.REQUIRES_NEW,
        isolation = Isolation.READ_COMMITTED,
        timeout = 5
    )
    public DeviceDataVO insert(DeviceDataVO sensorDataVO){
        return deviceDataDao.save(sensorDataVO);
    }

    /**
     * 获得日期最新的数据
     * @param deviceId
     * @return
     */
    @Transactional(
        readOnly = true,
        timeout = 5
    )
    public DeviceDataVO getLatestDeviceData(Long deviceId){
        return deviceDataDao.findFirstByDeviceIdOrderByCollectedDateDesc(deviceId);
    }
}
