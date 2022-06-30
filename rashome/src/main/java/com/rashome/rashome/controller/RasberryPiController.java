package com.rashome.rashome.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rashome.rashome.entity.RasberryPi;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
@RequestMapping("/rasberrypi")
public class RasberryPiController {
    
    @PostMapping(value="/heartbeat")
    public void receiveHeartbeat(@RequestBody RasberryPi ras) {
        //TODO: process POST request
        
        return ras;
    }
    
}
