package com.iot.rashome.converter;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.iot.rashome.commons.exception.IotBackendException;
import com.iot.rashome.commons.util.JsonUtil;

import jakarta.persistence.AttributeConverter;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ListFloatConverter implements AttributeConverter<List<Float>, String> {

    @Override
    public String convertToDatabaseColumn(List<Float> attribute) {

        if (CollectionUtils.isEmpty(attribute)) {
            return null;
        }

        try {
            return JsonUtil.toJsonString(attribute);
        } catch (IotBackendException e) {
            log.error(String.format("无法序列化 %s", attribute), e);
            return null;
        }
    }

    @Override
    public List<Float> convertToEntityAttribute(String dbData) {

        if (StringUtils.isBlank(dbData)) {
            return new ArrayList<Float>();
        }

        try {
            return JsonUtil.stringToArrayObject(dbData, Float.class);
        } catch (IotBackendException e) {
            log.error(String.format("无法反序列化 %s", dbData), e);
            return new ArrayList<Float>();
        }
    }
}