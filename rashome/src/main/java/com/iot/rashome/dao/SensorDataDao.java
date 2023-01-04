package com.iot.rashome.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.iot.rashome.vo.SensorDataVO;

@Repository
public interface SensorDataDao extends CrudRepository<SensorDataVO,Long> {
    
    SensorDataVO findFirstByCollectedByOrderByCollectedDateDesc(Long createdBy);

    SensorDataVO findFirstBySensorIdOrderByCollectedDateDesc(Long sensorID);

    List<SensorDataVO> findByCollectedDateBetween(Date leftBorderDate, Date rightBorderDate);
}
