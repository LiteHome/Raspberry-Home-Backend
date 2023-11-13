package com.iot.rashome.service;

import java.util.UUID;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.iot.rashome.commons.enums.DeviceStatus;
import com.iot.rashome.commons.exception.IotBackendException;
import com.iot.rashome.commons.util.JsonUtil;
import com.iot.rashome.dto.MqttSensorDTO;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MqttHaDiscoveryService {
    @Value("${mqtt.ha.discovery.topic.prefix}")
    private String mqttTopicPrefix;

    private MqttClient client;

    public MqttHaDiscoveryService(
        @Value("${mqtt.host}")String mqttHost,
        @Value("${mqtt.username}") String mqttUsername,
        @Value("${mqtt.password}") String mqttPassword) 
        throws IotBackendException {
        // 使用内存存储
        MemoryPersistence persistence = new MemoryPersistence();
        // 连接服务器
        try {
            this.client = new MqttClient(
                mqttHost, 
                UUID.randomUUID() + "-backend",
                persistence);

            // 设置连接选项
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setUserName(mqttUsername);
            connOpts.setPassword(mqttPassword.toCharArray());
            // retain session
            connOpts.setCleanSession(true);

            // 创建连接
            log.info("正在连接 MQTT Broker: " + mqttHost);
            client.connect(connOpts);
            log.info("连接成功");

//            client.disconnect();
//            client.close();
        } catch (MqttException e) {
            throw new IotBackendException("Mqtt 连接失败", e);
        }
    }

    // todo: mqtt 默认配置抽成一个类
    public void sendMessage(MqttSensorDTO mqttSensorDTO) {
        // https://www.home-assistant.io/integrations/mqtt/

        String commonTopicPrefix = String.format("%s/sensor/%s", mqttTopicPrefix, mqttSensorDTO.getUniqueId());

        String configTopic = commonTopicPrefix + "/config";
        String stateTopic = commonTopicPrefix + "/state";
        String availabilityTopic = commonTopicPrefix + "/availability";
        mqttSensorDTO.setStateTopic(stateTopic);
        mqttSensorDTO.setAvailabilityTopic(availabilityTopic);
        mqttSensorDTO.setPayloadAvailable(DeviceStatus.ONLINE.name());
        mqttSensorDTO.setPayloadNotAvailable(DeviceStatus.OFFLINE.name());
        mqttSensorDTO.setValueTemplate("{{value}}");


        String payloadString;
        MqttMessage message;
        try {
            payloadString = JsonUtil.toJsonString(mqttSensorDTO);
            message = new MqttMessage(payloadString.getBytes());
            message.setQos(1);
        } catch (IotBackendException e) {
            log.warn("mqtt 消息序列化失败", e);
            return;
        }

        try {
            client.publish(configTopic, message);
        } catch (MqttException e) {
            log.warn("mqtt 消息发送失败", new IotBackendException(e));
        }
    }
}
