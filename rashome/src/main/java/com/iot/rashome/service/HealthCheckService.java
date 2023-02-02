package com.iot.rashome.service;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import com.iot.rashome.commons.enums.DeviceStatus;
import com.iot.rashome.commons.exception.IotBackendException;
import com.iot.rashome.commons.util.HttpUtil;
import com.iot.rashome.vo.DeviceVO;

@Service
public class HealthCheckService {

    @Autowired
    private DeviceService deviceService;

    private Logger logger = LoggerFactory.getLogger(HealthCheckService.class);
    
    @Scheduled(fixedRate = 1000 * 60)
    public void healthCheck() {

        List<DeviceVO> onlineDeviceVOList = deviceService.getAllOnlineDevices();


        if (CollectionUtils.isNotEmpty(onlineDeviceVOList)){

            for (DeviceVO onlineDeviceVO : onlineDeviceVOList) {
                
                try {
                    HttpUtil.postJsonPayload(onlineDeviceVO.getHealthCheckUrl(), onlineDeviceVO.getDeviceNickname());
                } catch (IotBackendException | IllegalArgumentException e) {
                    onlineDeviceVO.setStatus(DeviceStatus.OFFLINE.name());
                    logger.info(String.format("%s 下线", onlineDeviceVO.getDeviceNickname()), new IotBackendException(e));
                } catch (RestClientException e) {
                    logger.error("网络异常", new IotBackendException(e));
                    return;
                } finally {
                    deviceService.updateDeviceVO(onlineDeviceVOList);
                }
            }
        }

    }
}
