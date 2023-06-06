package com.iot.rashome.service;

import java.io.IOException;
import java.time.ZonedDateTime;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.iot.rashome.commons.exception.IotBackendException;
import com.iot.rashome.commons.util.DateUtil;

@Service
public class DateCustomDeserializer extends StdDeserializer<ZonedDateTime> {

    private static final Logger logger = LoggerFactory.getLogger(DateCustomDeserializer.class);

    public DateCustomDeserializer() {
        this(null);
    }

    public DateCustomDeserializer(Class<?> src) {
        super(src);
    }

    /**
     * used for Long Timestamp field to zonedDateTime
     */
    @Override
    public ZonedDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {

        Long timestamp = p.readValueAs(Long.class);

        if (ObjectUtils.isEmpty(timestamp)) {
            logger.error("时间戳序列化失败", new IotBackendException());
        }

        try {
            ZonedDateTime result = DateUtil.parseFromTimeStampToZonedDateTime(timestamp);
            return result;
        } catch (IotBackendException e) {
            logger.error("无法转换为 Zoned DateTime", e);
        }

        return null;
    }
}