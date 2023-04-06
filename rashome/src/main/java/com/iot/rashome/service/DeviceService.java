package com.iot.rashome.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import com.iot.rashome.commons.enums.DeviceStatus;
import com.iot.rashome.dao.DeviceDao;
import com.iot.rashome.vo.DeviceVO;

@Service
public class DeviceService {
    
    @Autowired
    private DeviceDao deviceDao;

    public DeviceVO offlineDevice(DeviceVO deviceVO) {

        deviceVO.setStatus(DeviceStatus.OFFLINE.name());
        return deviceDao.save(deviceVO);
    }

    public DeviceVO checkIfDeviceRegist(Long deviceID) {

        Optional<DeviceVO> deviceOptional = deviceDao.findById(deviceID);

        if (deviceOptional.isPresent()) {
            return deviceOptional.get();
        } else {
            return null;
        }
    }

    public DeviceVO checkIfDeviceRegist(String deviceName) {

        Optional<DeviceVO> deviceVO = deviceDao.findByDeviceName(deviceName);

        if (deviceVO.isPresent()) {
            return deviceVO.get();
        } else {
            return null;
        }
    }

    public DeviceVO finDeviceVOByDeviceId(Long deviceId){
        Optional<DeviceVO> optionDeviceVO = deviceDao.findById(deviceId);

        return optionDeviceVO.isPresent() ? optionDeviceVO.get() : null;
    }

    public DeviceVO updateDeviceVO(DeviceVO deviceVO){
        return deviceDao.save(deviceVO);
    }

    public List<DeviceVO> updateDeviceVO(List<DeviceVO> deviceVOList){
        return Streamable.of(deviceDao.saveAll(deviceVOList)).toList();
    }

    public DeviceVO saveDevice(DeviceVO deviceVO){
        return deviceDao.save(deviceVO);
    }

    public List<DeviceVO> getAllDevices(){
        return Streamable.of(deviceDao.findAll()).toList();
    }

    public List<DeviceVO> getAllOnlineDevices(){
        return deviceDao.findByStatus(DeviceStatus.ONLINE.name());
    }
}
