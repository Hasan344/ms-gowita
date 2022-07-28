package com.gowita.controller;

import com.gowita.dto.request.LoginRequest;
import com.gowita.dto.request.ResetPasswordRequest;
import com.gowita.dto.response.LoginResponse;
import com.gowita.service.AuthService;
import javax.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/auth")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthController {

    AuthService authService;

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.OK)
    void logout(@RequestHeader(name = "authorization") String authorization) {
        authService.logout(authorization);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/reset-password")
    void resetPassword(@Valid @RequestBody ResetPasswordRequest passwordRequest) {
        authService.resetPassword(passwordRequest);
    }

}
