package com.iot.rashome.service;

import java.util.UUID;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.iot.rashome.commons.exception.IotBackendException;
import com.iot.rashome.commons.util.JsonUtil;
import com.iot.rashome.dto.MqttSensorDTO;

@Service
public class MqttHaDiscoveryService {
    @Value("${mqtt.host}")
    private String mqttHost;

    @Value("${mqtt.username}")
    private String mqttUsername;

    @Value("${mqtt.password}")
    private String mqttPassword;

    @Value("${mqtt.ha.discovery.topic.prefix}")
    private String mqttTopicPrefix;

    private MqttClient client;

    private static final Logger logger = LoggerFactory.getLogger(MqttHaDiscoveryService.class);

    public MqttHaDiscoveryService() throws IotBackendException {

        MemoryPersistence persistence = new MemoryPersistence();

        try {
            this.client = new MqttClient(
                mqttHost, 
                UUID.randomUUID().toString() + "-backend", 
                persistence);

            // MQTT connection option
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setUserName(mqttUsername);
            connOpts.setPassword(mqttPassword.toCharArray());
            // retain session
            connOpts.setCleanSession(true);

            // establish a connection
            System.out.println("正在连接 MQTT Broker: " + mqttHost);
            client.connect(connOpts);

            System.out.println("连接成功");

            client.disconnect();
            client.close();
        } catch (MqttException e) {
            throw new IotBackendException("Mqtt 连接失败", e);
        }
    }

    public void sendMessage(MqttSensorDTO mqttSensorDTO) {
        // https://www.home-assistant.io/integrations/mqtt/

        String configTopic = String.format("%s/sensor/%s/config", mqttTopicPrefix, mqttSensorDTO.getUniqueId());
        String stateTopic = String.format("%s/sensor/%s/state", mqttTopicPrefix, mqttSensorDTO.getUniqueId());
        mqttSensorDTO.setStateTopic(stateTopic);

        String payloadString;
        MqttMessage message;
        try {
            payloadString = JsonUtil.toJsonString(mqttSensorDTO);
            message = new MqttMessage(payloadString.getBytes());
            message.setQos(1);
        } catch (IotBackendException e) {
            logger.warn("mqtt 消息序列化失败", e);
            return;
        }

        try {
            client.publish(configTopic, message);
        } catch (MqttException e) {
            logger.warn("mqtt 消息发送失败", new IotBackendException(e));
        }
    }
}
