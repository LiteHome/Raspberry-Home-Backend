package com.iot.rashome.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.iot.rashome.vo.DeviceDataVO;

@Repository
public interface SensorDataDao extends CrudRepository<DeviceDataVO,Long> {
    
    DeviceDataVO findFirstByCollectedByOrderByCollectedDateDesc(Long createdBy);

    DeviceDataVO findFirstBySensorIdOrderByCollectedDateDesc(Long sensorID);

    List<DeviceDataVO> findByCollectedDateBetween(Date leftBorderDate, Date rightBorderDate);
}
