package com.gowita.dto.request;

import java.time.LocalDate;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PackageRequest {

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

    @NotNull(message = "Weight must not be empty")
    Double weight;

    @NotNull(message = "Package type must be selected")
    Long typeId;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    LocalDate conductDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    LocalDate deliveryDate;

    MultipartFile[] files;
}
