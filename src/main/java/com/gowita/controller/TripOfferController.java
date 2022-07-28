package com.gowita.controller;

import static com.gowita.constant.AuthRole.ROLE_USER;

import com.gowita.constant.TripOfferStatus;
import com.gowita.dto.request.TripOfferRequest;
import com.gowita.dto.response.CountTripOfferStatus;
import com.gowita.dto.response.PackageDetailsForTripOffer;
import com.gowita.dto.response.TripOfferResponseForPackage;
import com.gowita.dto.response.TripOfferResponseForTrip;
import com.gowita.service.TripOfferService;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/trip-offer")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TripOfferController {

    TripOfferService tripOfferService;

    @PostMapping
    @PreAuthorize(ROLE_USER)
    @ResponseStatus(HttpStatus.CREATED)
    void create(@Valid @RequestBody TripOfferRequest request) {
        tripOfferService.create(request);
    }

    @PreAuthorize(ROLE_USER)
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/status/package/{id}")
    Page<TripOfferResponseForPackage> getTripOfferForPackage(@PathVariable Long id, TripOfferStatus status,
                                                             int pageSize, int pageNumber) {
        return tripOfferService.getTripOfferForPackage(id, status, PageRequest.of(pageNumber, pageSize));
    }

    @PreAuthorize(ROLE_USER)
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/status/trip/{id}")
    Page<TripOfferResponseForTrip> getTripContactOffersForTrip(@PathVariable Long id, TripOfferStatus status,
                                                               int pageSize, int pageNumber) {
        return tripOfferService.getTripContactOffersForTrip(id, status, PageRequest.of(pageNumber, pageSize));
    }

    @PreAuthorize(ROLE_USER)
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{id}/status/confirmed")
    void changeOfferStatusConfirmed(@PathVariable Long id) {
        tripOfferService.changeOfferStatusConfirmed(id);
    }

    @PreAuthorize(ROLE_USER)
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{id}/status/rejected")
    void changOfferStatusReject(@PathVariable Long id) {
        tripOfferService.changOfferStatusReject(id);
    }

    @PreAuthorize(ROLE_USER)
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}/status/delete")
    void changeOfferStatusDelete(@PathVariable Long id) {
        tripOfferService.changeOfferStatusDelete(id);
    }

    @PreAuthorize(ROLE_USER)
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{id}/status/card")
    void changeOfferStatusCard(@PathVariable Long id) {
        tripOfferService.changeOfferStatusCard(id);
    }

    @PreAuthorize(ROLE_USER)
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{id}/status/opened")
    void changeOfferStatusOpen(@PathVariable Long id) {
        tripOfferService.changeOfferStatusOpen(id);
    }

    @PreAuthorize(ROLE_USER)
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}/details")
    PackageDetailsForTripOffer getPackageDetailsForTripOffer(@PathVariable Long id) {
        return tripOfferService.getPackageDetailsForTripOffer(id);
    }

    @PreAuthorize(ROLE_USER)
    @GetMapping("/count/package/{id}")
    @ResponseStatus(HttpStatus.OK)
    CountTripOfferStatus getTripOfferCountForPackage(@PathVariable Long id) {
        return tripOfferService.getTripOfferCountForPackage(id);
    }

    @PreAuthorize(ROLE_USER)
    @GetMapping("/count/trip/{id}")
    @ResponseStatus(HttpStatus.OK)
    CountTripOfferStatus getTripOfferCountForTrip(@PathVariable Long id) {
        return tripOfferService.getTripOfferCountForTrip(id);
    }
}
