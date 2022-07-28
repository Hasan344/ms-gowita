package com.gowita.controller;

import static com.gowita.constant.AuthRole.ROLE_USER;

import com.gowita.dto.request.ChangePasswordRequest;
import com.gowita.dto.request.SignupRequest;
import com.gowita.dto.request.UserEditRequest;
import com.gowita.dto.request.VerifyRequest;
import com.gowita.dto.response.UserResponse;
import com.gowita.service.UserService;
import javax.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/user")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {

    UserService userService;

    @GetMapping("/check/{phoneNumber}")
    @ResponseStatus(HttpStatus.OK)
    void signup(@PathVariable String phoneNumber) {
        userService.check(phoneNumber);
    }

    @PostMapping("/otp")
    @ResponseStatus(HttpStatus.OK)
    void otpVerification(@RequestBody VerifyRequest request) {
        userService.otpVerification(request.getPhoneNumber());
    }

    @PutMapping("/{id}")
    @PreAuthorize(ROLE_USER)
    @ResponseStatus(HttpStatus.OK)
    void edit(@Valid @RequestBody UserEditRequest request,
              @PathVariable Long id) {
        userService.edit(request, id);
    }

    @GetMapping("/{id}")
    @PreAuthorize(ROLE_USER)
    @ResponseStatus(HttpStatus.OK)
    public UserResponse getUserByIdForEdit(@PathVariable Long id) {
        return userService.getUserByIdForEdit(id);
    }

    @PatchMapping("/change-password")
    @PreAuthorize(ROLE_USER)
    @ResponseStatus(HttpStatus.OK)
    void changePassword(@Valid @RequestBody ChangePasswordRequest request) {
        userService.changePassword(request);
    }
}
