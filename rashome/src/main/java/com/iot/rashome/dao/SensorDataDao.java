package com.iot.rashome.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.iot.rashome.vo.SensorDataVO;

@Repository
public interface SensorDataDao extends CrudRepository<SensorDataVO,Long> {
    
    SensorDataVO findFirstByCollectedByOrderByCollectedDateDesc(Long createdBy);

    List<SensorDataVO> findByCollectedDateBetween(Date leftBorderDate, Date rightBorderDate);
}
