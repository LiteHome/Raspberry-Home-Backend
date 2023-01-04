package com.iot.rashome.commons.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum DeviceType {

    MCU("Microcontroller Unit"),
    SENSOR("SENSOR"),
    SBC("Single Board Computer");

    @Getter
    private String value;
}
