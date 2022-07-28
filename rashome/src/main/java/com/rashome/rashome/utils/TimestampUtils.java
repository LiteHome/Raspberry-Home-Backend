package com.rashome.rashome.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class TimestampUtils {

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

    public static int diffInMinutes(long timestamp) {
        var currentTimestamp = ZonedDateTime.now().toInstant().toEpochMilli();
        return Math.round((currentTimestamp - timestamp) / (1000 * 60));
    }
}
