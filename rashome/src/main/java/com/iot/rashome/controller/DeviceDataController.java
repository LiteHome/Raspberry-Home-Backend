package com.iot.rashome.controller;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iot.rashome.commons.enums.DeviceStatus;
import com.iot.rashome.commons.exception.IotBackendException;
import com.iot.rashome.service.DeviceDataService;
import com.iot.rashome.service.DeviceService;
import com.iot.rashome.service.ImageService;
import com.iot.rashome.vo.DeviceDataVO;
import com.iot.rashome.vo.DeviceVO;

import lombok.extern.slf4j.Slf4j;

/**
 * 设备数据增加, 查询接口
 */
@RestController
@RequestMapping("/data")
@Slf4j
public class DeviceDataController {

    @Autowired
    private DeviceDataService deviceDataService;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private ImageService imageService;



    /**
     * 校验设备是否注册, 将设备数据插入数据库
     * @param deviceDataVO 设备数据 VO
     * @throws IotBackendException
     */
    @Transactional(
        propagation = Propagation.REQUIRED,
        isolation = Isolation.READ_COMMITTED,
        timeout = 10
    )
    @PostMapping("/")
    public void addDeviceData(@RequestBody DeviceDataVO deviceDataVO) throws IotBackendException, ParseException {

        // 参数检查
        if (ObjectUtils.anyNull(
            deviceDataVO, 
            deviceDataVO.getDeviceId(), 
            deviceDataVO.getCollectedDate())) {
            log.warn(String.format("收到的数据是 %s", deviceDataVO));
            throw IotBackendException.nullParameters("设备数据 VO", "设备 ID, 数据收集时间");
        }
        
        // 校验设备是否注册, 如果注册, 则 deviceVO 不为空
        DeviceVO deviceVO = deviceService.checkIfDeviceRegistByDeviceId(deviceDataVO.getDeviceId());
        if (ObjectUtils.isEmpty(deviceVO)) {
            throw IotBackendException.deviceIsNotRegist();
        }

        // 更新设备状态
        deviceVO.setStatus(DeviceStatus.ONLINE.name());
        deviceService.updateDeviceVO(deviceVO);
        log.info("更新设备状态 " + deviceVO.getId());

        // 如果图片数据不为空, 则进行图片处理
        if (StringUtils.isNotBlank(deviceDataVO.getCameraImageBase64())) {
            deviceDataVO.setCameraImageUrl(imageService.imageToUrl(deviceDataVO));
        }

        // 设备数据插入数据库
        deviceDataService.insert(deviceDataVO);
    }

    /**
     * 根据设备名称查询设备数据. 设计保证设备名称全局唯一
     * @param deviceName 设备名称, UTF-8
     * @return 设备数据
     * @throws UnsupportedEncodingException
     */
    @GetMapping("/")
    public DeviceDataVO getDeviceData(@RequestParam String deviceName) throws UnsupportedEncodingException, IotBackendException {

        // 参数检查
        if(StringUtils.isBlank(deviceName)){
            throw IotBackendException.nullParameters("设备名称");
        }

        // 根据设备名称查找设备
        DeviceVO deviceVO = deviceService.checkIfDeviceRegistByDeviceName(deviceName);

        if (ObjectUtils.isEmpty(deviceVO)) {
            // 找不到设备说明没有注册
            throw IotBackendException.deviceIsNotRegist();
        } else if (deviceVO.getStatus().equals(DeviceStatus.OFFLINE.name())) {
            // 设备已下线
            throw IotBackendException.parametersInMessage("%s 下线", deviceName);
        }

        // 获取设备数据
        return deviceDataService.getLatestDeviceData(deviceVO.getId());
    }

}
