package com.rashome.rashome.controller;


import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rashome.rashome.dto.QuerySensorData;
import com.rashome.rashome.po.Dht11Data;
import com.rashome.rashome.service.Dht11DataService;

@RestController
@RequestMapping(value = "/sensors/dht11")
public class Dht11Controller {

    private Dht11DataService dht11DataService;

    public Dht11Controller(
        Dht11DataService dht11DataService
    ){
        this.dht11DataService = dht11DataService;
    }

    @PostMapping(value = "/data")
    public void receiveData(@RequestBody Dht11Data dht11Data){
        this.dht11DataService.addRecord(dht11Data);
    }

    @PostMapping(value = "/queryData")
    public List<Dht11Data> queryData(@RequestBody QuerySensorData querySensorData){
        return this.dht11DataService.queryDataByRasberryIDAndSensorID(querySensorData);
    }
}
