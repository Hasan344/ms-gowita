package com.gowita.dto.response;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoginResponse {
    String token;
    String phoneNumber;
    Long id;
    String name;
    String surname;
}

