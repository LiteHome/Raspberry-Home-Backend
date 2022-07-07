package com.rashome.rashome.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rashome.rashome.dto.Topology;
import com.rashome.rashome.po.RasberryPiData;
import com.rashome.rashome.service.TopologyService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping(value = "/rasberrypi")
public class RasberryPiController {

    private TopologyService topologyService;

    public RasberryPiController(
        TopologyService topologyService
    ){
        this.topologyService = topologyService;
    }
    
    @PostMapping(value="/data")
    public void receiveData(@RequestBody RasberryPiData rasberryPiData) {

        System.out.println(rasberryPiData.toString());
    }

    @PostMapping(value = "/topology/add")
    public void receiveTopology(@RequestBody Topology topology){
        this.topologyService.addTopologyInfo(topology);
        System.out.println(this.topologyService.queryTopology(topology.getRasberryPiID()));
    }
}
