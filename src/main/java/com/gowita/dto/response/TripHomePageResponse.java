package com.gowita.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gowita.constant.TripType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TripHomePageResponse {
    Long id;
    String size;
    Double weight;
    String fromWhere;
    String toWhere;
    TripType type;
    String packageType;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MMM-yyyy HH:mm")
    LocalDateTime departureDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MMM-yyyy HH:mm")
    LocalDateTime arrivalDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MMM-yyyy")
    LocalDate pickUpDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MMM-yyyy")
    LocalDateTime updatedAt;
    boolean isMine;
    boolean alreadyOffered;
}
