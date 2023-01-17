package com.iot.rashome.controller;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iot.rashome.commons.exception.DeviceIsNotRegistException;
import com.iot.rashome.commons.exception.EmptyParameterException;
import com.iot.rashome.dto.ReportDeviceDTO;
import com.iot.rashome.dto.TopologyDataDTO;
import com.iot.rashome.service.DeviceService;
import com.iot.rashome.service.TopologyService;
import com.iot.rashome.vo.DeviceVO;

@RestController
@RequestMapping("/device")
public class DeviceController {

    @Autowired
    private TopologyService topologyService;

    @Autowired
    private DeviceService deviceService;

    @GetMapping("/")
    public TopologyDataDTO getDevice(@RequestHeader String masterID){
        
        if (StringUtils.isNumeric(masterID)) {
            return topologyService.getTopology(Long.parseLong(masterID));
        } else {
            throw new DeviceIsNotRegistException("Device is not regist");
        }
    }

    @PostMapping("/name")
    public void updateDeviceNickname(@RequestBody ReportDeviceDTO reportDeviceDTO){
        
        if (ObjectUtils.isNotEmpty(reportDeviceDTO) && ObjectUtils.isNotEmpty(reportDeviceDTO.getDeviceId())) {
            DeviceVO deviceVO = deviceService.findDeviceVOById(reportDeviceDTO.getDeviceId());
            if (ObjectUtils.isNotEmpty(deviceVO) && StringUtils.isNotEmpty(reportDeviceDTO.getNickname())) {
                deviceVO.setDeviceNickname(reportDeviceDTO.getNickname());
            } else {
                throw new DeviceIsNotRegistException("Device is not regist");
            }
        } else {
            throw new EmptyParameterException(String.format("Empty Parameter %s", reportDeviceDTO));
        }
    }
}
