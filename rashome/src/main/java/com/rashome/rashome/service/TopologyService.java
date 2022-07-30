package com.rashome.rashome.service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.rashome.rashome.dto.QueryData;
import com.rashome.rashome.dto.Topology;


import lombok.var;

@Service
public class TopologyService {

    private ConcurrentHashMap<Long, ConcurrentHashMap<Long, String>> map = new ConcurrentHashMap<>();

    public void addTopologyInfo(Topology topology){

        var size = topology.getSensorsModel().size();
        var idsList = topology.getSensorsID();
        var modelList = topology.getSensorsModel();
        var subMap = new ConcurrentHashMap<Long, String>();

        for (int i = 0; i < size; i++) {
            subMap.put(idsList.get(i), modelList.get(i));
        }

        this.map.put(topology.getRasberryPiID(), subMap);
    }

    public ConcurrentHashMap<Long, String> queryAllSensorsWithModel(QueryData queryData) {
        return this.map.get(queryData.getRasberryPiID());
    }

    public ConcurrentHashMap<String, List<Long>> queryAllModelWithSensors(QueryData queryData){

        if (this.map.containsKey(queryData.getRasberryPiID())) {
            var subMap = this.map.get(queryData.getRasberryPiID());
            var result = new ConcurrentHashMap<String, List<Long>>();
            var entrySet = subMap.entrySet();
            
            for (var entry : entrySet) {
                var list = result.getOrDefault(entry.getValue(), new ArrayList<Long>());
                list.add(entry.getKey());
                result.put(entry.getValue(), list);
            }
            return result;
        }else{
            return null;
        }
    }
}
