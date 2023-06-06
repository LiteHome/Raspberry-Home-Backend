package com.iot.rashome.commons.util;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import com.iot.rashome.commons.exception.IotBackendException;

public class DateUtil {

    private static final ZoneId SHANGHAI_ZONE_ID = ZoneId.of("Asia/Shanghai");

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:MM:SS.SSS");

    private static final Long SEC_OR_MILLIS_THRESHOLD = 5L * 1000; // 5 秒内

    public static String parseFromTimeStamp(String timestamp) throws IotBackendException {

        try {
            Instant instant;
            long timestampLong = Long.parseLong(timestamp);

            // 检查 timestamp 是 毫秒 还是 秒
            // 使用当前时间和入参比较, 如果相差在阈值内, 说明是毫秒;否则是秒
            Long diff = Math.abs(System.currentTimeMillis() - timestampLong);
            if (diff < SEC_OR_MILLIS_THRESHOLD * 1000) {
                instant = Instant.ofEpochMilli(timestampLong);
            } else {
                instant = Instant.ofEpochSecond(timestampLong);
            }


            return ZonedDateTime.ofInstant(instant, SHANGHAI_ZONE_ID).format(FORMATTER);
        } catch (NumberFormatException e) {
            throw new IotBackendException("解析时间错误, String timestamp 无法解析", e);
        }
    }

    public static ZonedDateTime parseFromTimeStampToZonedDateTime(Long unixTimestamp) throws IotBackendException {

        try {
            Instant instant;

            // 检查 timestamp 是 毫秒 还是 秒
            // 使用当前时间和入参比较, 如果相差在阈值(ms)内, 说明是毫秒;否则是秒
            Long diff = System.currentTimeMillis() - unixTimestamp;
            if (diff < 0) {
                throw new IotBackendException("下游传输时间戳带了时区偏移");
            } else if (diff < SEC_OR_MILLIS_THRESHOLD) {
                instant = Instant.ofEpochMilli(unixTimestamp);
            } else {
                instant = Instant.ofEpochSecond(unixTimestamp);
            }

            return ZonedDateTime.ofInstant(instant, SHANGHAI_ZONE_ID);
        } catch (NumberFormatException e) {
            throw new IotBackendException("解析时间错误, String timestamp 无法解析", e);
        }
    }

    /**
     * 计算当前日期和目标日期在 秒 上面的差别, 正值代表当前日期较新, 反之同理
     * @param dateString 'yyyy-dd-mm HH:MM:SS'
     * @return 秒数差别
     */
    public static Long dateDiffInSecond(ZonedDateTime zonedDateTime) {
        return ChronoUnit.SECONDS.between(zonedDateTime, ZonedDateTime.now(SHANGHAI_ZONE_ID));
    }

    /**
     * 获取当前时间并格式化
     * @param dateTimeFormatter
     * @return
     */
    public static String getCurDateAndFormatted(DateTimeFormatter dateTimeFormatter) {

        ZonedDateTime instantTime = ZonedDateTime.ofInstant(Instant.now(), SHANGHAI_ZONE_ID);

        return instantTime.format(dateTimeFormatter);
    }
}
