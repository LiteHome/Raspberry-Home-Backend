package com.rashome.rashome.utils;

import java.time.*;

public class TimestampConvert {
    
    public static String convertToLocalDate(long timestamp) {
        return LocalDateTime.ofInstant(
                Instant.ofEpochMilli(timestamp),
                ZoneId.of("Asia/Shanghai")
            ).toString();
    }
}
