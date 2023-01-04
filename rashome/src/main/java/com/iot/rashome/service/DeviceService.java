package com.iot.rashome.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iot.rashome.dao.DeviceDao;
import com.iot.rashome.vo.DeviceVO;

@Service
public class DeviceService {
    
    @Autowired
    private DeviceDao deviceDao;

    public boolean isDeviceExist(Long id){
        return deviceDao.findById(id).isPresent();
    }

    public DeviceVO findDeviceVOById(Long id){

        Optional<DeviceVO> deviceVO = deviceDao.findById(id);

        return deviceVO.isPresent() ? deviceVO.get() : null;
    }
}
