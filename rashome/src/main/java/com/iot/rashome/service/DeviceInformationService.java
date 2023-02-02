package com.iot.rashome.service;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iot.rashome.dao.DeviceInformationDao;
import com.iot.rashome.vo.DeviceInformationVO;

@Service
public class DeviceInformationService {
    
    @Autowired
    private DeviceInformationDao deviceInformationDao;

    public DeviceInformationVO findByDeviceInformationVO(String deviceName) {
        return deviceInformationDao.findFirstByDeviceName(deviceName);
    }

    public Pair<Boolean, DeviceInformationVO> IsDeviceInformationRegist(String deviceName){
        
        DeviceInformationVO deviceInformationVO = findByDeviceInformationVO(deviceName);

        return new MutablePair<Boolean,DeviceInformationVO>(ObjectUtils.isNotEmpty(deviceInformationVO), deviceInformationVO);
    }

    public DeviceInformationVO createDeviceInformation(DeviceInformationVO deviceInformationVO) {
        return deviceInformationDao.save(deviceInformationVO);
    }
}
