package com.iot.rashome.service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.stereotype.Service;

import com.iot.rashome.commons.exception.DeviceIsNotAppearException;

@Service
public class TopologyService {
    
    // master id : set<slave id>
    private static final Map<Long, Set<Long>> topologyMap = new ConcurrentHashMap<>();

    public void updateTopology(Long masterID, Long slaveID){

        topologyMap.putIfAbsent(masterID, new HashSet<>());
        Set<Long> slaveIDSet = topologyMap.get(masterID);

        slaveIDSet.add(slaveID);
    }

    public List<Long> getTopologyMap(Long masterID){

        if (BooleanUtils.isFalse(topologyMap.containsKey(masterID))) {
            throw new DeviceIsNotAppearException(String.format("Master is not Appearing, Master Device ID is %d", masterID));
        } else if (CollectionUtils.isEmpty(topologyMap.get(masterID))) {
            throw new DeviceIsNotAppearException(String.format("Slave is not Appearing, Master Device ID is %d", masterID));
        } else {
            return topologyMap.get(masterID).stream().toList();
        }

    }
}
