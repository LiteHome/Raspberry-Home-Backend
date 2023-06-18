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

    public static String parseFromTimeStamp(String timestamp) throws IotBackendException {

        try {
            long timestampLong = Long.parseLong(timestamp);
            Instant instant = Instant.ofEpochMilli(timestampLong);

            return ZonedDateTime.ofInstant(instant, SHANGHAI_ZONE_ID).format(FORMATTER);
        } catch (NumberFormatException e) {
            throw new IotBackendException(String.format("无法解析 %s 时间戳", timestamp), e);
        }
    }

    /***
     * Long 时间戳转换为中国上海时间, 要求传入的时间戳是毫秒单位
     * @param unixTimestamp
     * @return
     * @throws IotBackendException
     */
    public static ZonedDateTime parseFromTimeStampToZonedDateTime(Long unixTimestamp) throws IotBackendException {

        try {
            Instant instant = Instant.ofEpochMilli(unixTimestamp);

            return ZonedDateTime.ofInstant(instant, SHANGHAI_ZONE_ID);
        } catch (NumberFormatException e) {
            throw new IotBackendException(String.format("无法解析 %s 时间戳", unixTimestamp), e);
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

    /**
     * 获取当前时间
     * @param dateTimeFormatter
     * @return
     */
    public static ZonedDateTime getCurDate() {

        return ZonedDateTime.ofInstant(Instant.now(), SHANGHAI_ZONE_ID);
    }
}
