package com.gowita.controller;

import static com.gowita.constant.AuthRole.ROLE_ADMIN;
import static com.gowita.constant.AuthRole.ROLE_USER;

import com.gowita.constant.Status;
import com.gowita.dto.filter.TripFilter;
import com.gowita.dto.request.ChangeStatusRequest;
import com.gowita.dto.request.TripRequest;
import com.gowita.dto.response.TripAdminResponse;
import com.gowita.dto.response.TripDetailAdminResponse;
import com.gowita.dto.response.TripDetailUserResponse;
import com.gowita.dto.response.TripEditDetailUserResponse;
import com.gowita.dto.response.TripHomePageResponse;
import com.gowita.dto.response.TripResponseForSelect;
import com.gowita.dto.response.TripUserResponse;
import com.gowita.service.TripService;
import java.util.List;
import javax.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/trip")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TripController {

    TripService tripService;

    @PostMapping
    @PreAuthorize(ROLE_USER)
    @ResponseStatus(HttpStatus.CREATED)
    void create(@Valid @RequestBody TripRequest request) {
        tripService.create(request);
    }

    @PutMapping("/{id}")
    @PreAuthorize(ROLE_USER)
    @ResponseStatus(HttpStatus.OK)
    void update(@Valid @RequestBody TripRequest request, @PathVariable Long id) {
        tripService.update(request, id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize(ROLE_USER)
    @ResponseStatus(HttpStatus.OK)
    void delete(@PathVariable Long id) {
        tripService.delete(id);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    TripHomePageResponse get(@PathVariable Long id) {
        return tripService.get(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    Page<TripHomePageResponse> getAll(TripFilter tripFilter, @RequestParam int pageSize, @RequestParam int pageNumber) {
        return tripService.getAll(tripFilter, PageRequest.of(pageNumber, pageSize));
    }

    @PreAuthorize(ROLE_ADMIN)
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{id}/change-status")
    void changeTripStatus(@PathVariable Long id, @Valid @RequestBody ChangeStatusRequest request) {
        tripService.changeTripStatus(id, request);
    }

    @PreAuthorize(ROLE_ADMIN)
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/status/admin")
    Page<TripAdminResponse> getTripsForAdmin(Status status, @RequestParam(required = false) String phoneNumber, int pageSize, int pageNumber) {
        return tripService.getTripsForAdmin(status, phoneNumber, PageRequest.of(pageNumber, pageSize));
    }

    @PreAuthorize(ROLE_ADMIN)
    @GetMapping("/{id}/admin")
    @ResponseStatus(HttpStatus.OK)
    TripDetailAdminResponse getTripDetailForAdmin(@PathVariable Long id) {
        return tripService.getTripDetailForAdmin(id);
    }

    @PreAuthorize(ROLE_ADMIN)
    @GetMapping("/pending/count")
    @ResponseStatus(HttpStatus.OK)
    Long getPendingCount() {
        return tripService.getPendingCount();
    }

    @PreAuthorize(ROLE_USER)
    @GetMapping("/status")
    @ResponseStatus(HttpStatus.OK)
    Page<TripUserResponse> getTripsForUser(Status status, int pageSize, int pageNumber) {
        return tripService.getTripsForUser(status, PageRequest.of(pageNumber, pageSize));
    }

    @PreAuthorize(ROLE_USER)
    @GetMapping("/{id}/user")
    @ResponseStatus(HttpStatus.OK)
    TripDetailUserResponse getTripDetailForUser(@PathVariable Long id) {
        return tripService.getTripDetailForUser(id);
    }

    @PreAuthorize(ROLE_USER)
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}/edit/user")
    TripEditDetailUserResponse getTripEditDetailForUser(@PathVariable Long id) {
        return tripService.getTripEditDetailForUser(id);
    }

    @PreAuthorize(ROLE_USER)
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{id}/change-status/user")
    void changeStatusForUser(@PathVariable Long id, Status status) {
        tripService.changeStatusForUser(id, status);
    }

    @PreAuthorize(ROLE_USER)
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/select/package-offer")
    List<TripResponseForSelect> getTripsForPackageOfferSelect() {
        return tripService.getTripsForPackageOfferSelect();
    }
}
