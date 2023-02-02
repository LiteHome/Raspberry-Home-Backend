package com.iot.rashome.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.iot.rashome.vo.DeviceInformationVO;

@Repository
public interface DeviceInformationDao extends CrudRepository<DeviceInformationVO, Long> {

    DeviceInformationVO findFirstByDeviceName(String deviceName);
}
