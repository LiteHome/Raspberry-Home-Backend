package com.iot.rashome.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.iot.rashome.vo.DeviceDataVO;

@Repository
public interface DeviceDataDao extends CrudRepository<DeviceDataVO,Long> {
    DeviceDataVO findFirstByDeviceIdOrderByCollectedDateDesc(Long deviceId);
}
