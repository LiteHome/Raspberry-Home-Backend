package com.rashome.rashome.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rashome.rashome.entity.RasberryPi;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping(value = "/rasberrypi")
public class RasberryPiController {
    
    @PostMapping(value="/heartbeat")
    public String receiveHeartbeat(@RequestBody RasberryPi ras) {
        System.out.println(ras.toString());
        return "roger";
    }
    
    @PostMapping(value = "/test")
    public String test(String string){
        System.out.println(string);
        return "okk";
    }
}
