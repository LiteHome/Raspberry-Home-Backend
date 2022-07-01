package com.rashome.rashome.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rashome.rashome.entity.RasberryPi;
import com.rashome.rashome.utils.BytesConvert;
import com.rashome.rashome.utils.BytesConvert.*;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping(value = "/rasberrypi")
public class RasberryPiController {
    
    @PostMapping(value="/heartbeat")
    public void receiveHeartbeat(@RequestBody RasberryPi ras) {
        System.out.println(ras.toString());
    }
}
