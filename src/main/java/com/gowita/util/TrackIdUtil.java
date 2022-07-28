package com.gowita.util;

import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TrackIdUtil {

    public static String generatePackageId(Long id) {
        String date = String.valueOf(LocalDate.now().getYear()).substring(2);
        return "#P" + date + generateId(id);
    }

    public static String generateTripId(Long id) {
        String date = String.valueOf(LocalDate.now().getYear()).substring(2);
        return "#T" + date + generateId(id);
    }

    private static String generateId(Long id) {
        int length = 8 - id.toString().length();
        StringBuilder generated = new StringBuilder();
        for (int i = 0; i < length; i++) {
            generated.append("0");
        }
        generated.append(id);
        return generated.toString();
    }
}
