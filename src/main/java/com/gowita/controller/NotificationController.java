package com.gowita.controller;

import static com.gowita.constant.AuthRole.ROLE_USER;

import com.gowita.dto.response.UserNotificationByStatus;
import com.gowita.dto.response.UserNotificationResponse;
import com.gowita.service.NotificationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/notification")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotificationController {

    NotificationService notificationService;

    @GetMapping("/user")
    @PreAuthorize(ROLE_USER)
    @ResponseStatus(HttpStatus.OK)
    UserNotificationResponse getUserNotification() {
        return notificationService.getUserNotification();
    }

    @PreAuthorize(ROLE_USER)
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/package/detail")
    UserNotificationByStatus getPackageNotificationByStatus() {
        return notificationService.getPackageNotificationByStatus();
    }

    @PreAuthorize(ROLE_USER)
    @GetMapping("/trip/detail")
    @ResponseStatus(HttpStatus.OK)
    UserNotificationByStatus getTripNotificationByStatus() {
        return notificationService.getTripNotificationByStatus();
    }

    @PreAuthorize(ROLE_USER)
    @PatchMapping("/package/id")
    @ResponseStatus(HttpStatus.OK)
    void editPackageNotification(@PathVariable Long id) {
        notificationService.editPackageNotification(id);
    }

    @PreAuthorize(ROLE_USER)
    @PatchMapping("/trip/id")
    @ResponseStatus(HttpStatus.OK)
    void editTripNotification(@PathVariable Long id) {
        notificationService.editTripNotification(id);
    }
}
