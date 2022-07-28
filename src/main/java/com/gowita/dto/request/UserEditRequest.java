package com.gowita.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
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
public class UserEditRequest {

    @NotBlank(message = "Name must not be empty")
    @Size(min = 3, max = 50, message = "Name should be in the range of 3 and 50 characters")
    @Pattern(regexp = "^[A-Za-zÜüÖöĞğİıƏəÇçŞş]+$",
            message = "Only letters must be entered!")
    String name;

    @NotBlank(message = "Surname must not be empty")
    @Size(min = 4, max = 50, message = "Surname should be in the range of 4 and 50 characters")
    @Pattern(regexp = "^[A-Za-zÜüÖöĞğİıƏəÇçŞş]+$",
            message = "Only letters must be entered!")
    String surname;

    @NotNull(message = "Country must be selected")
    Long countryId;

    String additionalNumber;
}

