package com.gowita.dto.request;

import javax.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoginRequest {
    @NotBlank(message = "Phone number must be entered")
    String phoneNumber;

    @NotBlank(message = "Password must be entered")
    String password;
}
