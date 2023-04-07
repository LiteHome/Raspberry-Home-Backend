package com.iot.rashome.controller;

import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iot.rashome.commons.enums.DeviceStatus;
import com.iot.rashome.commons.exception.IotBackendException;
import com.iot.rashome.dto.RegistDeviceDTO;
import com.iot.rashome.service.DeviceService;
import com.iot.rashome.vo.DeviceVO;

/**
 * 设备接口
 */
@RestController
@RequestMapping("/device")
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    /**
     * 参数校验并清理 RegistDeviceDTO 中字符串的空格
     * @param registDeviceDTO 注册设备 VO
     * @throws IotBackendException
     */
    private void checkAndTrimRegistDeviceDTO(RegistDeviceDTO registDeviceDTO) throws IotBackendException{

        // 清理空格
        String deviceNameString = StringUtils.trimToEmpty(registDeviceDTO.getDeviceName());
        String deviceInformatioString = StringUtils.trimToEmpty(registDeviceDTO.getDeviceInformation());

        // 校验参数是否为空
        if (StringUtils.isNoneEmpty(deviceNameString, deviceInformatioString)) {

            registDeviceDTO.setDeviceInformation(deviceInformatioString);
            registDeviceDTO.setDeviceName(deviceNameString);
        } else {
            throw IotBackendException.nullParameters("DeviceName", "DeviceInformation");
        }
    }

    /**
     * 根据前端传参赋值 deviceVO
     * @param registDeviceDTO 前端传参
     * @param deviceVO
     * @return
     */
    private DeviceVO setDeviceVOFromRegistDeviceDTO(RegistDeviceDTO registDeviceDTO, DeviceVO deviceVO) {

        // 赋值
        deviceVO.setDeviceInformation(registDeviceDTO.getDeviceInformation());
        deviceVO.setDeviceName(registDeviceDTO.getDeviceName());
        deviceVO.setStatus(DeviceStatus.OFFLINE.name());
        // health check rate 可以为空, 有默认值
        if (StringUtils.isNoneBlank(registDeviceDTO.getHealthCheckRate())) {
            deviceVO.setHealthCheckRate(registDeviceDTO.getHealthCheckRate());
        }
        // health check url 可以为空, 有默认值
        if (StringUtils.isNoneBlank(registDeviceDTO.getHealthCheckUrl())) {
            deviceVO.setHealthCheckUrl(registDeviceDTO.getHealthCheckUrl());
        }

        return deviceVO;
    }

    /**
     * 查询全部设备
     * @return List<DeviceVO> 全部设备VO
     */
    @GetMapping("/")
    public List<DeviceVO> getDevices(){
        return deviceService.getAllDevices();
    }

    /**
     * 1. 校验设备注册, 是否重名, 控制设备别名是全局唯一
     * 2. 然后注册设备并返回设备 ID. 
     * @param registDeviceDTO 注册设备 DTO
     * @return 设备 ID
     * @throws IotBackendException
     */
    @PostMapping("/")
    public Long registDevice(@RequestBody RegistDeviceDTO registDeviceDTO) throws IotBackendException {

        // 参数检查
        checkAndTrimRegistDeviceDTO(registDeviceDTO);
        
        // 检查设备是否注册
        DeviceVO deviceVO = deviceService.checkIfDeviceRegist(registDeviceDTO.getDeviceName());

        // 设备未注册, 注册设备并返回设备 ID
        if (ObjectUtils.isEmpty(deviceVO)) {
            DeviceVO newDeviceVO = setDeviceVOFromRegistDeviceDTO(registDeviceDTO, new DeviceVO());
            return deviceService.saveDevice(newDeviceVO).getId();
        } else {
            // 设备已注册, 更新 Device VO
            DeviceVO newDeviceVO = setDeviceVOFromRegistDeviceDTO(registDeviceDTO, deviceVO);
            newDeviceVO.setId(deviceVO.getId());
            return deviceService.updateDeviceVO(newDeviceVO).getId();
        }

    }

}
