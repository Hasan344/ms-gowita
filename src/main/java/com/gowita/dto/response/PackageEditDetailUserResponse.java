package com.gowita.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gowita.constant.Status;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
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
public class PackageEditDetailUserResponse {
    Long id;
    Status status;
    CityResponse fromWhere;
    CityResponse toWhere;
    Double width;
    Double length;
    Double height;
    Double weight;
    PackageTypeResponse packageType;
    List<FilePathResponse> filePaths;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    LocalDate conductDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    LocalDate deliveryDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MMM-yyyy")
    LocalDateTime editedDate;
}
