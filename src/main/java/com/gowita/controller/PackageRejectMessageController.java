package com.gowita.controller;

import static com.gowita.constant.AuthRole.ROLE_ADMIN;

import com.gowita.dto.request.PackageRejectMessageRequest;
import com.gowita.dto.response.PackageMessageResponse;
import com.gowita.service.PackageRejectMessageService;
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
@RequestMapping("/v1/reject-message/package")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PackageRejectMessageController {

    PackageRejectMessageService packageRejectMessageService;

    @GetMapping
    @PreAuthorize(ROLE_ADMIN)
    @ResponseStatus(HttpStatus.OK)
    List<PackageMessageResponse> getAll() {
        return packageRejectMessageService.getAll();
    }

    @PostMapping
    @PreAuthorize(ROLE_ADMIN)
    @ResponseStatus(HttpStatus.CREATED)
    void create(@Valid @RequestBody PackageRejectMessageRequest request) {
        packageRejectMessageService.create(request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize(ROLE_ADMIN)
    @ResponseStatus(HttpStatus.OK)
    void delete(@PathVariable Long id) {
        packageRejectMessageService.delete(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize(ROLE_ADMIN)
    @ResponseStatus(HttpStatus.OK)
    void update(@Valid @RequestBody PackageRejectMessageRequest request, @PathVariable Long id) {
        packageRejectMessageService.update(request, id);
    }
}
