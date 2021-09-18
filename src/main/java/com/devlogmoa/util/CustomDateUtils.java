package com.devlogmoa.util;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class CustomDateUtils {

    public static LocalDate parseLocalDate(Date date) {
        if (date == null) {
            throw new NullPointerException("null은 처리할 수 없습니다.");
        }
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }
}
