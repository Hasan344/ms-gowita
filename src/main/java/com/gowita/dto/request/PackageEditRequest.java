package com.gowita.dto.request;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PackageEditRequest {

    @NotNull(message = "From where must not be empty")
    Long fromWhereId;

    @NotNull(message = "To where must not be empty")
    Long toWhereId;

    @NotNull(message = "This field must not be empty")
    Double width;

    @NotNull(message = "This field must not be empty")
    Double length;

    @NotNull(message = "This field must not be empty")
    Double height;

    @NotNull(message = "Weight must not be null")
    Double weight;

    Long typeId;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    LocalDate conductDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    LocalDate deliveryDate;

    List<String> filePaths;

    List<MultipartFile> files = new ArrayList<>();
}
