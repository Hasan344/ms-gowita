package com.gowita.controller;

import static com.gowita.constant.AuthRole.ROLE_USER;

import com.gowita.constant.PackageOfferStatus;
import com.gowita.dto.request.PackageOfferEditRequest;
import com.gowita.dto.request.PackageOfferRequest;
import com.gowita.dto.response.CountPackageOfferStatus;
import com.gowita.dto.response.PackageOfferResponseForPackage;
import com.gowita.dto.response.PackageOfferResponseForTrip;
import com.gowita.service.PackageOfferService;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/package-offer")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PackageOfferController {
    PackageOfferService packageOfferService;

    @PostMapping
    @PreAuthorize(ROLE_USER)
    @ResponseStatus(HttpStatus.CREATED)
    void create(@Valid @RequestBody PackageOfferRequest request) {
        packageOfferService.create(request);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize(ROLE_USER)
    void update(@Valid @RequestBody PackageOfferEditRequest request, @PathVariable Long id) {
        packageOfferService.update(request, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize(ROLE_USER)
    void delete(@PathVariable Long id) {
        packageOfferService.delete(id);
    }

    @PreAuthorize(ROLE_USER)
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/status/package/{id}")
    Page<PackageOfferResponseForPackage> getPackageContactOffersForPackage(@PathVariable Long id, PackageOfferStatus status, int pageSize,
                                                                           int pageNumber) {
        return packageOfferService.getPackageContactOffersForPackage(id, status, PageRequest.of(pageNumber, pageSize));
    }

    @PreAuthorize(ROLE_USER)
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/status/trip/{id}")
    Page<PackageOfferResponseForTrip> getPackageOfferForTrip(@PathVariable Long id, PackageOfferStatus status, int pageSize, int pageNumber) {
        return packageOfferService.getPackageOfferForTrip(id, status, PageRequest.of(pageNumber, pageSize));
    }

    @PreAuthorize(ROLE_USER)
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    PackageOfferResponseForTrip getPackageOfferEditForTrip(@PathVariable Long id) {
        return packageOfferService.getPackageOfferEditForTrip(id);
    }

    @PreAuthorize(ROLE_USER)
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/count/package/{id}")
    CountPackageOfferStatus getPackageOfferCountForPackage(@PathVariable Long id) {
        return packageOfferService.getPackageOfferCountForPackage(id);
    }

    @PreAuthorize(ROLE_USER)
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/count/trip/{id}")
    CountPackageOfferStatus getPackageOfferCountForTrip(@PathVariable Long id) {
        return packageOfferService.getPackageOfferCountForTrip(id);
    }

    @PreAuthorize(ROLE_USER)
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{id}/status/card")
    void changePackagerOfferStatusCard(@PathVariable Long id) {
        packageOfferService.changeOfferStatusCard(id);
    }

    @PreAuthorize(ROLE_USER)
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{id}/status/hidden")
    void changePackagerOfferStatusHidden(@PathVariable Long id) {
        packageOfferService.changeOfferStatusHidden(id);
    }

    @PreAuthorize(ROLE_USER)
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{id}/status/raw")
    void changePackagerOfferStatusRaw(@PathVariable Long id) {
        packageOfferService.changeOfferStatusRaw(id);
    }


    @PreAuthorize(ROLE_USER)
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/package/{id}/status/open")
    void changePackagerOfferStatusOpen(@PathVariable Long id) {
        packageOfferService.changeOfferStatusOpen(id);
    }

}
