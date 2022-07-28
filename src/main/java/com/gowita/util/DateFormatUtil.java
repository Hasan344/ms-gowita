package com.gowita.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.experimental.UtilityClass;
import org.mapstruct.Named;

@UtilityClass
@Named(value = "Date")
public class DateFormatUtil {

    @Named(value = "formatDate")
    public static LocalDate toDate(String date) {
        return date == null ? null : LocalDate.parse(date);
    }

    @Named(value = "formatDateTime")
    public static LocalDateTime toDateTime(String dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return dateTime == null ? null : LocalDateTime.parse(dateTime, formatter);
    }
}
