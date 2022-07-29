package com.rashome.rashome.controller;


import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rashome.rashome.cache.JustOneCache;
import com.rashome.rashome.dto.QueryData;
import com.rashome.rashome.po.Dht11Data;
import com.rashome.rashome.service.Dht11DataService;
import com.rashome.rashome.utils.TimestampUtils;

@RestController
@RequestMapping(value = "/sensors/dht11")
public class Dht11Controller {

    private Dht11DataService dht11DataService;
    private JustOneCache<Dht11Data> cache;

    public Dht11Controller(
        Dht11DataService dht11DataService
    ){
        this.dht11DataService = dht11DataService;
        this.cache = new JustOneCache<Dht11Data>();
    }

    @PostMapping(value = "/data")
    public void receiveData(@RequestBody Dht11Data dht11Data){
        this.cache.putItem(
            dht11Data, 
            dht11Data.getRasberryPiID(),
            dht11Data.getSensorID()
            );

        this.dht11DataService.addRecord(dht11Data);
    }


    @PostMapping(value = "/queryRealTimeData")
    public List<Dht11Data> queryRealTimeData(@RequestBody QueryData queryData) {

        var addNullDht11Data = true;

        var sensorsID = queryData.getSensorsID();

        var result = new ArrayList<Dht11Data>(sensorsID.size());

        for (long sensorID : sensorsID) {
            
            var item = this.cache.getItem(
                queryData.getRasberryPiID(),
                sensorID
            );
    
            if (item != null) {
                if (TimestampUtils.diffInMinutes(item.getCollectTime()) < 1) {
                    result.add(item);
                    addNullDht11Data = false;
                }
            }

            // process with item == null and item expire
            if (addNullDht11Data) {
                result.add(new Dht11Data(queryData.getRasberryPiID(), sensorID));
            }
        }

        return result;
    }

    @PostMapping(value = "/queryHistoryData")
    public List<Dht11Data> queryHistoryData(@RequestBody QueryData queryData){
        System.out.println(queryData.toString());
        return this.dht11DataService.queryDataByTimestamp(queryData);
    }

    @PostMapping(value = "/queryData")
    public List<Dht11Data> queryData(@RequestBody QueryData queryData){
        return this.dht11DataService.queryDataByRasberryIDAndSensorID(queryData);
    }
}
