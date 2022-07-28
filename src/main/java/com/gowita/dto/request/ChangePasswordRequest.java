package com.gowita.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
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
public class ChangePasswordRequest {
    @NotBlank(message = "Password must not be empty")
    @Size(min = 8, message = "Minimum 9 character must be entered")
    String oldPassword;
    @NotBlank(message = "Password must not be empty")
    @Size(min = 8, message = "Minimum 9 character must be entered")
    String newPassword;

}
