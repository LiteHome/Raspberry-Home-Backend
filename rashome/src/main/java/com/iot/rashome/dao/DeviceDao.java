package com.iot.rashome.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.iot.rashome.vo.DeviceVO;

@Repository
public interface DeviceDao extends CrudRepository<DeviceVO, Long> {
}
