package com.iot.rashome.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iot.rashome.dao.DeviceDao;

@Service
public class DeviceService {
    
    @Autowired
    private DeviceDao deviceDao;

    public boolean isDeviceExist(Long id){
        return deviceDao.findById(id).isPresent();
    }
}
