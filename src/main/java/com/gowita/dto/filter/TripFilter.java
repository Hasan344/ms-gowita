package com.gowita.dto.filter;

import com.gowita.constant.TripType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TripFilter {
    Long fromWhereId;
    Long toWhereId;
    List<Long> packageTypeIdList;
    List<TripType> tripTypes;
    Double minWidth;
    Double maxWidth;
    Double minLength;
    Double maxLength;
    Double minHeight;
    Double maxHeight;
    Double minWeight;
    Double maxWeight;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    LocalDateTime arrivalEndDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    LocalDateTime arrivalStartDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    LocalDateTime departureStartDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    LocalDateTime departureEndDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate pickUpStartDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate pickUpEndDate;
}
