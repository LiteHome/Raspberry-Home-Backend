package com.rashome.rashome.service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.rashome.rashome.dto.Topology;

@Service
public class TopologyService {

    private ConcurrentHashMap<Long, ConcurrentHashMap<String, List<Long>>> result = new ConcurrentHashMap<>();

    public void addTopologyInfo(Topology topology){
        ConcurrentHashMap<String, List<Long>>  tmp = new ConcurrentHashMap<>();

        int size = topology.getSensorsModel().size();

        for (int i = 0; i < size; i++) {
            tmp.putIfAbsent(topology.getSensorsModel().get(i), new ArrayList<>());

            tmp.get(topology.getSensorsModel().get(i))
            .add(topology.getSensorsID().get(i));
        }

        result.put(topology.getRasberryPiID(), tmp);
    }

    public ConcurrentHashMap<String, List<Long>> queryTopology(Long rasberryPiID){
        return result.get(rasberryPiID);
    }
}
