package com.rashome.rashome.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rashome.rashome.entity.Sensor;

@Controller
@RequestMapping(value = "/sensor")
public class SensorController {
    
    @PostMapping(value = "/data")
    public void receiveData(@RequestBody Sensor sensor){

        System.out.println(sensor.toString());
    }
}
