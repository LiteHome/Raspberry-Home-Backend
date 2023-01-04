package com.iot.rashome.dto;

import java.util.ArrayList;
import java.util.List;

import com.iot.rashome.vo.DeviceVO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TopologyData {

    private DeviceVO master;

    @Builder.Default
    private List<DeviceVO> slave = new ArrayList<>();
}
