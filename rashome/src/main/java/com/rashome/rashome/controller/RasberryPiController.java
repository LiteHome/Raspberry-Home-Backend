package com.rashome.rashome.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rashome.rashome.cache.JustOneCache;
import com.rashome.rashome.dto.QueryData;
import com.rashome.rashome.dto.Topology;
import com.rashome.rashome.po.RasberryPiData;
import com.rashome.rashome.service.RasberryPiDataService;
import com.rashome.rashome.service.TopologyService;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping(value = "/rasberrypi")
public class RasberryPiController {

    private TopologyService topologyService;
    private RasberryPiDataService rasberryPiDataService;
    private JustOneCache<RasberryPiData> cache;

    public RasberryPiController(
        TopologyService topologyService,
        RasberryPiDataService rasberryPiDataService
    ){
        this.topologyService = topologyService;
        this.rasberryPiDataService = rasberryPiDataService;
        this.cache = new JustOneCache<>();
    }
    
    @PostMapping(value="/data")
    public void receiveData(@RequestBody RasberryPiData rasberryPiData) {
        this.cache.putItem(rasberryPiData, rasberryPiData.getRasberryPiID(), null);
        this.rasberryPiDataService.addRecord(rasberryPiData);
    }

    @PostMapping(value = "/queryHistoryData")
    public List<RasberryPiData> querHistoryData(@RequestBody QueryData queryData){
        return this.rasberryPiDataService.queryDataByTimestamp(queryData);
    }

    @PostMapping(value = "/queryRealTimeData")
    public RasberryPiData querRealTimeData(@RequestBody QueryData queryData){
        
        var item = this.cache.getItem(queryData.getRasberryPiID(), null);
        if (item == null) {
            return new RasberryPiData();
        }else{
            return item;
        }
    }

    @PostMapping(value = "/topology/add")
    public void receiveTopology(@RequestBody Topology topology){
        this.topologyService.addTopologyInfo(topology);
    }

    @PostMapping(value = "/topology/querymodels")
    public ConcurrentHashMap<String, List<Long>> queryModels(@RequestBody QueryData queryData){
        return this.topologyService.queryAllModelWithSensors(queryData);
    }

    @PostMapping(value = "/topology/querysensors")
    public ConcurrentHashMap<Long, String> querySensors(@RequestBody QueryData queryData){
        return this.topologyService.queryAllSensorsWithModel(queryData);
    }
}
