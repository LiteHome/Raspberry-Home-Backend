package com.iot.rashome.base;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
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
import com.iot.rashome.util.DateUtil;

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
            logger.info("insert method get null Request parameter");
        }
    }
    

    @GetMapping("/getLatestRecord")
    public V getLatestRecordBySampledBy(@RequestParam String sampledBy){

        if (StringUtils.isNotBlank(sampledBy)) {

            logger.info("sampledBy parameter is : {}", sampledBy);

            return service.getLatestRecord(sampledBy);
        } else {
            logger.info("getLatestRecordBySampledBy method get null SampledBy parameter");
            return null;
        }
    }

    @GetMapping("/getRecordBetweenDate")
    public List<V> getRecordBetweenDate(@RequestParam String left, @RequestParam String right){
        
        if(StringUtils.isNotBlank(left) && StringUtils.isNotBlank(right)){

            left = StringUtils.trim(left);
            right = StringUtils.trim(right);

            try {
                Date leftBorderDate = DateUtil.parseInAllPossiableFormat(left);
                Date rightBorderDate = DateUtil.parseInAllPossiableFormat(right);

                return service.getRecordBetweenDate(leftBorderDate, rightBorderDate);
            } catch (ParseException e) {
                logger.info("bad query date formatte, left is {}, right is {}", left, right, e);
                return null;
            }
        } else {
            logger.info("null parameter");
            return null;
        }
    }

    @GetMapping("/getRecordInLastDate")
    public List<V> getRecordInLastDate(@RequestParam String unit, @RequestParam String quantity){

        if (StringUtils.isNotBlank(unit) && StringUtils.isNotBlank(quantity)) {
            unit = StringUtils.trimToNull(unit);
            quantity = StringUtils.trimToNull(quantity);

            if (!StringUtils.isNumeric(quantity)) {
                logger.info("bad quantity format {}", quantity);
                return null;
            }

            Pair<Date,Date> datePair = DateUtil.subtractDate(unit, Integer.parseInt(quantity));

            if (ObjectUtils.isEmpty(datePair)) {
                logger.info("bad unit format {}", unit);
                return null;
            }

            return service.getRecordBetweenDate(datePair.getLeft(), datePair.getRight());
        } else {
            logger.info("null parameter");
            return null;
        }
    }
}
