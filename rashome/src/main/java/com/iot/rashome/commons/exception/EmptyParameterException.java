package com.iot.rashome.commons.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class EmptyParameterException extends RuntimeException {

    public EmptyParameterException(String message) {
        super(message);
    }
    
}
