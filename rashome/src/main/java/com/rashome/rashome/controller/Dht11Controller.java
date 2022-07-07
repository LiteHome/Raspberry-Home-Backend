package com.rashome.rashome.controller;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rashome.rashome.po.Dht11Data;
import com.rashome.rashome.utils.TimestampConvert;

@RestController
@RequestMapping(value = "/sensors/dht11")
public class Dht11Controller {
    
    @PostMapping(value = "/data")
    public void receiveData(@RequestBody Dht11Data dht11Data){
        System.out.println(dht11Data.toString());
    }
}
