package com.iot.rashome.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.iot.rashome.vo.SensorEnviromentVO;

@Repository
public interface SensorEnviromentDao extends CrudRepository<SensorEnviromentVO,Long> {
    
    SensorEnviromentVO findFirstBySampledByOrderBySampledDate(String sampledBy);
}
