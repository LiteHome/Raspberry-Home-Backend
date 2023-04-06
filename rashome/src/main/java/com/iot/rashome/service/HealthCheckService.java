package com.iot.rashome.service;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import com.iot.rashome.commons.enums.DeviceStatus;
import com.iot.rashome.commons.exception.IotBackendException;
import com.iot.rashome.commons.util.DateUtil;
import com.iot.rashome.commons.util.HttpUtil;
import com.iot.rashome.vo.DeviceDataVO;
import com.iot.rashome.vo.DeviceVO;

/**
 * 定时任务做健康检查
 */
@Service
public class HealthCheckService {

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private DeviceDataService deviceDataService;

    private Logger logger = LoggerFactory.getLogger(HealthCheckService.class);
    
    /**
     * 定时健康检查任务, 固定频率吧为 1000ms * 60 = 一分钟
     */
    @Scheduled(fixedRate = 1000 * 60)
    public void healthCheck() {

        // 获取当前在线的设备
        List<DeviceVO> onlineDeviceVOList = deviceService.getAllOnlineDevices();


        // 没有在线设备, 则不需要做健康检查
        if (CollectionUtils.isEmpty(onlineDeviceVOList)) {
            return;
        }

        // 遍历全部设备, 判断是否在线
        for (DeviceVO onlineDeviceVO : onlineDeviceVOList) {

            // 如果没有健康检查 url, 则查看最近一次数据传输, 根据健康检查频率, 确认是否离线
            if (StringUtils.isEmpty(onlineDeviceVO.getHealthCheckUrl())) {
                
                // 获取最新的传感器数据
                DeviceDataVO latestDeviceData = deviceDataService.getLatestDeviceData(onlineDeviceVO.getId());

                // 没有数据, 说明设备还没有发数据, 设备下线
                if (ObjectUtils.isEmpty(latestDeviceData)) {
                    onlineDeviceVO.setStatus(DeviceStatus.OFFLINE.name());
                    logger.info(String.format("%s 下线, 设备没有数据", onlineDeviceVO.getDeviceName()));
                    continue;
                }

                // 计算与当前的时间差
                int dateDiffInSecond = DateUtil.dateDiffInSecond(latestDeviceData.getCollectedDate());
                // 大于阈值, 则更新传感器状态为失败
                if (dateDiffInSecond > Integer.parseInt(onlineDeviceVO.getHealthCheckRate())) {
                    onlineDeviceVO.setStatus(DeviceStatus.OFFLINE.name());
                    logger.info(String.format("%s 下线, 设备长时间没有发送数据", onlineDeviceVO.getDeviceName()));
                }

                continue;
            }
            
            // 如果健康检查 url 不为空, 则访问 url 做健康检查
            try {
                HttpUtil.postJsonPayload(onlineDeviceVO.getHealthCheckUrl(), onlineDeviceVO.getDeviceName());
            } catch (IotBackendException | IllegalArgumentException e) {
                onlineDeviceVO.setStatus(DeviceStatus.OFFLINE.name());
                logger.info(String.format("%s 下线, 健康检查失败", onlineDeviceVO.getDeviceName()), new IotBackendException(e));
            } catch (RestClientException e) {
                logger.error("网络异常, 健康检查失败", new IotBackendException(e));
            }
        }

        deviceService.updateDeviceVO(onlineDeviceVOList);
    }
}
