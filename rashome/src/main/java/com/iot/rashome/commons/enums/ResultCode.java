package com.iot.rashome.commons.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum ResultCode {

    SUCCESSED("200"),

    DUPLICATE_DEVICE_NAME("401"),

    DUPLICATE_DEVICE_UCCID("403"),
    
    DEVICE_NOT_REGISTED("402");

    @Getter
    private final String code;

}
