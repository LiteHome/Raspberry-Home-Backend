package com.rashome.rashome.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rashome.rashome.dto.QueryRasberryPi;
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

    public RasberryPiController(
        TopologyService topologyService,
        RasberryPiDataService rasberryPiDataService
    ){
        this.topologyService = topologyService;
    }
    
    @PostMapping(value="/data")
    public void receiveData(@RequestBody RasberryPiData rasberryPiData) {
        this.rasberryPiDataService.addRecord(rasberryPiData);
    }

    @PostMapping(value = "/queryData")
    public List<RasberryPiData> querData(@RequestBody QueryRasberryPi queryRasberryPi){
        return this.rasberryPiDataService.queryDataByRasberrPiID(queryRasberryPi);
    }

    @PostMapping(value = "/topology/add")
    public void receiveTopology(@RequestBody Topology topology){
        this.topologyService.addTopologyInfo(topology);
    }

    @PostMapping(value = "/topology/query")
    public ConcurrentHashMap<String, List<Long>> queryTopology(@RequestBody QueryRasberryPi queryRasberryPi){
        return this.topologyService.queryTopology(queryRasberryPi.getRasberryPiID());
    }
}
