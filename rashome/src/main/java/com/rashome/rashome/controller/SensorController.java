package com.rashome.rashome.controller;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rashome.rashome.dto.TemperatureSensor;
import com.rashome.rashome.utils.TimestampConvert;

@RestController
@RequestMapping(value = "/sensor")
public class SensorController {
    
    @PostMapping(value = "/temperature/data")
    public void receiveData(@RequestBody TemperatureSensor sensor){

        System.out.println(sensor.toString());
        System.out.println(TimestampConvert.convertToLocalDate(sensor.getTime()));
    }
}
