package com.iot.rashome.commons.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum DeviceType {

    MASTER("Master"),
    SLAVE("Slave");

    @Getter
    private String value;
}
