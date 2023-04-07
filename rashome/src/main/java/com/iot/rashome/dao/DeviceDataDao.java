package com.iot.rashome.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.iot.rashome.vo.DeviceDataVO;

@Repository
public interface DeviceDataDao extends CrudRepository<DeviceDataVO,Long> {
    
    @Transactional(
        readOnly = true,
        timeout = 5
    )
    DeviceDataVO findFirstByDeviceIdOrderByCollectedDateDesc(Long deviceId);
}
