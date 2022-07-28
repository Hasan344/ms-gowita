package com.gowita.dto.filter;

import java.time.LocalDate;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PackageFilter {
    List<Long> packageTypeIdList;
    Long fromWhereId;
    Long toWhereId;
    Double minWidth;
    Double maxWidth;
    Double minLength;
    Double maxLength;
    Double minHeight;
    Double maxHeight;
    Double minWeight;
    Double maxWeight;

    LocalDate dateTo;
    LocalDate dateFrom;

    LocalDate conductDateTo;
    LocalDate conductDateFrom;
}
