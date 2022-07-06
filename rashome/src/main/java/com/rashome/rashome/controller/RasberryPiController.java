package com.rashome.rashome.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rashome.rashome.dto.RasberryPi;
import com.rashome.rashome.service.RasPiHeartbeatDataService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping(value = "/rasberrypi")
public class RasberryPiController {

    private RasPiHeartbeatDataService rasPiHeartbeatDataService;

    @Autowired
    public RasberryPiController(
        RasPiHeartbeatDataService rasPiHeartbeatDataService
    ){
        this.rasPiHeartbeatDataService = rasPiHeartbeatDataService;
    }
    
    @PostMapping(value="/heartbeat")
    public void receiveHeartbeat(@RequestBody RasberryPi rasberryPi) {

        System.out.println(this.rasPiHeartbeatDataService.addRecord(rasberryPi));
    }
}
