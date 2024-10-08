package com.fc.fcserver.service.utils;


import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;

public class TimeUtils {
    public static Timestamp convertToShanghaiTime(String localTime) {
        // 传入UTC标准时间 s
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        LocalDateTime localDateTime = LocalDateTime.parse(localTime, formatter);
        ZonedDateTime shanghaiTime = localDateTime.atZone(ZoneId.of("Asia/Shanghai"));
//        shanghaiTime = shanghaiTime.minusHours(8);
        return Timestamp.valueOf(shanghaiTime.toLocalDateTime());
    }
}