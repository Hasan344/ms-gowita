package com.gowita.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gowita.constant.TripType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;
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
public class TripRequest {
    @NotNull(message = "This field must not be empty")
    Long fromWhereId;

    @NotNull(message = "This field must not be empty")
    Long toWhereId;

    @NotNull(message = "This field must not be empty")
    Double width;

    @NotNull(message = "This field must not be empty")
    Double length;

    @NotNull(message = "This field must not be empty")
    Double height;

    @NotNull(message = "This field must not be empty")
    Double weight;
    Long packageTypeId;
    TripType tripType;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    LocalDateTime departureDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    LocalDateTime arrivalDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    LocalDate pickUpDate;
}
