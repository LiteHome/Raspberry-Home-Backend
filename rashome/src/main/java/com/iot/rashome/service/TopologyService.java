package com.iot.rashome.service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iot.rashome.commons.exception.DeviceIsNotAppearException;
import com.iot.rashome.dto.TopologyData;
import com.iot.rashome.vo.DeviceVO;

@Service
public class TopologyService {

    @Autowired
    private DeviceService deviceService;
    
    // master id : set<slave id>
    private static final Map<Long, Set<Long>> topologyMap = new ConcurrentHashMap<>();

    public void updateTopology(Long masterID, Long slaveID){

        topologyMap.putIfAbsent(masterID, new HashSet<>());
        Set<Long> slaveIDSet = topologyMap.get(masterID);

        slaveIDSet.add(slaveID);
    }

    // 保证返回的结果 master, sensor 都是注册过的
    private List<Long> getTopologyMap(Long masterID){

        if (BooleanUtils.isFalse(topologyMap.containsKey(masterID))) {
            throw new DeviceIsNotAppearException(String.format("Master is not Appearing, Device ID is %d", masterID));
        } else if (CollectionUtils.isEmpty(topologyMap.get(masterID))) {
            throw new DeviceIsNotAppearException(String.format("Slave is not Appearing, Device ID is %d", masterID));
        } else {
            return topologyMap.get(masterID).stream().toList();
        }
    }

    public TopologyData getTopology(Long masterID){
        
        TopologyData result = new TopologyData();
        List<Long> sensorIDList = getTopologyMap(masterID);

        DeviceVO masterDeviceVO = deviceService.findDeviceVOById(masterID);
        if (ObjectUtils.isNotEmpty(masterDeviceVO)) {
            result.setMaster(masterDeviceVO);

            for (Long sensorID : sensorIDList) {
                DeviceVO sensDeviceVO = deviceService.findDeviceVOById(sensorID);
                if (ObjectUtils.isNotEmpty(sensDeviceVO)) {
                    result.getSlave().add(sensDeviceVO);
                } else {
                    throw new DeviceIsNotAppearException(String.format("Slave is not Appearing, Device ID is %d", sensorID));
                }
            }

            return result;
        } else {
            throw new DeviceIsNotAppearException(String.format("Master is not Appearing, Master Device ID is %d", masterID));
        }
    }
}
