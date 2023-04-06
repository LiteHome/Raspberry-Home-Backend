package com.iot.rashome.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.iot.rashome.vo.DeviceVO;

@Repository
public interface DeviceDao extends CrudRepository<DeviceVO, Long> {

    List<DeviceVO> findByStatus(String status);

    Optional<DeviceVO> findByDeviceName(String deviceName);
}
