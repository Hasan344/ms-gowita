package com.gowita.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PackageTypeRequest {

    @NotBlank(message = "Package type must not be empty")
    @Size(min = 3, max = 50, message = "Name should be in the range of 3 and 50 characters")
    String name;
}
