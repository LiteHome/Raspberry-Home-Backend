package com.iot.rashome.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class MqttSensorDTO {
    // 来源 : https://www.home-assistant.io/integrations/sensor.mqtt/#model
    
    @JsonProperty("name")
    private String name; // 传感器名称

    @JsonProperty("state_topic")
    private String stateTopic;

    @JsonProperty("unique_id")
    private String uniqueId;

    @JsonProperty("unit_of_measurement")
    private String unitOfMeasurement;

    @JsonProperty("value_template")
    private String valueTemplate; // 格式形如 {{<模板>}}

    @AllArgsConstructor
    @Data
    static class Device {
        @JsonProperty("configuration_url")
        public String configurationUrl; // 设备配置页面

        @JsonProperty("identifiers")
        public String identifiers; // 唯一 ID

        @JsonProperty("manufacturer")
        public String manufacturer; // 厂商

        @JsonProperty("model")
        public String model; // 型号

        @JsonProperty("name")
        public String name; // 设备名称

        @JsonProperty("via_device")
        public String viaDevice; // 父设备
    }
}
