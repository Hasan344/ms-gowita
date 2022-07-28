package com.gowita.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gowita.constant.TripType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TripEditDetailUserResponse {
    Long id;
    CityResponse fromWhere;
    CityResponse toWhere;
    TripType tripType;
    Double width;
    Double length;
    Double height;
    Double weight;
    PackageTypeResponse packageType;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    LocalDateTime departureDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    LocalDateTime arrivalDate;
    LocalDate pickUpDate;
}
