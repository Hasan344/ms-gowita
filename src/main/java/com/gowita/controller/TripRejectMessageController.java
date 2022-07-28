package com.gowita.controller;

import static com.gowita.constant.AuthRole.ROLE_ADMIN;

import com.gowita.dto.request.TripRejectMessageRequest;
import com.gowita.dto.response.TripMessageResponse;
import com.gowita.service.TripRejectMessageService;
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
@RequestMapping("/v1/reject-message/trip")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TripRejectMessageController {

    TripRejectMessageService tripRejectMessageService;

    @GetMapping
    @PreAuthorize(ROLE_ADMIN)
    @ResponseStatus(HttpStatus.OK)
    List<TripMessageResponse> getAll() {
        return tripRejectMessageService.getAll();
    }

    @PostMapping
    @PreAuthorize(ROLE_ADMIN)
    @ResponseStatus(HttpStatus.CREATED)
    void create(@Valid @RequestBody TripRejectMessageRequest message) {
        tripRejectMessageService.create(message);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize(ROLE_ADMIN)
    @ResponseStatus(HttpStatus.OK)
    void delete(@PathVariable Long id) {
        tripRejectMessageService.delete(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize(ROLE_ADMIN)
    @ResponseStatus(HttpStatus.OK)
    void update(@Valid @RequestBody TripRejectMessageRequest message, @PathVariable Long id) {
        tripRejectMessageService.update(message, id);
    }
}
