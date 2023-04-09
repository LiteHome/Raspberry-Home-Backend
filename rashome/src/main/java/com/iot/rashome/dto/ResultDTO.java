package com.iot.rashome.dto;

import com.iot.rashome.commons.enums.ResultCode;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ResultDTO {
    
    private String code;
    
    private String message;

    private String data;

    public static ResultDTO success(String data) {
        return new ResultDTO(ResultCode.SUCCESSED.getCode(), "success", data);
    }

    public static ResultDTO fail(String code, String message) {
        return new ResultDTO(code, message, null);
    }
}
