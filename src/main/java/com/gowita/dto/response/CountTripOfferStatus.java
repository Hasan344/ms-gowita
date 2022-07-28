package com.gowita.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CountTripOfferStatus {
    Long countCard;
    Long countRejected;
    Long countPending;
    Long countConfirmed;
    Long countOpened;
    Long countAll;
}
