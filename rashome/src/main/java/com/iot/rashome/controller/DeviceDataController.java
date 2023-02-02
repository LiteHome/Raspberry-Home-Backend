package com.iot.rashome.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

@RestController
@RequestMapping("/data")
public class DeviceDataController {

    @Autowired
    private DeviceDataService deviceDataService;

    @Autowired
    private DeviceService deviceService;

    private Logger logger = LoggerFactory.getLogger(DeviceDataController.class);

    @PostMapping("/")
    public void addDeviceData(@RequestBody DeviceDataVO deviceDataVO) {

        if (ObjectUtils.isEmpty(deviceDataVO) || ObjectUtils.isEmpty(deviceDataVO.getDeviceId())){
            throw IotBackendException.nullParameters("设备数据 VO", "设备 ID");
        }
            
        DeviceVO deviceVO = deviceService.finDeviceVOByDeviceId(deviceDataVO.getDeviceId());

        if (ObjectUtils.isEmpty(deviceVO)){
            throw new IotBackendException("Device is not registed");
        }

        deviceVO.setStatus(DeviceStatus.ONLINE.name());
        deviceService.updateDeviceVO(deviceVO);

        deviceDataService.insert(deviceDataVO);
    }

    @GetMapping("/")
    public DeviceDataVO getDeviceData(@RequestHeader String deviceNickname) throws UnsupportedEncodingException {

        if(StringUtils.isBlank(deviceNickname)){
            throw IotBackendException.nullParameters("设备别名");
        }

        DeviceVO deviceVO = deviceService.findDeviceVOByDeviceNickname(URLDecoder.decode(deviceNickname, "UTF-8"));

        if (ObjectUtils.isEmpty(deviceVO)){
            throw new IotBackendException("设备没有注册");
        }

        return deviceDataService.getLatestDeviceData(deviceVO.getId());
    }

}
