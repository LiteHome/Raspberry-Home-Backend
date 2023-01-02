package com.iot.rashome.controller;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iot.rashome.commons.exception.DeviceIsNotRegistException;
import com.iot.rashome.commons.exception.EmptyParameterException;
import com.iot.rashome.service.DeviceService;
import com.iot.rashome.service.SensorDataService;
import com.iot.rashome.service.TopologyService;
import com.iot.rashome.vo.SensorDataVO;

@RestController
@RequestMapping("/sensordata")
public class SensorDataController {

    @Autowired
    private SensorDataService sensorDataService;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private TopologyService topologyService;

    private boolean isAllDeviceExist(SensorDataVO sensorDataVO){
        if (BooleanUtils.isFalse(deviceService.isDeviceExist(sensorDataVO.getCollectedBy()))) {
            throw new DeviceIsNotRegistException(String.format("Device is not Registed, Device Id is %d", sensorDataVO.getCollectedBy()));
        } else if (BooleanUtils.isFalse(deviceService.isDeviceExist(sensorDataVO.getSensorId()))) {
            throw new DeviceIsNotRegistException(String.format("Device is not Registed, Device Id is %d", sensorDataVO.getSensorId()));
        } else {
            return true;
        }
    }

    @PostMapping("/")
    public void addSensorData(@RequestBody SensorDataVO sensorDataVO) {

        if (ObjectUtils.isNotEmpty(sensorDataVO)) {
            if (ObjectUtils.isNotEmpty(sensorDataVO.getCollectedBy()) && ObjectUtils.isNotEmpty(sensorDataVO.getSensorId())) {
                if (BooleanUtils.isTrue(isAllDeviceExist(sensorDataVO))) {
                    sensorDataService.insert(sensorDataVO);
                    topologyService.updateTopology(sensorDataVO.getCollectedBy(), sensorDataVO.getSensorId());
                }
            } else {
                throw new EmptyParameterException("Got Empty CreatedBy or/and SensorId");
            }
        } else {
            throw new EmptyParameterException("Got Empty SensorDataVO");
        }
    }

}
