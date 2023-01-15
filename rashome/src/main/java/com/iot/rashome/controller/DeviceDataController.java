package com.iot.rashome.controller;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iot.rashome.commons.exception.DeviceIsNotRegistException;
import com.iot.rashome.commons.exception.EmptyParameterException;
import com.iot.rashome.commons.util.ParameterCheckUtil;
import com.iot.rashome.dto.TopologyData;
import com.iot.rashome.service.DeviceDataService;
import com.iot.rashome.service.DeviceService;
import com.iot.rashome.service.TopologyService;
import com.iot.rashome.vo.DeviceDataVO;

@RestController
@RequestMapping("/data")
public class DeviceDataController {

    @Autowired
    private DeviceDataService deviceDataService;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private TopologyService topologyService;

    private boolean isMasterAndSlaveRegist(DeviceDataVO deviceDataVO){
        return deviceService.isDeviceExist(deviceDataVO.getMasterId()) & deviceService.isDeviceExist(deviceDataVO.getSensorId());
    }

    private boolean isMasterIDAndSensorIDPresent(DeviceDataVO deviceDataVO){
        return ObjectUtils.isNotEmpty(deviceDataVO) &
               ObjectUtils.isNotEmpty(deviceDataVO.getMasterId()) &
               ObjectUtils.isNotEmpty(deviceDataVO.getSensorId());
    }

    private boolean isMasterRegist(Long masterId){
        return deviceService.isDeviceExist(masterId);
    }

    @PostMapping("/")
    public void addDeviceData(@RequestBody DeviceDataVO sensorDataVO) {

        if (isMasterIDAndSensorIDPresent(sensorDataVO)) {
            if (isMasterAndSlaveRegist(sensorDataVO)) {
                deviceDataService.insert(sensorDataVO);
                topologyService.updateTopology(sensorDataVO.getMasterId(), sensorDataVO.getSensorId());
            } else {
                throw new EmptyParameterException("Got Empty CreatedBy or/and SensorId");
            }
        } else {
            throw new EmptyParameterException("Got Empty SensorDataVO");
        }
    }

    @GetMapping("/")
    public TopologyData getDeviceData(@RequestHeader String masterId) {

        if (ParameterCheckUtil.isStringHeaderNumeric(masterId)) {
            long masterIdLong = Long.parseLong(masterId);

            if (isMasterRegist(masterIdLong)) {
                return topologyService.getTopologyWithDeviceData(masterIdLong);
            } else {
                throw new DeviceIsNotRegistException(String.format("Device is not Registed, ID is %d", masterIdLong));
            }
        } else {
            throw new EmptyParameterException(String.format("MasterID Parameter in Wrong Format, Master ID is %s", masterId));
        }
    }

}
