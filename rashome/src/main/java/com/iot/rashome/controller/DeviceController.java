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
import com.iot.rashome.service.DeviceInformationService;
import com.iot.rashome.service.DeviceService;
import com.iot.rashome.vo.DeviceInformationVO;
import com.iot.rashome.vo.DeviceVO;

@RestController
@RequestMapping("/device")
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private DeviceInformationService deviceInformationService;

    private void getRegistDeviceDTOClean(RegistDeviceDTO registDeviceDTO){

        registDeviceDTO.setDeviceName(StringUtils.trimToNull(registDeviceDTO.getDeviceName()));
        registDeviceDTO.setDeviceNickname(StringUtils.trimToNull(registDeviceDTO.getDeviceNickname()));
    }

    @GetMapping("/")
    public List<DeviceVO> getDevices(){
        return deviceService.getAllDevices();
    }

    @PostMapping("/")
    public Long registDevice(@RequestBody RegistDeviceDTO registDeviceDTO) {

        if (ObjectUtils.isEmpty(registDeviceDTO)) {
            throw IotBackendException.nullParameters("注册信息 DTO");
        }

        getRegistDeviceDTOClean(registDeviceDTO);

        if (StringUtils.isBlank(registDeviceDTO.getDeviceNickname()) || StringUtils.isBlank(registDeviceDTO.getDeviceName())) {
            throw IotBackendException.nullParameters("设备别名", "设备信息");
        }

            
        // 检查设备信息是否注册
        DeviceInformationVO deviceInformationVO = deviceInformationService.findByDeviceInformationVO(registDeviceDTO.getDeviceName());
        if (ObjectUtils.isEmpty(deviceInformationVO)) {
            // 设备信息未注册
            throw new IotBackendException("设备信息没有注册");
        }
        
        // 设备信息已经注册过, 检查设备是否已经添加
        DeviceVO deviceVO = deviceService.findDeviceVOByDeviceNickname(registDeviceDTO.getDeviceNickname());
        if (ObjectUtils.isNotEmpty(deviceVO)) {
            
            // 如果设备信息一致, 则更新状态
            if (StringUtils.equals(deviceVO.getDeviceInformationVO().getDeviceName(), registDeviceDTO.getDeviceName())) {

                deviceVO.setStatus(DeviceStatus.ONLINE.name());
                DeviceVO updatedDeviceVO = deviceService.createDevice(deviceVO);
                return updatedDeviceVO.getId();

            } else {
                // 设备信息不一致, 说明设备重名了
                throw new IotBackendException("设备别名重复");
            }
        } else {

            // 没有找到设备, 说明是新设备, 添加新设备并返回设备 id
            DeviceVO newDeviceVO = new DeviceVO();
            newDeviceVO.setDeviceNickname(registDeviceDTO.getDeviceNickname());
            newDeviceVO.setStatus(DeviceStatus.ONLINE.name());
            newDeviceVO.setDeviceInformationVO(deviceInformationVO);

            return deviceService.createDevice(newDeviceVO).getId();
        }
    }

}
