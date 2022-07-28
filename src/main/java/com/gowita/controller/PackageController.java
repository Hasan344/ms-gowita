package com.gowita.controller;

import static com.gowita.constant.AuthRole.ROLE_ADMIN;
import static com.gowita.constant.AuthRole.ROLE_USER;

import com.gowita.constant.Status;
import com.gowita.dto.filter.PackageFilter;
import com.gowita.dto.request.ChangeStatusRequest;
import com.gowita.dto.request.PackageEditRequest;
import com.gowita.dto.request.PackageRequest;
import com.gowita.dto.response.PackageAdminResponse;
import com.gowita.dto.response.PackageDetailAdminResponse;
import com.gowita.dto.response.PackageDetailHomePageResponse;
import com.gowita.dto.response.PackageDetailUserResponse;
import com.gowita.dto.response.PackageEditDetailUserResponse;
import com.gowita.dto.response.PackageHomePageResponse;
import com.gowita.dto.response.PackageResponseForSelect;
import com.gowita.dto.response.PackageUserResponse;
import com.gowita.service.PackageService;
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
@RequestMapping("/v1/package")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PackageController {

    PackageService packageService;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    PackageDetailHomePageResponse get(@PathVariable Long id) {
        return packageService.get(id);
    }

    @PostMapping
    @PreAuthorize(ROLE_USER)
    @ResponseStatus(HttpStatus.CREATED)
    void create(@Valid PackageRequest request) {
        packageService.create(request);
    }

    @PutMapping("/{id}")
    @PreAuthorize(ROLE_USER)
    @ResponseStatus(HttpStatus.OK)
    void edit(@Valid PackageEditRequest request, @PathVariable Long id) {
        packageService.edit(request, id);
    }

    @PreAuthorize(ROLE_USER)
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    void delete(@PathVariable Long id) {
        packageService.delete(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    Page<PackageHomePageResponse> getAll(PackageFilter packageFilter, int pageSize, int pageNumber) {
        return packageService.getAll(packageFilter, PageRequest.of(pageNumber, pageSize));
    }

    @PreAuthorize(ROLE_ADMIN)
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{id}/change-status")
    void changePackageStatus(@PathVariable Long id, @Valid @RequestBody ChangeStatusRequest request) {
        packageService.changePackageStatus(id, request);
    }

    @PreAuthorize(ROLE_ADMIN)
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/status/admin")
    Page<PackageAdminResponse> getPackagesForAdmin(Status status, @RequestParam(required = false) String phoneNumber, int pageSize, int pageNumber) {
        return packageService.getPackagesForAdmin(status, phoneNumber, PageRequest.of(pageNumber, pageSize));
    }


    @PreAuthorize(ROLE_ADMIN)
    @GetMapping("/{id}/admin")
    @ResponseStatus(HttpStatus.OK)
    PackageDetailAdminResponse getPackageDetailForAdmin(@PathVariable Long id) {
        return packageService.getPackageDetailForAdmin(id);
    }

    @PreAuthorize(ROLE_ADMIN)
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/pending/count")
    Long getPendingCount() {
        return packageService.getPendingCount();
    }

    @PreAuthorize(ROLE_USER)
    @GetMapping("/status")
    @ResponseStatus(HttpStatus.OK)
    Page<PackageUserResponse> getPackagesForUser(Status status, int pageSize, int pageNumber) {
        return packageService.getPackagesForUser(status, PageRequest.of(pageNumber, pageSize));
    }

    @PreAuthorize(ROLE_USER)
    @GetMapping("/{id}/user")
    @ResponseStatus(HttpStatus.OK)
    PackageDetailUserResponse getPackageDetailForUser(@PathVariable Long id) {
        return packageService.getPackageDetailForUser(id);
    }

    @PreAuthorize(ROLE_USER)
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}/edit/user")
    PackageEditDetailUserResponse getPackageEditDetailForUser(@PathVariable Long id) {
        return packageService.getPackageEditDetailForUser(id);
    }

    @PreAuthorize(ROLE_USER)
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{id}/change-status/user")
    void changeStatusForUser(@PathVariable Long id, Status status) {
        packageService.changeStatusForUser(id, status);
    }

    @PreAuthorize(ROLE_USER)
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/select/trip-offer")
    List<PackageResponseForSelect> getPackagesForPackageOfferSelect() {
        return packageService.getPackagesForPackageOfferSelect();
    }
}

