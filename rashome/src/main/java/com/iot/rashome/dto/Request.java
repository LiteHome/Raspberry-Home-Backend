package com.iot.rashome.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Request implements Serializable{
    private static final long serialVersionUID = 1L;

    @JsonPropertyDescription("sampled date")
    private String sampledDate;

    @JsonPropertyDescription("sensor value in json")
    private String value;
}
