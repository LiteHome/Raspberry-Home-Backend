package com.iot.rashome.service;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
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

    public DeviceVO findDeviceVOByDeviceNickname(String deviceNickname){
        return deviceDao.findFirstByDeviceNickname(deviceNickname);
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

    public Pair<Boolean, DeviceVO> IsDeviceRegistByDeviceNickname(String deviceNickname){

        DeviceVO deviceVO = findDeviceVOByDeviceNickname(deviceNickname);

        return new MutablePair<Boolean, DeviceVO>(ObjectUtils.isNotEmpty(deviceVO), deviceVO);
    }

    public DeviceVO createDevice(DeviceVO deviceVO){
        return deviceDao.save(deviceVO);
    }

    public List<DeviceVO> getAllDevices(){
        return Streamable.of(deviceDao.findAll()).toList();
    }

    public List<DeviceVO> getAllOnlineDevices(){
        return deviceDao.findByStatus(DeviceStatus.ONLINE.name());
    }
}
