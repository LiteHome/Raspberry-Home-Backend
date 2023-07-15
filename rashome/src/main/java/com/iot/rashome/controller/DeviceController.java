package com.iot.rashome.controller;

import java.util.ArrayList;
import java.util.Collections;
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
    private void checkAndTrimRegistDeviceDTO(DeviceVO  ...deviceVOList) throws IotBackendException {

        for (DeviceVO deviceVO : deviceVOList) {

            // 清理空格
            String deviceInformatioString = StringUtils.trimToEmpty(deviceVO.getDeviceInformation());
            String deviceUuidString = StringUtils.trimToEmpty(deviceVO.getDeviceUuid());
            String deviceParentUuidString = StringUtils.trimToEmpty(deviceVO.getParentUuid());
            String deviceGatewayUuidString = StringUtils.trimToEmpty(deviceVO.getGatewayUuid());
            String deviceTagString = StringUtils.trimToEmpty(deviceVO.getDeviceTag());

            // 校验参数是否为空
            if (StringUtils.isNoneEmpty(deviceInformatioString, deviceUuidString, deviceParentUuidString, deviceGatewayUuidString)) {

                deviceVO.setDeviceInformation(deviceInformatioString);
                deviceVO.setDeviceUuid(deviceUuidString);
                deviceVO.setParentUuid(deviceParentUuidString);
                deviceVO.setGatewayUuid(deviceGatewayUuidString);
                deviceVO.setDeviceTag(deviceTagString);
            } else {
                throw IotBackendException.nullParameters("DeviceInformation, DeviceUuid, Device Parent Uuid, Gateway Uuid, Device tag");
            }
        }
    }

        /**
     * 参数校验并清理 RegistDeviceDTO 中字符串的空格
     * @param deviceVOList 注册设备 VO
     * @throws IotBackendException
     */
    private void checkAndTrimSetDevicesNameDeviceDTO(List<DeviceVO> deviceVOList) throws IotBackendException {

        for (DeviceVO deviceVO : deviceVOList) {
            // 清理空格
            String deviceUuidString = StringUtils.trimToEmpty(deviceVO.getDeviceUuid());
            String deviceName = StringUtils.trimToEmpty(deviceVO.getDeviceName());

            // 校验参数是否为空
            if (StringUtils.isNoneEmpty(deviceUuidString, deviceName)) {
                deviceVO.setDeviceUuid(deviceUuidString);
                deviceVO.setDeviceName(deviceName);
            } else {
                throw IotBackendException.nullParameters("DeviceUuid, DeviceName");
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
     * 1. 校验设备是否已经注册, 通过 uuid 保证唯一
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
        timeout = 10
    )
    @PostMapping("/")
    public ResultDTO registDevice(@RequestBody DeviceVO deviceVO) throws IotBackendException, JsonProcessingException {

        // 参数检查
        checkAndTrimRegistDeviceDTO(deviceVO);

        // 检查设备是否注册
        DeviceVO databaseDeviceVO = deviceService.checkIfDeviceRegistByDeviceUuid(deviceVO.getDeviceUuid());
        // 未注册则注册并返回
        if (ObjectUtils.isEmpty(databaseDeviceVO)) {
            return ResultDTO.success(OBJECT_MAPPER.writeValueAsString(deviceService.saveDevice(deviceVO)));
        }

        deviceVO.setId(databaseDeviceVO.getId());
        return ResultDTO.success(OBJECT_MAPPER.writeValueAsString(deviceService.updateDeviceVO(deviceVO)));
    }

    @PostMapping("/setDevicesName")
    public ResultDTO setDevicesName(@RequestBody DeviceVO deviceVO) throws IotBackendException {

        checkAndTrimSetDevicesNameDeviceDTO(Collections.singletonList(deviceVO));

        // 检查设备是否注册
        DeviceVO databaseDeviceVOByUuid = deviceService.checkIfDeviceRegistByDeviceUuid(deviceVO.getDeviceUuid());
        if (ObjectUtils.isEmpty(databaseDeviceVOByUuid)) {
            throw new IotBackendException("设备未注册, uuid 是 " + deviceVO.getDeviceUuid());
        }

        // 检查设备名称是否注册, 需要排除本设备, 即 uuid 相同的
        DeviceVO databaseDeviceVOByName = deviceService.checkIfDeviceRegistByDeviceName(deviceVO.getDeviceName());
        if (!ObjectUtils.isEmpty(databaseDeviceVOByName) && databaseDeviceVOByName.getDeviceUuid() != deviceVO.getDeviceUuid()) {
            throw new IotBackendException("设备名字重复, 名字是 " + deviceVO.getDeviceName());
        }

        databaseDeviceVOByUuid.setDeviceName(deviceVO.getDeviceName());
        deviceService.updateDeviceVO(databaseDeviceVOByUuid);
        return ResultDTO.success("成功");
    }

    @GetMapping("/getAllGateways")
    public List<DeviceVO> getAllGateways() throws IotBackendException {

        List<DeviceVO> result = new ArrayList<>(50);

        List<DeviceVO> allOnlineDevices = deviceService.getAllOnlineDevices();
        for (DeviceVO deviceVO : allOnlineDevices) {
            // 网关没有级联关系, 所以三个 uuid 一样. 网关没有传感器
            if (deviceVO.getDeviceUuid().equals(deviceVO.getParentUuid()) && deviceVO.getParentUuid().equals(deviceVO.getGatewayUuid())) {
                result.add(deviceVO);
            }
        }

        return result;
    }

    @GetMapping("/getAllBoards")
    public List<DeviceVO> getAllBoards() throws IotBackendException {

        List<DeviceVO> result = new ArrayList<>(50);

        List<DeviceVO> allOnlineDevices = deviceService.getAllOnlineDevices();
        for (DeviceVO deviceVO : allOnlineDevices) {
            // 设备可以关联多个传感器, 但是只能关联一个网关
            if (deviceVO.getDeviceUuid().equals(deviceVO.getParentUuid()) && !deviceVO.getParentUuid().equals(deviceVO.getGatewayUuid())) {
                result.add(deviceVO);
            }
        }

        return result;
    }

    @GetMapping("/getAllSensors")
    public List<DeviceVO> getAllSensors() throws IotBackendException {

        List<DeviceVO> result = new ArrayList<>(50);

        List<DeviceVO> allOnlineDevices = deviceService.getAllOnlineDevices();
        for (DeviceVO deviceVO : allOnlineDevices) {
            // 传感器只能关联一个设备和网关
            if (!deviceVO.getDeviceUuid().equals(deviceVO.getParentUuid()) && !deviceVO.getParentUuid().equals(deviceVO.getGatewayUuid())) {
                result.add(deviceVO);
            }
        }

        return result;
    }

}
