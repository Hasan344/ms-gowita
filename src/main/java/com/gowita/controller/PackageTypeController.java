package com.gowita.controller;

import static com.gowita.constant.AuthRole.ROLE_ADMIN;

import com.gowita.dto.request.PackageTypeRequest;
import com.gowita.dto.response.PackageTypeResponse;
import com.gowita.dto.response.PackageTypeResponseForSelect;
import com.gowita.service.PackageTypeService;
import java.util.List;
import javax.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/package-type")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PackageTypeController {

    PackageTypeService packageTypeService;

    @GetMapping("/select")
    @ResponseStatus(HttpStatus.OK)
    List<PackageTypeResponseForSelect> getAllForSelect() {
        return packageTypeService.getAllForSelect();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<PackageTypeResponse> getAll() {
        return packageTypeService.getAll();
    }

    @PostMapping
    @PreAuthorize(ROLE_ADMIN)
    @ResponseStatus(HttpStatus.CREATED)
    void create(@Valid @RequestBody PackageTypeRequest request) {
        packageTypeService.create(request);
    }

    @PutMapping("/{id}")
    @PreAuthorize(ROLE_ADMIN)
    @ResponseStatus(HttpStatus.OK)
    void edit(@Valid @RequestBody PackageTypeRequest request,
              @PathVariable Long id) {
        packageTypeService.edit(request, id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize(ROLE_ADMIN)
    @ResponseStatus(HttpStatus.OK)
    void delete(@PathVariable Long id) {
        packageTypeService.delete(id);
    }
}
