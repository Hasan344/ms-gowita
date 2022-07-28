package com.gowita.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.gowita.constant.TripOfferStatus;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TripOfferResponseForTrip {

    Long id;
    String packageType;
    String size;
    Double weight;
    TripOfferStatus status;
    String name;
    String surname;
    String phoneNumber;
}
