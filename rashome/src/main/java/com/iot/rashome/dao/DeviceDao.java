package com.iot.rashome.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.iot.rashome.vo.DeviceVO;

@Repository
public interface DeviceDao extends CrudRepository<DeviceVO, Long> {

    @Transactional(
        readOnly = true,
        timeout = 2
    )
    List<DeviceVO> findByStatus(String status);

    @Transactional(
        readOnly = true,
        timeout = 2
    )
    Optional<DeviceVO> findByDeviceName(String deviceName);

    @Transactional(
        readOnly = true,
        timeout = 2
    )
    Optional<DeviceVO> findByDeviceUccid(String deviceUccid);
}
