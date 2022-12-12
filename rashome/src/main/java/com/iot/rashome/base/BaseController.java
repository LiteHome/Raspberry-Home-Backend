package com.iot.rashome.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iot.rashome.dto.Request;

import io.micrometer.common.util.StringUtils;

public class BaseController<S extends BaseService<V>,V> {

    @Autowired
    private S s;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public void realInsert(Request request, Class<V> type) throws JsonMappingException, JsonProcessingException {

        if (StringUtils.isNotBlank(request.getValue())) {
            
            V v = objectMapper.readValue(request.getValue(), type);

            s.insert(v);

        }
    }
    

    @GetMapping("/getLatestRecord")
    public V getLatestRecordBySampledBy(@RequestParam String sampledBy){

        if (StringUtils.isNotBlank(sampledBy)) {
            return s.getLatestRecord(sampledBy);
        } else {
            return null;
        }
    }
}
