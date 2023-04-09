package com.iot.rashome.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iot.rashome.commons.enums.DeviceStatus;
import com.iot.rashome.commons.enums.ResultCode;
import com.iot.rashome.commons.exception.IotBackendException;
import com.iot.rashome.dto.ResultDTO;
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


    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * 参数校验并清理 RegistDeviceDTO 中字符串的空格
     * @param deviceVOList 注册设备 VO
     * @throws IotBackendException
     */
    private void checkAndTrimRegistDeviceDTO(List<DeviceVO> deviceVOList) throws IotBackendException{

        for (DeviceVO deviceVO : deviceVOList) {

            // 清理空格
            String deviceNameString = StringUtils.trimToEmpty(deviceVO.getDeviceName());
            String deviceInformatioString = StringUtils.trimToEmpty(deviceVO.getDeviceInformation());

            // 校验参数是否为空
            if (StringUtils.isNoneEmpty(deviceNameString, deviceInformatioString)) {

                deviceVO.setDeviceInformation(deviceInformatioString);
                deviceVO.setDeviceName(deviceNameString);
            } else {
                throw IotBackendException.nullParameters("DeviceName", "DeviceInformation");
            }
        }
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
     * 3. 重名设备, 如果旧设备已经下线, 则直接覆盖. 否则该次请求失败
     * @param registDeviceDTO 注册设备 DTO
     * @return List<DeviceVO> JSON
     * @throws IotBackendException
     * @throws JsonProcessingException
     */
    @Transactional(
        propagation = Propagation.REQUIRED,
        isolation = Isolation.READ_COMMITTED,
        timeout = 100
    )
    @PostMapping("/")
    public ResultDTO registDevice(@RequestBody List<DeviceVO> deviceVOList) throws IotBackendException, JsonProcessingException {

        // 参数检查
        checkAndTrimRegistDeviceDTO(deviceVOList);

        for (DeviceVO deviceVO : deviceVOList) {

            // 检查设备是否注册
            DeviceVO databaseDeviceVO = deviceService.checkIfDeviceRegist(deviceVO.getDeviceName());
            if (ObjectUtils.isNotEmpty(databaseDeviceVO)) {
                if (databaseDeviceVO.getStatus().equals(DeviceStatus.OFFLINE.name())) {
                    // 设备已注册但是下线, 注入设备 ID, 覆盖原来的信息
                    deviceVO.setId(databaseDeviceVO.getId());
                } else {
                    // 设备已注册且上线, 不允许覆盖
                    return ResultDTO.fail(ResultCode.DUPLICATE_DEVICE_NAME.getCode(), String.format("重名设备 %s", deviceVO.getDeviceName()));
                }
            } 
        }

        // 没有注册的设备在这一步注册
        List<DeviceVO> updatedDeviceVOList = deviceService.updateDeviceVO(deviceVOList);;

        return ResultDTO.success(OBJECT_MAPPER.writeValueAsString(updatedDeviceVOList));
    }

}
