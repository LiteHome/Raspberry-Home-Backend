package com.iot.rashome.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.iot.rashome.commons.enums.DeviceStatus;
import com.iot.rashome.dao.DeviceDao;
import com.iot.rashome.vo.DeviceVO;

/**
 * 设备 Service
 */
@Service
public class DeviceService {
    
    @Autowired
    private DeviceDao deviceDao;

    /**
     * 检查设备是否注册
     * @param deviceID 设备主键 ID
     * @return 注册则返回数据库数据, 否则返回 null
     */
    public DeviceVO checkIfDeviceRegistByDeviceId(Long deviceID) {

        Optional<DeviceVO> deviceOptional = deviceDao.findById(deviceID);

        if (deviceOptional.isPresent()) {
            return deviceOptional.get();
        } else {
            return null;
        }
    }

    /**
     * 检查设备是否注册
     * @param deviceUccid 设备 Uccid
     * @return 注册则返回数据库数据, 否则返回 null
     */
    public DeviceVO checkIfDeviceRegistByDeviceUccid(String deviceUccid) {

        Optional<DeviceVO> deviceOptional = deviceDao.findByDeviceUccid(deviceUccid);

        if (deviceOptional.isPresent()) {
            return deviceOptional.get();
        } else {
            return null;
        }
    }

    /**
     * 检查设备是否注册
     * @param deviceName 设备名称
     * @return 注册则返回数据库数据, 否则返回 null
     */
    public DeviceVO checkIfDeviceRegistByDeviceName(String deviceName) {

        Optional<DeviceVO> deviceVO = deviceDao.findByDeviceName(deviceName);

        if (deviceVO.isPresent()) {
            return deviceVO.get();
        } else {
            return null;
        }
    }

    /**
     * 更新单个设备
     * @param deviceVO
     * @return
     */
    @Transactional(
        propagation = Propagation.REQUIRES_NEW,
        isolation = Isolation.READ_COMMITTED,
        timeout = 5
    )
    public DeviceVO updateDeviceVO(DeviceVO deviceVO){
        return deviceDao.save(deviceVO);
    }

    /**
     * 批量更新设备
     * @param deviceVOList
     * @return
     */
    @Transactional(
        propagation = Propagation.REQUIRES_NEW,
        isolation = Isolation.READ_COMMITTED,
        timeout = 5
    )
    public List<DeviceVO> updateDeviceVO(List<DeviceVO> deviceVOList){
        return Streamable.of(deviceDao.saveAll(deviceVOList)).toList();
    }

    @Transactional(
        propagation = Propagation.REQUIRES_NEW,
        isolation = Isolation.READ_COMMITTED,
        timeout = 5
    )
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
