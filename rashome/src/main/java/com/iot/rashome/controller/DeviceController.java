package com.iot.rashome.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iot.rashome.commons.exception.DeviceIsNotRegistException;
import com.iot.rashome.dto.TopologyData;
import com.iot.rashome.service.TopologyService;

@RestController
@RequestMapping("/device")
public class DeviceController {

    @Autowired
    private TopologyService topologyService;

    @GetMapping("/")
    public TopologyData getDevice(@RequestHeader String masterID){
        
        if (StringUtils.isNumeric(masterID)) {
            return topologyService.getTopology(Long.parseLong(masterID));
        } else {
            throw new DeviceIsNotRegistException("Device is not regist");
        }
    }
}
