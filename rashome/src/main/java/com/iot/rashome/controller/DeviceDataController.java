package com.iot.rashome.controller;

import java.io.UnsupportedEncodingException;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iot.rashome.commons.enums.DeviceStatus;
import com.iot.rashome.commons.exception.IotBackendException;
import com.iot.rashome.service.DeviceDataService;
import com.iot.rashome.service.DeviceService;
import com.iot.rashome.vo.DeviceDataVO;
import com.iot.rashome.vo.DeviceVO;

/**
 * 设备数据增加, 查询接口
 */
@RestController
@RequestMapping("/data")
public class DeviceDataController {

    @Autowired
    private DeviceDataService deviceDataService;

    @Autowired
    private DeviceService deviceService;

    /**
     * 校验设备是否注册, 将设备数据插入数据库
     * @param deviceDataVO 设备数据 VO
     * @throws IotBackendException
     */
    @PostMapping("/")
    public void addDeviceData(@RequestBody DeviceDataVO deviceDataVO) throws IotBackendException {

        // 参数检查
        if (ObjectUtils.isEmpty(deviceDataVO) || ObjectUtils.isEmpty(deviceDataVO.getDeviceId())){
            throw IotBackendException.nullParameters("设备数据 VO", "设备 ID");
        }
        
        // 校验设备是否注册, 如果注册, 则 deviceVO 不为空
        DeviceVO deviceVO = deviceService.checkIfDeviceRegist(deviceDataVO.getDeviceId());
        if (ObjectUtils.isEmpty(deviceVO)) {
            throw IotBackendException.deviceIsNotRegist();
        }

        // 更新设备状态
        deviceVO.setStatus(DeviceStatus.ONLINE.name());
        deviceService.updateDeviceVO(deviceVO);

        // 设备数据插入数据库
        deviceDataService.insert(deviceDataVO);
    }

    /**
     * 根据设备名称查询设备数据. 设计保证设备名称全局唯一
     * @param deviceName 设备别名, UTF-8
     * @return 设备数据
     * @throws UnsupportedEncodingException
     */
    @GetMapping("/")
    public DeviceDataVO getDeviceData(@RequestHeader String deviceName) throws UnsupportedEncodingException, IotBackendException {

        // 参数检查
        if(StringUtils.isBlank(deviceName)){
            throw IotBackendException.nullParameters("设备别名");
        }

        // 检查设备是否注册以及是否在线
        DeviceVO deviceVO = deviceService.checkIfDeviceRegist(deviceName);
        if (ObjectUtils.isEmpty(deviceVO)) {
            throw IotBackendException.deviceIsNotRegist();
        } else if (deviceVO.getStatus().equals(DeviceStatus.OFFLINE.name())) {
            throw new IotBackendException("设备下线");
        }

        // 获取设备数据
        return deviceDataService.getLatestDeviceData(deviceVO.getId());
    }

}
