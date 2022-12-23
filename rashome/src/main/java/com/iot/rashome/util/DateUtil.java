package com.iot.rashome.util;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

public class DateUtil {
    
    public static Date parseInAllPossiableFormat(String stringDate) throws ParseException {
        return DateUtils.parseDate(stringDate, 
        "yyyy-MM-dd HH:mm:ss", 
        "yyyy-MM-dd HH:mm",
        "yyyy-MM-dd HH",
        "yyyy-MM-dd",
        "yyyy-MM",
        "yyyy");
    }

    public static Pair<Date, Date> subtractDate(String unit, Integer quantity){

        Date currentDate = new Date();

        MutablePair<Date, Date> result = new MutablePair<>();

        if (StringUtils.equalsIgnoreCase(unit, "year")) {
            result.left = DateUtils.addYears(currentDate, -quantity);
            result.right = DateUtils.truncate(currentDate, Calendar.YEAR);
        } else if (StringUtils.equalsIgnoreCase(unit, "month")){
            result.left = DateUtils.addMonths(currentDate, -quantity);
            result.right = DateUtils.truncate(currentDate, Calendar.MONTH);
        } else if (StringUtils.equalsIgnoreCase(unit, "day")){
            result.left = DateUtils.addDays(currentDate, -quantity);
            result.right = DateUtils.truncate(currentDate, Calendar.DATE);
        } else if (StringUtils.equalsIgnoreCase(unit, "hour")){
            result.left = DateUtils.addHours(currentDate, -quantity);
            result.right = DateUtils.truncate(currentDate, Calendar.HOUR);
        } else if (StringUtils.equalsIgnoreCase(unit, "minute")){
            result.left = DateUtils.addMinutes(currentDate, -quantity);
            result.right = DateUtils.truncate(currentDate, Calendar.MINUTE);
        } else if (StringUtils.equalsIgnoreCase(unit, "second")){
            result.left = DateUtils.addSeconds(currentDate, -quantity);
            result.right = DateUtils.truncate(currentDate, Calendar.SECOND);
        } else {
            return null;
        }

        return result;
    }
}
