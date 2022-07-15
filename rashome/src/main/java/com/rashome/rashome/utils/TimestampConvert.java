package com.rashome.rashome.utils;

import java.time.*;
import java.time.format.DateTimeFormatter;

public class TimestampConvert {

    private static DateTimeFormatter yearFormatterOnly = DateTimeFormatter.ofPattern("yyyy");
    private static DateTimeFormatter monthOnlyFormatter = DateTimeFormatter.ofPattern("MM");
    private static DateTimeFormatter dayOnlyFormatter = DateTimeFormatter.ofPattern("dd");
    private static DateTimeFormatter hmsOnlyFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    private static LocalDateTime convertToLocalDate(long timestamp) {
        return LocalDateTime.ofInstant(
                Instant.ofEpochMilli(timestamp),
                ZoneId.of("Asia/Shanghai")
            );
    }

    public static String timestampToYear(long timestamp) {
        return convertToLocalDate(timestamp).format(yearFormatterOnly);
    }

    public static String timestampToMonthOfYear(long timestamp) {
        return convertToLocalDate(timestamp).format(monthOnlyFormatter);
    }

    public static String timestampToDayOfYear(long timestamp) {
        return convertToLocalDate(timestamp).format(dayOnlyFormatter);
    }

    public static String timestampToSecondOfYear(long timestamp) {
        return convertToLocalDate(timestamp).format(hmsOnlyFormatter);
    }
}
