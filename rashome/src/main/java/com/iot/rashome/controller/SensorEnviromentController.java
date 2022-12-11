package com.iot.rashome.controller;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iot.rashome.dto.Request;
import com.iot.rashome.service.MainControlService;
import com.iot.rashome.vo.MainControlVO;

import io.micrometer.common.util.StringUtils;

@RestController
@RequestMapping("/sensorEnviroment")
public class SensorEnviromentController {
    
    @Autowired
    private MainControlService mainControlService;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/")
    public void insert(@RequestBody Request request) throws JsonMappingException, JsonProcessingException{

        System.out.println(request);

        if (StringUtils.isNotBlank(request.getValue())) {
            
            MainControlVO mainControlVO = objectMapper.readValue(request.getValue(), MainControlVO.class);

            mainControlVO = mainControlService.insert(mainControlVO);

            System.out.println(mainControlVO);
        }
    }
}
