package com.rashome.rashome.dto;

import java.util.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Topology {
    
    private Long rasberryPiID;
    private List<String> sensorsModel;
    private List<Long> sensorsID;
}
