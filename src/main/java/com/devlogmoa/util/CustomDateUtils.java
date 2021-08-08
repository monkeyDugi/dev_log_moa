package com.devlogmoa.util;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class CustomDateUtils {

    public static LocalDate parseLocalDate(Date date) {
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }
}
