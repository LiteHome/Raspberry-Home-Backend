package com.iot.rashome.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iot.rashome.dto.Request;

import io.micrometer.common.util.StringUtils;

public abstract class BaseController<S extends BaseService<V>, V> {

    @Autowired
    public S service;

    public static final ObjectMapper objectMapper = new ObjectMapper();

    public final Logger logger = LoggerFactory.getLogger(setChildClass());

    public Class<V> type = setVoType();

    public abstract Class<V> setVoType();

    public abstract Class<?> setChildClass();

    @PostMapping("/")
    public void insert(@RequestBody Request request) throws JsonMappingException, JsonProcessingException {

    
        if (StringUtils.isNotBlank(request.getValue())) {

            logger.info("Request parameter is : {}",request.toString());
            
            V v = objectMapper.readValue(request.getValue(), type);
            
            service.insert(v);
        } else {
            logger.warn("insert method get null Request parameter");
        }
    }
    

    @GetMapping("/getLatestRecord")
    public V getLatestRecordBySampledBy(@RequestParam String sampledBy){

        if (StringUtils.isNotBlank(sampledBy)) {

            logger.info("sampledBy parameter is : {}", sampledBy);

            return service.getLatestRecord(sampledBy);
        } else {
            logger.warn("getLatestRecordBySampledBy method get null SampledBy parameter");
            return null;
        }
    }
}
